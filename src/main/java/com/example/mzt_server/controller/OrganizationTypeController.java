package com.example.mzt_server.controller;

import com.example.mzt_server.common.Result;
import com.example.mzt_server.dto.OrganizationTypeDTO;
import com.example.mzt_server.service.OrganizationTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 机构类型管理控制器
 */
@Tag(name = "机构类型管理", description = "机构类型的增删改查接口")
@RestController
@RequestMapping("/organization-type")
public class OrganizationTypeController {
    @Autowired
    private OrganizationTypeService organizationTypeService;

    @Operation(summary = "获取机构类型列表")
    @GetMapping("/list")
    public Result<List<OrganizationTypeDTO>> list() {
        return Result.success(organizationTypeService.listAll());
    }

    @Operation(summary = "获取机构类型详情")
    @GetMapping("/{id}")
    public Result<OrganizationTypeDTO> getById(@Parameter(description = "机构类型ID") @PathVariable Integer id) {
        OrganizationTypeDTO dto = organizationTypeService.getById(id);
        return dto != null ? Result.success(dto) : Result.error("机构类型不存在");
    }

    @Operation(summary = "新增机构类型")
    @PostMapping
    public Result<Boolean> save(@Valid @RequestBody OrganizationTypeDTO dto) {
        boolean success = organizationTypeService.saveOrganizationType(dto);
        return success ? Result.success(true) : Result.error("新增机构类型失败，可能是编码已存在");
    }

    @Operation(summary = "更新机构类型")
    @PutMapping
    public Result<Boolean> update(@Valid @RequestBody OrganizationTypeDTO dto) {
        if (dto.getId() == null) {
            return Result.error("机构类型ID不能为空");
        }
        boolean success = organizationTypeService.updateOrganizationType(dto);
        return success ? Result.success(true) : Result.error("更新机构类型失败，可能是编码已存在或ID不存在");
    }

    @Operation(summary = "删除机构类型")
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@Parameter(description = "机构类型ID") @PathVariable Integer id) {
        boolean success = organizationTypeService.removeOrganizationType(id);
        return success ? Result.success(true) : Result.error("删除机构类型失败");
    }
} 