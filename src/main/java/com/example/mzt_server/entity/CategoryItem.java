package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 首页分类条目实体类
 */
@Data
@TableName("category_item")
@Schema(description = "分类条目信息")
public class CategoryItem {
    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    /** 分类类型（如service/platform/course/job） */
    @Schema(description = "分类类型", example = "service", allowableValues = {"service", "platform", "course", "job"})
    private String categoryType;

    /** 条目名称 */
    @Schema(description = "条目名称", example = "医美咨询")
    private String name;

    /** 图标路径或icon名 */
    @Schema(description = "图标路径或icon名", example = "icon-service")
    private String icon;

    /** 排序号 */
    @Schema(description = "排序号，越小越靠前", example = "1")
    private Integer sort;

    /** 描述 */
    @Schema(description = "描述", example = "提供专业的医美咨询服务")
    private String description;

    /** 是否启用(0:禁用, 1:启用) */
    @Schema(description = "是否启用", example = "1", allowableValues = {"0", "1"})
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updateTime;
} 