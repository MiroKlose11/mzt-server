package com.example.mzt_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mzt_server.dto.StudentDTO;
import com.example.mzt_server.entity.Student;
import com.example.mzt_server.mapper.StudentMapper;
import com.example.mzt_server.service.OrganizationService;
import com.example.mzt_server.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 学生服务实现类
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    
    @Autowired
    private OrganizationService organizationService;
    
    @Override
    public List<StudentDTO> listAll() {
        return list().stream().map(this::toDTO).collect(Collectors.toList());
    }
    
    @Override
    public Page<StudentDTO> page(int page, int size, String name, Integer organizationId) {
        // 构建查询条件
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        
        // 姓名模糊查询
        if (StringUtils.hasText(name)) {
            queryWrapper.like(Student::getName, name);
        }
        
        // 机构ID精确查询
        if (organizationId != null) {
            queryWrapper.eq(Student::getOrganizationId, organizationId);
        }
        
        // 按创建时间降序排序
        queryWrapper.orderByDesc(Student::getCreatedAt);
        
        // 执行分页查询
        Page<Student> studentPage = new Page<>(page, size);
        Page<Student> resultPage = page(studentPage, queryWrapper);
        
        // 转换为DTO
        Page<StudentDTO> dtoPage = new Page<>(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal());
        List<StudentDTO> dtoList = resultPage.getRecords().stream().map(this::toDTO).collect(Collectors.toList());
        dtoPage.setRecords(dtoList);
        
        return dtoPage;
    }
    
    @Override
    public StudentDTO getById(Integer id) {
        Student student = super.getById(id);
        return student == null ? null : toDTO(student);
    }
    
    @Override
    public boolean saveStudent(StudentDTO dto) {
        Student student = new Student();
        BeanUtils.copyProperties(dto, student);
        
        // 设置默认值
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        if (student.getStatus() == null) {
            student.setStatus(1);
        }
        
        return save(student);
    }
    
    @Override
    public boolean updateStudent(StudentDTO dto) {
        Student student = super.getById(dto.getId());
        if (student == null) {
            return false;
        }
        
        BeanUtils.copyProperties(dto, student);
        student.setUpdatedAt(LocalDateTime.now());
        
        return updateById(student);
    }
    
    @Override
    public boolean removeStudent(Integer id) {
        return removeById(id);
    }
    
    /**
     * 将实体转换为DTO
     * @param student 学生实体
     * @return 学生DTO
     */
    private StudentDTO toDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        BeanUtils.copyProperties(student, dto);
        
        // 设置机构名称
        if (student.getOrganizationId() != null) {
            dto.setOrganizationName(organizationService.getById(student.getOrganizationId()).getName());
        }
        
        return dto;
    }
} 