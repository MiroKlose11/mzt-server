package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Banner轮播图实体类
 */
@Data
@TableName("banner")
@Schema(description = "轮播图信息")
public class Banner {
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    /**
     * Banner标题
     */
    @Schema(description = "Banner标题", example = "欢迎来到美职通")
    private String title;
    
    /**
     * 图片URL
     */
    @Schema(description = "图片URL", example = "https://example.com/banner.jpg")
    private String imageUrl;
    
    /**
     * 链接地址
     */
    @Schema(description = "链接地址", example = "https://example.com")
    private String link;
    
    /**
     * Banner位置 (1:顶部Banner, 2:平台介绍Banner)
     */
    @Schema(description = "Banner位置", example = "1", allowableValues = {"1", "2"})
    private Integer position;
    
    /**
     * 排序号
     */
    @Schema(description = "排序号，越小越靠前", example = "1")
    private Integer sort;
    
    /**
     * 是否启用 (0:禁用, 1:启用)
     */
    @Schema(description = "是否启用", example = "1", allowableValues = {"0", "1"})
    private Integer status;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updateTime;
    
    /**
     * 是否删除 (0:未删除, 1:已删除)
     */
    @TableLogic
    @Schema(description = "是否删除", hidden = true)
    private Integer deleted;
} 