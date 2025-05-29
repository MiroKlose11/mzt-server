package com.example.mzt_server.dto;

import com.example.mzt_server.entity.Role;
import com.example.mzt_server.entity.Title;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 成员数据传输对象
 */
@Data
@Schema(description = "成员信息")
public class MemberDTO {
    
    @Schema(description = "成员ID")
    private Integer id;
    
    @Schema(description = "姓名", required = true)
    private String name;
    
    @Schema(description = "头像URL")
    private String avatar;
    
    @Schema(description = "性别 0=未知 1=男 2=女")
    private Integer gender;
    
    @Schema(description = "所属单位/机构")
    private String organization;
    
    @Schema(description = "职业所在地ID")
    private Integer cityId;
    
    @Schema(description = "个人简介")
    private String introduction;
    
    @Schema(description = "状态：1=启用 0=禁用")
    private Integer status;
    
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
    
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
    
    @Schema(description = "角色ID列表")
    private List<Integer> roleIds;
    
    @Schema(description = "头衔ID列表")
    private List<Integer> titleIds;
    
    @Schema(description = "角色信息列表")
    private List<Role> roles;
    
    @Schema(description = "头衔信息列表")
    private List<Title> titles;
} 