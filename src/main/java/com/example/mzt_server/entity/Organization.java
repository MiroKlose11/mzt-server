package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 机构实体类
 */
@Data
@TableName("organization")
@Schema(description = "机构信息")
public class Organization {
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "机构ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    
    @Schema(description = "机构名称", example = "某某医美医院", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    
    @Schema(description = "机构类型ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer typeId;
    
    @Schema(description = "机构地址", example = "北京市朝阳区xxx路xxx号")
    private String address;
    
    @Schema(description = "所在城市ID", example = "110100")
    private Integer cityId;
    
    /**
     * 联系电话
     */
    @Schema(description = "联系电话", example = "010-12345678")
    private String phone;
    
    /**
     * 机构封面图URL
     */
    @Schema(description = "机构封面图URL", example = "https://example.com/logo.jpg")
    private String avatar;
    
    /**
     * 状态，1=启用，0=禁用
     */
    @Schema(description = "状态", example = "1", allowableValues = {"0", "1"})
    private Integer status;
    
    /**
     * 权重，越大越靠前
     */
    @Schema(description = "权重，越大越靠前", example = "100")
    private Integer weight;
    
    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;
    
    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;
} 