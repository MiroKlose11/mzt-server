package com.example.mzt_server.controller;

import com.example.mzt_server.common.Result;
import com.example.mzt_server.util.TencentCosUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件上传控制器
 */
@Tag(name = "文件管理", description = "文件上传相关接口")
@RestController
@RequestMapping("/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private TencentCosUtils tencentCosUtils;

    /**
     * 上传文件到腾讯云COS
     * @param file 文件对象
     * @param directory 上传目录 (可选, 例如: images/avatars/)
     * @return 文件访问URL
     */
    @Operation(summary = "上传文件", description = "上传文件到腾讯云COS")
    @PostMapping("/upload")
    public Result<String> uploadFile(
            @Parameter(description = "要上传的文件") @RequestParam MultipartFile file,
            @Parameter(description = "上传到COS的目录路径 (可选, e.g., 'images/articles/')") @RequestParam(required = false) String directory) {
        if (file.isEmpty()) {
            return Result.error("上传失败，请选择文件");
        }
        try {
            // 自动判断目录
            String dir = directory;
            if (dir == null || dir.trim().isEmpty()) {
                String contentType = file.getContentType();
                if (contentType != null) {
                    if (contentType.startsWith("image/")) {
                        dir = "images";
                    } else if (contentType.startsWith("video/")) {
                        dir = "videos";
                    } else {
                        dir = "others";
                    }
                } else {
                    dir = "others";
                }
            }
            String fileUrl = tencentCosUtils.uploadFile(file, dir);
            return Result.success(fileUrl);
        } catch (IOException e) {
            logger.error("File upload failed due to IOException. FileName: {}, Directory: {}", file.getOriginalFilename(), directory, e);
            return Result.error("文件上传失败：IO异常，请稍后重试或联系管理员");
        } catch (Exception e) {
            logger.error("File upload failed due to an unexpected error. FileName: {}, Directory: {}", file.getOriginalFilename(), directory, e);
            return Result.error("文件上传失败：系统内部错误，请稍后重试或联系管理员");
        }
    }
} 