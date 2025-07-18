package com.example.mzt_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 机构数据传输对象
 */
@Data
@Schema(description = "机构信息")
public class OrganizationDTO {
    @Schema(description = "机构ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "机构名称", required = true)
    private String name;

    @Schema(description = "机构类型ID", required = true)
    private Integer typeId;
    
    @Schema(description = "机构类型名称", accessMode = Schema.AccessMode.READ_ONLY)
    private String typeName;

    @Schema(description = "机构地址")
    private String address;

    @Schema(description = "所在城市ID")
    private Integer cityId;
    
    @Schema(description = "所在城市名称", accessMode = Schema.AccessMode.READ_ONLY)
    private String cityName;

    @Schema(description = "联系电话")
    private String phone;
    
    @Schema(description = "机构封面图URL")
    private String avatar;

    @Schema(description = "状态，1=启用，0=禁用")
    private Integer status;
    
    @Schema(description = "权重，越大越靠前")
    private Integer weight;

    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;
} 