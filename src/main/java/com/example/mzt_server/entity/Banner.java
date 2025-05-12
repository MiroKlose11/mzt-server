package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Banner轮播图实体类
 */
@Data
@TableName("banner")
public class Banner {
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    
    /**
     * Banner标题
     */
    private String title;
    
    /**
     * 图片URL
     */
    private String imageUrl;
    
    /**
     * 链接地址
     */
    private String link;
    
    /**
     * Banner位置 (1:顶部Banner, 2:平台介绍Banner)
     */
    private Integer position;
    
    /**
     * 排序号
     */
    private Integer sort;
    
    /**
     * 是否启用 (0:禁用, 1:启用)
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    /**
     * 是否删除 (0:未删除, 1:已删除)
     */
    @TableLogic
    private Integer deleted;
} 