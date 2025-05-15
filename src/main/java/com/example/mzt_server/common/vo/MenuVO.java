package com.example.mzt_server.common.vo;

import java.util.List;

public class MenuVO {
    private Long id;
    private Long parentId;
    private String name;
    private Integer type;
    private String routeName;
    private String routePath;
    private String component;
    private Integer sort;
    private Integer visible;
    private String icon;
    private String redirect;
    private String perm;
    private List<MenuVO> children;

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
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
    public Integer getSort() { return sort; }
    public void setSort(Integer sort) { this.sort = sort; }
    public Integer getVisible() { return visible; }
    public void setVisible(Integer visible) { this.visible = visible; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public String getRedirect() { return redirect; }
    public void setRedirect(String redirect) { this.redirect = redirect; }
    public String getPerm() { return perm; }
    public void setPerm(String perm) { this.perm = perm; }
    public List<MenuVO> getChildren() { return children; }
    public void setChildren(List<MenuVO> children) { this.children = children; }
} 