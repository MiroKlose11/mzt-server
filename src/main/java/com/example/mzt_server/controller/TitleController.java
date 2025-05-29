package com.example.mzt_server.controller;

import com.example.mzt_server.common.Result;
import com.example.mzt_server.dto.TitleDTO;
import com.example.mzt_server.service.TitleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 头衔控制器
 */
@Tag(name = "头衔管理", description = "头衔相关接口")
@RestController
@RequestMapping("/title")
public class TitleController {
    
    @Autowired
    private TitleService titleService;
    
    /**
     * 获取头衔列表
     */
    @Operation(summary = "获取头衔列表")
    @GetMapping("/list")
    public Result<List<TitleDTO>> list(
            @Parameter(description = "类型") @RequestParam(required = false) String type) {
        List<TitleDTO> titles = titleService.listTitles(type);
        return Result.success(titles);
    }
    
    /**
     * 根据ID获取头衔详情
     */
    @Operation(summary = "根据ID获取头衔详情")
    @GetMapping("/{id}")
    public Result<TitleDTO> getById(@Parameter(description = "头衔ID") @PathVariable Integer id) {
        TitleDTO title = titleService.getTitleById(id);
        return title != null ? Result.success(title) : Result.error("头衔不存在");
    }
    
    /**
     * 新增头衔
     */
    @Operation(summary = "新增头衔")
    @PostMapping
    public Result<Boolean> save(@Valid @RequestBody TitleDTO titleDTO) {
        boolean success = titleService.saveTitle(titleDTO);
        return success ? Result.success(true) : Result.error("新增头衔失败");
    }
    
    /**
     * 更新头衔
     */
    @Operation(summary = "更新头衔")
    @PutMapping
    public Result<Boolean> update(@Valid @RequestBody TitleDTO titleDTO) {
        if (titleDTO.getId() == null) {
            return Result.error("头衔ID不能为空");
        }
        boolean success = titleService.updateTitle(titleDTO);
        return success ? Result.success(true) : Result.error("更新头衔失败");
    }
    
    /**
     * 删除头衔
     */
    @Operation(summary = "删除头衔")
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@Parameter(description = "头衔ID") @PathVariable Integer id) {
        boolean success = titleService.removeTitle(id);
        return success ? Result.success(true) : Result.error("删除头衔失败");
    }
} 