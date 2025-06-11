package com.example.mzt_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 学生数据传输对象
 */
@Data
@Schema(description = "学生信息")
public class StudentDTO {
    @Schema(description = "学生ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "姓名", required = true)
    private String name;

    @Schema(description = "头像URL")
    private String avatar;

    @Schema(description = "性别：0=未知，1=男，2=女")
    private Integer gender;

    @Schema(description = "个性签名")
    private String signature;

    @Schema(description = "所属机构ID")
    private Integer organizationId;
    
    @Schema(description = "所属机构名称", accessMode = Schema.AccessMode.READ_ONLY)
    private String organizationName;

    @Schema(description = "状态：1=启用，0=禁用")
    private Integer status;

    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;
} 