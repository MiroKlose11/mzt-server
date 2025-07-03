package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 首页分类条目实体类
 */
@Data
@TableName("category_item")
public class CategoryItem {
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 分类类型（如service/platform/course/job） */
    private String categoryType;

    /** 条目名称 */
    private String name;

    /** 图标路径或icon名 */
    private String icon;

    /** 排序号 */
    private Integer sort;

    /** 描述 */
    private String description;

    /** 是否启用(0:禁用, 1:启用) */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
} 