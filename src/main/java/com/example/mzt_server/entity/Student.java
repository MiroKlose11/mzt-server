package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 学生实体类
 */
@Data
@TableName("student")
@Schema(description = "学生信息")
public class Student {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "学生ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    
    /**
     * 姓名
     */
    @Schema(description = "姓名", example = "李四", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    
    /**
     * 头像URL
     */
    @Schema(description = "头像URL", example = "https://example.com/avatar.jpg")
    private String avatar;
    
    /**
     * 性别：0=未知，1=男，2=女
     */
    @Schema(description = "性别", example = "1", allowableValues = {"0", "1", "2"})
    private Integer gender;
    
    /**
     * 个性签名
     */
    @Schema(description = "个性签名", example = "努力学习中")
    private String signature;
    
    /**
     * 所属机构ID
     */
    @Schema(description = "所属机构ID", example = "1")
    private Integer organizationId;
    
    /**
     * 用户ID，关联用户表
     */
    @Schema(description = "用户ID，关联系统用户", example = "1")
    private Integer userId;
    
    /**
     * 状态：1=启用，0=禁用
     */
    @Schema(description = "状态", example = "1", allowableValues = {"0", "1"})
    private Integer status;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;
} 