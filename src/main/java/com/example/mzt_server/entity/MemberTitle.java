package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 成员-头衔关联实体类
 */
@Data
@TableName("member_title")
public class MemberTitle {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 成员ID
     */
    private Integer memberId;
    
    /**
     * 头衔ID
     */
    private Integer titleId;
    
    /**
     * 自定义显示顺序
     */
    private Integer sort;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
} 