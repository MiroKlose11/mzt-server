package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统权限实体类
 */
@Data
@TableName("sys_permission")
public class SysPermission implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 权限名称
     */
    private String name;
    
    /**
     * 权限标识
     */
    private String permission;
    
    /**
     * 类型(1:菜单;2:按钮)
     */
    private Integer type;
    
    /**
     * 父权限ID
     */
    private Long parentId;
    
    /**
     * 路由路径
     */
    private String path;
    
    /**
     * 组件路径
     */
    private String component;
    
    /**
     * 图标
     */
    private String icon;
    
    /**
     * 显示顺序
     */
    private Integer sort;
    
    /**
     * 状态(0:禁用;1:正常)
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
} 