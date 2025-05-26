package com.example.mzt_server.util;

import com.example.mzt_server.config.TencentCosConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 腾讯云COS操作工具类
 */
@Component
public class TencentCosUtils {

    private static final Logger logger = LoggerFactory.getLogger(TencentCosUtils.class);

    @Autowired
    private TencentCosConfig tencentCosConfig;

    private COSClient cosClient;

    /**
     * 初始化COSClient (懒加载)
     */
    private synchronized void initClient() { // 添加synchronized确保线程安全初始化
        if (cosClient == null) {
            COSCredentials cred = new BasicCOSCredentials(tencentCosConfig.getSecretId(), tencentCosConfig.getSecretKey());
            Region region = new Region(tencentCosConfig.getRegion());
            ClientConfig clientConfig = new ClientConfig(region);
            clientConfig.setHttpProtocol(HttpProtocol.https); // 设置 Https 访问
            cosClient = new COSClient(cred, clientConfig);
        }
    }

    /**
     * 上传文件
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
        
        String processedDirectory = (directory == null ? "" : directory.trim()); // Trim whitespace

        String key = processedDirectory;
        if (!key.isEmpty() && !key.endsWith("/")) { // if not empty and not ending with /, add /
            key += "/";
        }
        key += newFileName;

        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            // 优先使用 file.getSize() 获取文件大小，更可靠
            objectMetadata.setContentLength(file.getSize()); 
            objectMetadata.setContentType(file.getContentType());

            logger.info("Uploading file '{}' to COS with key '{}' in bucket '{}'", originalFilename, key, tencentCosConfig.getBucketName());

            PutObjectRequest putObjectRequest = new PutObjectRequest(tencentCosConfig.getBucketName(), key, inputStream, objectMetadata);
            cosClient.putObject(putObjectRequest);
            logger.info("File '{}' uploaded successfully to COS. Key: '{}'", originalFilename, key);

        } catch (IOException e) {
            logger.error("IOException during file upload to COS for key '{}'. FileName: '{}'", key, originalFilename, e);
            throw e; // Re-throw the exception to be handled by the controller
        } catch (CosServiceException e) {
            logger.error("CosServiceException during file upload to COS for key '{}'. FileName: '{}'. Error Code: {}, Error Message: {}", 
                         key, originalFilename, e.getErrorCode(), e.getErrorMessage(), e);
            throw e; // Re-throw
        } catch (CosClientException e) {
            logger.error("CosClientException during file upload to COS for key '{}'. FileName: '{}'. Error Message: {}", 
                         key, originalFilename, e.getMessage(), e);
            throw e; // Re-throw
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.warn("Failed to close input stream for file: {}", originalFilename, e);
                }
            }
        }

        String baseUrl = tencentCosConfig.getBaseUrl();
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        // COS的key本身不应该以/开头，如果directory为空，key就是文件名；如果directory不为空且以/结尾，key就是 dir/filename
        // String finalKey = key.startsWith("/") ? key.substring(1) : key; 
        return baseUrl + key;
    }

    /**
     * 关闭COSClient (应用停止时调用)
     * 使用@PreDestroy注解，当Spring容器销毁TencentCosUtils bean时自动调用此方法。
     */
    @PreDestroy
    public void destroy() {
        if (cosClient != null) {
            logger.info("Shutting down COSClient...");
            cosClient.shutdown();
            cosClient = null; // help GC
            logger.info("COSClient shutdown complete.");
        }
    }
} 