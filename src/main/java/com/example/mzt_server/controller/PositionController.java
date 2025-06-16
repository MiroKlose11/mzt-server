package com.example.mzt_server.controller;

import com.example.mzt_server.common.Result;
import com.example.mzt_server.dto.PositionDTO;
import com.example.mzt_server.service.PositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 岗位管理控制器
 */
@Tag(name = "岗位管理", description = "岗位的增删改查接口")
@RestController
@RequestMapping("/position")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @Operation(summary = "获取岗位列表")
    @GetMapping("/list")
    public Result<List<PositionDTO>> list() {
        return Result.success(positionService.listAll());
    }

    @Operation(summary = "获取岗位详情")
    @GetMapping("/{id}")
    public Result<PositionDTO> getById(@Parameter(description = "岗位ID") @PathVariable Integer id) {
        PositionDTO dto = positionService.getById(id);
        return dto != null ? Result.success(dto) : Result.error("岗位不存在");
    }

    @Operation(summary = "新增岗位")
    @PostMapping
    public Result<Boolean> save(@Valid @RequestBody PositionDTO dto) {
        boolean success = positionService.savePosition(dto);
        return success ? Result.success(true) : Result.error("新增岗位失败");
    }

    @Operation(summary = "更新岗位")
    @PutMapping
    public Result<Boolean> update(@Valid @RequestBody PositionDTO dto) {
        if (dto.getId() == null) {
            return Result.error("岗位ID不能为空");
        }
        boolean success = positionService.updatePosition(dto);
        return success ? Result.success(true) : Result.error("更新岗位失败");
    }

    @Operation(summary = "删除岗位")
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@Parameter(description = "岗位ID") @PathVariable Integer id) {
        boolean success = positionService.removePosition(id);
        return success ? Result.success(true) : Result.error("删除岗位失败");
    }
} 