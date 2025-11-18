package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 机构类型实体类
 */
@Data
@TableName("organization_type")
@Schema(description = "机构类型信息")
public class OrganizationType {
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "机构类型ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    
    @Schema(description = "类型编码", example = "hospital", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;
    
    @Schema(description = "类型名称", example = "医院", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    
    @Schema(description = "类型描述", example = "医疗机构")
    private String description;
} 