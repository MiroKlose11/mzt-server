package com.example.mzt_server.common.vo;

import java.util.List;
import java.util.Map;

/**
 * 路由视图对象
 */
public class RouteVO {
    private String path;           // 路由路径
    private String component;      // 组件路径
    private String redirect;       // 重定向路径
    private String name;           // 路由名称
    private Meta meta;             // 路由元数据
    private List<RouteVO> children; // 子路由列表

    public static class Meta {
        private String title;       // 标题
        private String icon;        // 图标
        private Boolean hidden;     // 是否隐藏
        private Boolean keepAlive;  // 是否缓存
        private Boolean alwaysShow; // 是否总是显示
        private Map<String, Object> params; // 路由参数

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Boolean getHidden() {
            return hidden;
        }

        public void setHidden(Boolean hidden) {
            this.hidden = hidden;
        }

        public Boolean getKeepAlive() {
            return keepAlive;
        }

        public void setKeepAlive(Boolean keepAlive) {
            this.keepAlive = keepAlive;
        }

        public Boolean getAlwaysShow() {
            return alwaysShow;
        }

        public void setAlwaysShow(Boolean alwaysShow) {
            this.alwaysShow = alwaysShow;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public void setParams(Map<String, Object> params) {
            this.params = params;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<RouteVO> getChildren() {
        return children;
    }

    public void setChildren(List<RouteVO> children) {
        this.children = children;
    }
} 