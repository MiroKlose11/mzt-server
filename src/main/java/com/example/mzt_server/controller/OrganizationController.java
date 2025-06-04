package com.example.mzt_server.controller;

import com.example.mzt_server.common.Result;
import com.example.mzt_server.dto.OrganizationDTO;
import com.example.mzt_server.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 机构管理控制器
 */
@Tag(name = "机构管理", description = "机构的增删改查接口")
@RestController
@RequestMapping("/organization")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @Operation(summary = "获取机构列表")
    @GetMapping("/list")
    public Result<List<OrganizationDTO>> list() {
        return Result.success(organizationService.listAll());
    }

    @Operation(summary = "获取机构详情")
    @GetMapping("/{id}")
    public Result<OrganizationDTO> getById(@Parameter(description = "机构ID") @PathVariable Integer id) {
        OrganizationDTO dto = organizationService.getById(id);
        return dto != null ? Result.success(dto) : Result.error("机构不存在");
    }

    @Operation(summary = "新增机构")
    @PostMapping
    public Result<Boolean> save(@Valid @RequestBody OrganizationDTO dto) {
        boolean success = organizationService.saveOrganization(dto);
        return success ? Result.success(true) : Result.error("新增机构失败");
    }

    @Operation(summary = "更新机构")
    @PutMapping
    public Result<Boolean> update(@Valid @RequestBody OrganizationDTO dto) {
        if (dto.getId() == null) {
            return Result.error("机构ID不能为空");
        }
        boolean success = organizationService.updateOrganization(dto);
        return success ? Result.success(true) : Result.error("更新机构失败");
    }

    @Operation(summary = "删除机构")
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@Parameter(description = "机构ID") @PathVariable Integer id) {
        boolean success = organizationService.removeOrganization(id);
        return success ? Result.success(true) : Result.error("删除机构失败");
    }
} 