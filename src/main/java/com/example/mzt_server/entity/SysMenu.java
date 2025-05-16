package com.example.mzt_server.entity;

import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * 系统菜单实体
 */
@Data
public class SysMenu {
    /**
     * 菜单ID
     */
    private Long id;
    
    /**
     * 父菜单ID
     */
    private Long parentId;
    
    /**
     * 树路径，例如：0,1,2
     */
    private String treePath;
    
    /**
     * 菜单名称
     */
    private String name;
    
    /**
     * 菜单类型（1-菜单 2-目录 3-外链 4-按钮）
     */
    private Integer type;
    
    /**
     * 路由名称
     */
    private String routeName;
    
    /**
     * 路由路径
     */
    private String routePath;
    
    /**
     * 组件路径
     */
    private String component;
    
    /**
     * 权限标识
     */
    private String perm;
    
    /**
     * 显示状态(1-显示 0-隐藏)
     */
    private Integer visible;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 菜单图标
     */
    private String icon;
    
    /**
     * 跳转路径
     */
    private String redirect;
    
    /**
     * 是否开启页面缓存(1-开启 0-关闭)
     */
    private Integer keepAlive;
    
    /**
     * 是否显示在菜单中(1-显示 0-隐藏)
     */
    private Integer alwaysShow;
    
    /**
     * 路由参数
     */
    private String params;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * 子菜单列表
     */
    private List<SysMenu> children;
} 