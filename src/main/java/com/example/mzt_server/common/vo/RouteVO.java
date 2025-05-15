package com.example.mzt_server.common.vo;

import java.util.List;
import java.util.Map;

public class RouteVO {
    private String path;
    private String component;
    private String redirect;
    private String name;
    private Meta meta;
    private List<RouteVO> children;

    // getter/setter
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public String getComponent() { return component; }
    public void setComponent(String component) { this.component = component; }
    public String getRedirect() { return redirect; }
    public void setRedirect(String redirect) { this.redirect = redirect; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Meta getMeta() { return meta; }
    public void setMeta(Meta meta) { this.meta = meta; }
    public List<RouteVO> getChildren() { return children; }
    public void setChildren(List<RouteVO> children) { this.children = children; }

    public static class Meta {
        private String title;
        private String icon;
        private Boolean hidden;
        private Boolean keepAlive;
        private Boolean alwaysShow;
        private Map<String, String> params;
        // getter/setter
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
        public Boolean getHidden() { return hidden; }
        public void setHidden(Boolean hidden) { this.hidden = hidden; }
        public Boolean getKeepAlive() { return keepAlive; }
        public void setKeepAlive(Boolean keepAlive) { this.keepAlive = keepAlive; }
        public Boolean getAlwaysShow() { return alwaysShow; }
        public void setAlwaysShow(Boolean alwaysShow) { this.alwaysShow = alwaysShow; }
        public Map<String, String> getParams() { return params; }
        public void setParams(Map<String, String> params) { this.params = params; }
    }
} 