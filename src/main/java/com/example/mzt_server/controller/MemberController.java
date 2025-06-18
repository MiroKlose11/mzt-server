package com.example.mzt_server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.mzt_server.common.Result;
import com.example.mzt_server.dto.MemberDTO;
import com.example.mzt_server.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 成员控制器
 */
@Tag(name = "成员管理", description = "成员相关接口")
@RestController
@RequestMapping("/member")
public class MemberController {
    
    @Autowired
    private MemberService memberService;
    
    /**
     * 分页查询成员列表
     */
    @Operation(summary = "分页查询成员列表")
    @GetMapping("/page")
    public Result<IPage<MemberDTO>> page(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") long current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") long size,
            @Parameter(description = "姓名") @RequestParam(required = false) String name,
            @Parameter(description = "性别 0=未知 1=男 2=女") @RequestParam(required = false) Integer gender,
            @Parameter(description = "职业所在地ID") @RequestParam(required = false) Integer cityId,
            @Parameter(description = "角色ID") @RequestParam(required = false) Integer roleId,
            @Parameter(description = "状态：1=启用 0=禁用") @RequestParam(required = false) Integer status,
            @Parameter(description = "用户ID") @RequestParam(required = false) Integer userId) {
        
        IPage<MemberDTO> page = memberService.pageMember(current, size, name, gender, cityId, roleId, status, userId);
        return Result.success(page);
    }
    
    /**
     * 根据ID获取成员详情
     */
    @Operation(summary = "根据ID获取成员详情")
    @GetMapping("/{id}")
    public Result<MemberDTO> getById(@Parameter(description = "成员ID") @PathVariable Integer id) {
        MemberDTO member = memberService.getMemberById(id);
        return member != null ? Result.success(member) : Result.error("成员不存在");
    }
    
    /**
     * 新增成员
     */
    @Operation(summary = "新增成员")
    @PostMapping
    public Result<Boolean> save(@Valid @RequestBody MemberDTO memberDTO) {
        boolean success = memberService.saveMember(memberDTO);
        return success ? Result.success(true) : Result.error("新增成员失败");
    }
    
    /**
     * 更新成员
     */
    @Operation(summary = "更新成员")
    @PutMapping
    public Result<Boolean> update(@Valid @RequestBody MemberDTO memberDTO) {
        if (memberDTO.getId() == null) {
            return Result.error("成员ID不能为空");
        }
        boolean success = memberService.updateMember(memberDTO);
        return success ? Result.success(true) : Result.error("更新成员失败");
    }
    
    /**
     * 删除成员
     */
    @Operation(summary = "删除成员")
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@Parameter(description = "成员ID") @PathVariable Integer id) {
        boolean success = memberService.removeMember(id);
        return success ? Result.success(true) : Result.error("删除成员失败");
    }
} 