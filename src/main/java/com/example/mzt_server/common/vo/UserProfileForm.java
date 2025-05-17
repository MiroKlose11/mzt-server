package com.example.mzt_server.common.vo;

import lombok.Data;

/**
 * 个人中心用户信息
 */
@Data
public class UserProfileForm {
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 用户昵称
     */
    private String nickname;
    
    /**
     * 头像URL
     */
    private String avatar;
    
    /**
     * 性别
     */
    private Integer gender;
    
    /**
     * 手机号
     */
    private String mobile;
    
    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private String createTime;
    
} 