package com.example.mzt_server.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mzt_server.dto.StudentDTO;
import com.example.mzt_server.entity.Organization;
import com.example.mzt_server.entity.Student;
import com.example.mzt_server.mapper.OrganizationMapper;
import com.example.mzt_server.mapper.StudentMapper;
import com.example.mzt_server.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 学生服务实现类
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    
    // 移除未使用的字段
    // @Autowired
    // private OrganizationService organizationService;
    
    @Autowired
    private OrganizationMapper organizationMapper;
    
    @Override
    public List<StudentDTO> listAll() {
        return list().stream().map(this::toDTO).collect(Collectors.toList());
    }
    
    @Override
    public IPage<StudentDTO> page(int page, int size, String name, Integer gender, Integer organizationId, Integer userId, Integer status) {
        // 创建分页对象
        Page<Student> studentPage = new Page<>(page, size);
        
        // 使用Mapper中的自定义分页查询方法
        IPage<Student> resultPage = baseMapper.selectStudentPage(studentPage, name, gender, organizationId, userId, status);
        
        // 转换为DTO
        IPage<StudentDTO> dtoPage = resultPage.convert(this::toDTO);
        
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
            Organization org = organizationMapper.selectById(student.getOrganizationId());
            if (org != null) {
                dto.setOrganizationName(org.getName());
            }
        }
        
        return dto;
    }
}