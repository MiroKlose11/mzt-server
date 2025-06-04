package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 城市实体类
 */
@Data
@TableName("city")
public class City {
    @TableId(value = "id")
    private Integer id;
    private String name;
    private Integer parentid;
    private String shortname;
    private Integer leveltype;
    private String citycode;
    private String zipcode;
    private String lng;
    private String lat;
    private String pinyin;
    private String status;
} 