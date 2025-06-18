package com.example.mzt_server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mzt_server.dto.StudentDTO;
import com.example.mzt_server.entity.Student;

import java.util.List;

/**
 * 学生服务接口
 */
public interface StudentService extends IService<Student> {
    /**
     * 获取所有学生列表
     * @return 学生DTO列表
     */
    List<StudentDTO> listAll();
    
    /**
     * 分页获取学生列表
     * @param page 页码
     * @param size 每页大小
     * @param name 姓名（可选，模糊查询）
     * @param gender 性别（可选）
     * @param organizationId 机构ID（可选）
     * @param userId 用户ID（可选）
     * @param status 状态（可选）
     * @return 分页学生DTO列表
     */
    IPage<StudentDTO> page(int page, int size, String name, Integer gender, Integer organizationId, Integer userId, Integer status);
    
    /**
     * 根据ID获取学生
     * @param id 学生ID
     * @return 学生DTO
     */
    StudentDTO getById(Integer id);
    
    /**
     * 保存学生
     * @param dto 学生DTO
     * @return 是否成功
     */
    boolean saveStudent(StudentDTO dto);
    
    /**
     * 更新学生
     * @param dto 学生DTO
     * @return 是否成功
     */
    boolean updateStudent(StudentDTO dto);
    
    /**
     * 删除学生
     * @param id 学生ID
     * @return 是否成功
     */
    boolean removeStudent(Integer id);
} 