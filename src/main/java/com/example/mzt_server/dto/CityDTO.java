package com.example.mzt_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 城市数据传输对象
 */
@Data
@Schema(description = "城市信息")
public class CityDTO {
    @Schema(description = "城市ID")
    private Integer id;
    
    @Schema(description = "城市名称")
    private String name;
    
    @Schema(description = "父级ID")
    private Integer parentid;
    
    @Schema(description = "简称")
    private String shortname;
    
    @Schema(description = "级别:0,中国；1，省分；2，市；3，区、县")
    private Integer leveltype;
} 