package com.example.mzt_server.controller;

import com.example.mzt_server.common.Result;
import com.example.mzt_server.dto.CourseDTO;
import com.example.mzt_server.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 课程管理控制器
 */
@Tag(name = "课程管理", description = "课程的增删改查接口")
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Operation(summary = "获取课程列表")
    @GetMapping("/list")
    public Result<List<CourseDTO>> list() {
        return Result.success(courseService.listAll());
    }

    @Operation(summary = "获取课程详情")
    @GetMapping("/{id}")
    public Result<CourseDTO> getById(@Parameter(description = "课程ID") @PathVariable Integer id) {
        CourseDTO dto = courseService.getById(id);
        return dto != null ? Result.success(dto) : Result.error("课程不存在");
    }

    @Operation(summary = "新增课程")
    @PostMapping
    public Result<Boolean> save(@Valid @RequestBody CourseDTO dto) {
        boolean success = courseService.saveCourse(dto);
        return success ? Result.success(true) : Result.error("新增课程失败");
    }

    @Operation(summary = "更新课程")
    @PutMapping
    public Result<Boolean> update(@Valid @RequestBody CourseDTO dto) {
        if (dto.getId() == null) {
            return Result.error("课程ID不能为空");
        }
        boolean success = courseService.updateCourse(dto);
        return success ? Result.success(true) : Result.error("更新课程失败");
    }

    @Operation(summary = "删除课程")
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@Parameter(description = "课程ID") @PathVariable Integer id) {
        boolean success = courseService.removeCourse(id);
        return success ? Result.success(true) : Result.error("删除课程失败");
    }
} 