package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 小程序用户实体类
 */
@Data
@TableName("user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 微信openid
     */
    private String openid;
    
    /**
     * 微信unionid（可跨平台）
     */
    private String unionid;
    
    /**
     * 微信昵称
     */
    private String nickname;
    
    /**
     * 微信头像URL
     */
    private String avatar;
    
    /**
     * 性别（0=未知，1=男，2=女）
     */
    private Integer gender;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 密码（账号密码登录时使用）
     */
    private String password;
    
    /**
     * 用户名（账号密码登录时使用）
     */
    private String username;
    
    /**
     * 认证类型（none=未认证，personal=个人认证，enterprise=企业认证）
     */
    private String certificationType;
    
    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}