package com.example.mzt_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 机构类型数据传输对象
 */
@Data
@Schema(description = "机构类型信息")
public class OrganizationTypeDTO {
    @Schema(description = "机构类型ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "类型唯一标识", required = true)
    private String code;

    @Schema(description = "类型名称", required = true)
    private String name;

    @Schema(description = "类型说明")
    private String description;
} 