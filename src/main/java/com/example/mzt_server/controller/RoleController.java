package com.example.mzt_server.controller;

import com.example.mzt_server.common.Result;
import com.example.mzt_server.dto.RoleDTO;
import com.example.mzt_server.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 角色控制器
 */
@Tag(name = "角色管理", description = "角色相关接口")
@RestController
@RequestMapping("/role")
public class RoleController {
    
    @Autowired
    private RoleService roleService;
    
    /**
     * 获取角色列表
     */
    @Operation(summary = "获取角色列表")
    @GetMapping("/list")
    public Result<List<RoleDTO>> list() {
        List<RoleDTO> roles = roleService.listRoles();
        return Result.success(roles);
    }
    
    /**
     * 根据ID获取角色详情
     */
    @Operation(summary = "根据ID获取角色详情")
    @GetMapping("/{id}")
    public Result<RoleDTO> getById(@Parameter(description = "角色ID") @PathVariable Integer id) {
        RoleDTO role = roleService.getRoleById(id);
        return role != null ? Result.success(role) : Result.error("角色不存在");
    }
    
    /**
     * 新增角色
     */
    @Operation(summary = "新增角色")
    @PostMapping
    public Result<Boolean> save(@Valid @RequestBody RoleDTO roleDTO) {
        boolean success = roleService.saveRole(roleDTO);
        return success ? Result.success(true) : Result.error("新增角色失败");
    }
    
    /**
     * 更新角色
     */
    @Operation(summary = "更新角色")
    @PutMapping
    public Result<Boolean> update(@Valid @RequestBody RoleDTO roleDTO) {
        if (roleDTO.getId() == null) {
            return Result.error("角色ID不能为空");
        }
        boolean success = roleService.updateRole(roleDTO);
        return success ? Result.success(true) : Result.error("更新角色失败");
    }
    
    /**
     * 删除角色
     */
    @Operation(summary = "删除角色")
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@Parameter(description = "角色ID") @PathVariable Integer id) {
        boolean success = roleService.removeRole(id);
        return success ? Result.success(true) : Result.error("删除角色失败");
    }
} 