package com.example.mzt_server.util;

import com.example.mzt_server.config.TencentCosConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.qcloud.cos.transfer.Upload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.concurrent.Executors;

/**
 * 腾讯云COS操作工具类
 */
@Component
public class TencentCosUtils {

    private static final Logger logger = LoggerFactory.getLogger(TencentCosUtils.class);
    // 5MB，用于判断是否使用分片上传
    private static final long MULTIPART_UPLOAD_THRESHOLD = 5 * 1024 * 1024;
    // 分片大小为5MB
    private static final long MULTIPART_UPLOAD_PART_SIZE = 5 * 1024 * 1024;

    @Autowired
    private TencentCosConfig tencentCosConfig;

    private COSClient cosClient;
    private TransferManager transferManager;

    /**
     * 初始化COSClient和TransferManager (懒加载)
     */
    private synchronized void initClient() {
        if (cosClient == null) {
            COSCredentials cred = new BasicCOSCredentials(tencentCosConfig.getSecretId(), tencentCosConfig.getSecretKey());
            Region region = new Region(tencentCosConfig.getRegion());
            ClientConfig clientConfig = new ClientConfig(region);
            clientConfig.setHttpProtocol(HttpProtocol.https); // 设置 Https 访问
            cosClient = new COSClient(cred, clientConfig);
            
            // 初始化 TransferManager
            TransferManagerConfiguration transferConfig = new TransferManagerConfiguration();
            // 设置分片上传阈值和分片大小
            transferConfig.setMultipartUploadThreshold(MULTIPART_UPLOAD_THRESHOLD);
            transferConfig.setMinimumUploadPartSize(MULTIPART_UPLOAD_PART_SIZE);
            // 创建 TransferManager 实例，使用固定大小的线程池
            transferManager = new TransferManager(cosClient, Executors.newFixedThreadPool(10));
            transferManager.setConfiguration(transferConfig);
            
            logger.info("COS Client and TransferManager initialized successfully");
        }
    }

    /**
     * 上传文件（自动选择普通上传或分片上传）
     *
     * @param file         MultipartFile文件对象
     * @param directory    上传到COS的目录（例如：images/avatar/）
     * @return 文件访问URL
     * @throws IOException IO异常
     * @throws CosServiceException COS服务异常
     * @throws CosClientException COS客户端异常
     */
    public String uploadFile(MultipartFile file, String directory)
            throws IOException, CosServiceException, CosClientException {
        initClient(); // 确保客户端已初始化

        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFileName = UUID.randomUUID().toString().replace("-", "") + extension;
        
        String processedDirectory = (directory == null ? "" : directory.trim());

        String key = processedDirectory;
        if (!key.isEmpty() && !key.endsWith("/")) {
            key += "/";
        }
        key += newFileName;

        long fileSize = file.getSize();
        logger.info("Uploading file '{}' (size: {} bytes) to COS with key '{}' in bucket '{}'", 
                   originalFilename, fileSize, key, tencentCosConfig.getBucketName());

        try {
            if (fileSize > MULTIPART_UPLOAD_THRESHOLD) {
                // 大文件使用分片上传
                return uploadLargeFile(file, key);
            } else {
                // 小文件使用普通上传
                return uploadSmallFile(file, key);
            }
        } catch (IOException e) {
            logger.error("IOException during file upload to COS for key '{}'. FileName: '{}'", key, originalFilename, e);
            throw e;
        } catch (CosServiceException e) {
            logger.error("CosServiceException during file upload to COS for key '{}'. FileName: '{}'. Error Code: {}, Error Message: {}", 
                       key, originalFilename, e.getErrorCode(), e.getErrorMessage(), e);
            throw e;
        } catch (CosClientException e) {
            logger.error("CosClientException during file upload to COS for key '{}'. FileName: '{}'. Error Message: {}", 
                       key, originalFilename, e.getMessage(), e);
            throw e;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Upload interrupted for key '{}'. FileName: '{}'", key, originalFilename, e);
            throw new IOException("File upload was interrupted", e);
        }
    }

    /**
     * 上传小文件（直接上传）
     */
    private String uploadSmallFile(MultipartFile file, String key) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(tencentCosConfig.getBucketName(), key, inputStream, objectMetadata);
            cosClient.putObject(putObjectRequest);
            logger.info("Small file '{}' uploaded successfully to COS. Key: '{}'", file.getOriginalFilename(), key);

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.warn("Failed to close input stream for file: {}", file.getOriginalFilename(), e);
                }
            }
        }

        return getFileUrl(key);
    }

    /**
     * 上传大文件（分片上传）
     */
    private String uploadLargeFile(MultipartFile file, String key) throws IOException, InterruptedException {
        // 将MultipartFile转为临时文件
        Path tempFile = Files.createTempFile("cos_upload_", null);
        try {
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
            File fileToUpload = tempFile.toFile();

            // 设置元数据
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            
            // 创建分片上传请求
            PutObjectRequest putObjectRequest = new PutObjectRequest(tencentCosConfig.getBucketName(), key, fileToUpload);
            putObjectRequest.setMetadata(objectMetadata);
            
            // 执行分片上传
            Upload upload = transferManager.upload(putObjectRequest);
            
            // 等待传输完成（同步）
            upload.waitForCompletion();
            
            logger.info("Large file '{}' uploaded successfully to COS using multipart upload. Key: '{}'", 
                      file.getOriginalFilename(), key);
            
            return getFileUrl(key);
        } finally {
            // 删除临时文件
            try {
                Files.deleteIfExists(tempFile);
            } catch (IOException e) {
                logger.warn("Failed to delete temporary file: {}", tempFile, e);
            }
        }
    }

    /**
     * 获取文件的访问URL
     */
    private String getFileUrl(String key) {
        String baseUrl = tencentCosConfig.getBaseUrl();
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        return baseUrl + key;
    }

    /**
     * 关闭COSClient和TransferManager (应用停止时调用)
     */
    @PreDestroy
    public void destroy() {
        if (transferManager != null) {
            logger.info("Shutting down TransferManager...");
            transferManager.shutdownNow(false);
            logger.info("TransferManager shutdown complete.");
        }
        
        if (cosClient != null) {
            logger.info("Shutting down COSClient...");
            cosClient.shutdown();
            cosClient = null;
            logger.info("COSClient shutdown complete.");
        }
    }
} 