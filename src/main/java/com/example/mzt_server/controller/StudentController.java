package com.example.mzt_server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.mzt_server.common.Result;
import com.example.mzt_server.dto.StudentDTO;
import com.example.mzt_server.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 学生管理控制器
 */
@Tag(name = "学生管理", description = "学生的增删改查接口")
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Operation(summary = "获取学生列表")
    @GetMapping("/list")
    public Result<List<StudentDTO>> list() {
        return Result.success(studentService.listAll());
    }
    
    @Operation(summary = "分页获取学生列表")
    @GetMapping("/page")
    public Result<IPage<StudentDTO>> page(
            @Parameter(description = "页码，从1开始") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "学生姓名，支持模糊查询") @RequestParam(required = false) String name,
            @Parameter(description = "性别：0=未知，1=男，2=女") @RequestParam(required = false) Integer gender,
            @Parameter(description = "所属机构ID") @RequestParam(required = false) Integer organizationId,
            @Parameter(description = "用户ID") @RequestParam(required = false) Integer userId,
            @Parameter(description = "状态：1=启用，0=禁用") @RequestParam(required = false) Integer status) {
        IPage<StudentDTO> page = studentService.page(current, size, name, gender, organizationId, userId, status);
        return Result.success(page);
    }

    @Operation(summary = "获取学生详情")
    @GetMapping("/{id}")
    public Result<StudentDTO> getById(@Parameter(description = "学生ID") @PathVariable Integer id) {
        StudentDTO dto = studentService.getById(id);
        return dto != null ? Result.success(dto) : Result.error("学生不存在");
    }

    @Operation(summary = "新增学生")
    @PostMapping
    public Result<Boolean> save(@Valid @RequestBody StudentDTO dto) {
        boolean success = studentService.saveStudent(dto);
        return success ? Result.success(true) : Result.error("新增学生失败");
    }

    @Operation(summary = "更新学生")
    @PutMapping
    public Result<Boolean> update(@Valid @RequestBody StudentDTO dto) {
        if (dto.getId() == null) {
            return Result.error("学生ID不能为空");
        }
        boolean success = studentService.updateStudent(dto);
        return success ? Result.success(true) : Result.error("更新学生失败");
    }

    @Operation(summary = "删除学生")
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@Parameter(description = "学生ID") @PathVariable Integer id) {
        boolean success = studentService.removeStudent(id);
        return success ? Result.success(true) : Result.error("删除学生失败");
    }
} 