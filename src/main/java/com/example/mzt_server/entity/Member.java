package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 成员实体类
 */
@Data
@TableName("member")
@Schema(description = "成员信息")
public class Member {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "成员ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    
    /**
     * 姓名
     */
    @Schema(description = "姓名", example = "张三", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    
    /**
     * 头像URL
     */
    @Schema(description = "头像URL", example = "https://example.com/avatar.jpg")
    private String avatar;
    
    /**
     * 性别 0=未知 1=男 2=女
     */
    @Schema(description = "性别", example = "1", allowableValues = {"0", "1", "2"})
    private Integer gender;
    
    /**
     * 机构ID，关联 organization 表
     */
    @Schema(description = "机构ID，关联organization表", example = "1")
    private Integer organizationId;
    
    /**
     * 备用机构名称（兜底用）
     */
    @Schema(description = "备用机构名称（兜底用）", example = "某某医院")
    private String organization;
    
    /**
     * 职业所在地ID
     */
    @Schema(description = "职业所在地ID", example = "110100")
    private Integer cityId;
    
    /**
     * 个人简介
     */
    @Schema(description = "个人简介", example = "资深医美专家，具有10年从业经验")
    private String introduction;
    
    /**
     * 状态：1=启用 0=禁用
     */
    @Schema(description = "状态", example = "1", allowableValues = {"0", "1"})
    private Integer status;
    
    /**
     * 权重，越大越靠前
     */
    @Schema(description = "权重，越大越靠前", example = "100")
    private Integer weight;
    
    /**
     * 是否为精英成员：0=否，1=是
     */
    @Schema(description = "是否为精英成员", example = "1", allowableValues = {"0", "1"})
    private Integer isElite;
    
    /**
     * 用户ID，关联用户表
     */
    @Schema(description = "用户ID，关联系统用户", example = "1")
    private Integer userId;
    
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