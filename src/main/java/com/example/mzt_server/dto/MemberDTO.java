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
    
    @Schema(description = "机构ID，新增/修改时传递")
    private Integer organizationId;
    
    @Schema(description = "备用机构名称，新增/修改时传递")
    private String organization;
    
    @Schema(description = "机构名称（优先显示关联机构，否则显示原字段）", accessMode = Schema.AccessMode.READ_ONLY)
    private String organizationName;
    
    @Schema(description = "职业所在地ID")
    private Integer cityId;
    
    @Schema(description = "个人简介")
    private String introduction;
    
    @Schema(description = "状态：1=启用 0=禁用")
    private Integer status;
    
    @Schema(description = "用户ID，关联系统用户")
    private Integer userId;
    
    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;
    
    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;
    
    @Schema(description = "角色ID列表")
    private List<Integer> roleIds;
    
    @Schema(description = "头衔ID列表")
    private List<Integer> titleIds;
    
    @Schema(description = "角色信息列表", accessMode = Schema.AccessMode.READ_ONLY)
    private List<Role> roles;
    
    @Schema(description = "头衔信息列表", accessMode = Schema.AccessMode.READ_ONLY)
    private List<Title> titles;
} 