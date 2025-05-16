package com.example.mzt_server.common.vo;

import java.util.List;

/**
 * 菜单表单对象
 */
public class MenuForm {
    private Long id;                 // 菜单ID
    private Long parentId;           // 父菜单ID
    private String name;             // 菜单名称
    private Integer type;            // 菜单类型（1-菜单 2-目录 3-外链 4-按钮）
    private String routeName;        // 路由名称
    private String routePath;        // 路由路径
    private String component;        // 组件路径
    private String perm;             // 权限标识
    private Integer visible;         // 显示状态(1:显示;0:隐藏)
    private Integer sort;            // 排序
    private String icon;             // 菜单图标
    private String redirect;         // 跳转路径
    private Integer keepAlive;       // 是否开启页面缓存
    private Integer alwaysShow;      // 是否始终显示
    private List<KeyValue> params;   // 路由参数

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRoutePath() {
        return routePath;
    }

    public void setRoutePath(String routePath) {
        this.routePath = routePath;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getPerm() {
        return perm;
    }

    public void setPerm(String perm) {
        this.perm = perm;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public Integer getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Integer keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Integer getAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(Integer alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public List<KeyValue> getParams() {
        return params;
    }

    public void setParams(List<KeyValue> params) {
        this.params = params;
    }
} 