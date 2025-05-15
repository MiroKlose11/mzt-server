package com.example.mzt_server.common.vo;

import java.util.List;

/**
 * 分页结果
 */
public class PageResult<T> {
    private List<T> list; // 数据列表
    private Long total;   // 总记录数

    public PageResult() {
    }

    public PageResult(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
} 