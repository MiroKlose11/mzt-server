package com.example.mzt_server.entity;

import java.util.Date;

public class SysMenu {
    private Long id;
    private Long parentId;
    private String treePath;
    private String name;
    private Integer type;
    private String routeName;
    private String routePath;
    private String component;
    private String perm;
    private Integer alwaysShow;
    private Integer keepAlive;
    private Integer visible;
    private Integer sort;
    private String icon;
    private String redirect;
    private Date createTime;
    private Date updateTime;
    private String params;

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public String getTreePath() { return treePath; }
    public void setTreePath(String treePath) { this.treePath = treePath; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getType() { return type; }
    public void setType(Integer type) { this.type = type; }
    public String getRouteName() { return routeName; }
    public void setRouteName(String routeName) { this.routeName = routeName; }
    public String getRoutePath() { return routePath; }
    public void setRoutePath(String routePath) { this.routePath = routePath; }
    public String getComponent() { return component; }
    public void setComponent(String component) { this.component = component; }
    public String getPerm() { return perm; }
    public void setPerm(String perm) { this.perm = perm; }
    public Integer getAlwaysShow() { return alwaysShow; }
    public void setAlwaysShow(Integer alwaysShow) { this.alwaysShow = alwaysShow; }
    public Integer getKeepAlive() { return keepAlive; }
    public void setKeepAlive(Integer keepAlive) { this.keepAlive = keepAlive; }
    public Integer getVisible() { return visible; }
    public void setVisible(Integer visible) { this.visible = visible; }
    public Integer getSort() { return sort; }
    public void setSort(Integer sort) { this.sort = sort; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public String getRedirect() { return redirect; }
    public void setRedirect(String redirect) { this.redirect = redirect; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public Date getUpdateTime() { return updateTime; }
    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
    public String getParams() { return params; }
    public void setParams(String params) { this.params = params; }
} 