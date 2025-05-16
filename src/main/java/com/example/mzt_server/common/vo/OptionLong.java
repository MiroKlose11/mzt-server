package com.example.mzt_server.common.vo;

import java.util.List;

/**
 * 下拉选项对象
 */
public class OptionLong {
    private Long value;           // 选项的值
    private String label;         // 选项的标签
    private String tag;           // 标签类型
    private Long parentId;        // 父选项ID
    private List<OptionLong> children;  // 子选项列表

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<OptionLong> getChildren() {
        return children;
    }

    public void setChildren(List<OptionLong> children) {
        this.children = children;
    }
} 