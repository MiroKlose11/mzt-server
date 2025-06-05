package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 机构类型实体类
 */
@Data
@TableName("organization_type")
public class OrganizationType {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String code;
    private String name;
    private String description;
} 