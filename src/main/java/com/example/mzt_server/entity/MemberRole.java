package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 成员-角色关联实体类
 */
@Data
@TableName("member_role")
public class MemberRole {
    
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
     * 角色ID
     */
    private Integer roleId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
} 