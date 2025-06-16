package com.example.mzt_server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mzt_server.dto.CourseDTO;
import com.example.mzt_server.entity.Course;
import com.example.mzt_server.mapper.CourseMapper;
import com.example.mzt_server.service.CourseService;
import com.example.mzt_server.service.MemberService;
import com.example.mzt_server.service.OrganizationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 课程服务实现类
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    private MemberService memberService;
    
    @Autowired
    private OrganizationService organizationService;
    
    @Override
    public List<CourseDTO> listAll() {
        return list().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public CourseDTO getById(Integer id) {
        Course course = super.getById(id);
        return course == null ? null : toDTO(course);
    }

    @Override
    public boolean saveCourse(CourseDTO dto) {
        Course course = new Course();
        BeanUtils.copyProperties(dto, course);
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());
        if (course.getStatus() == null) course.setStatus(true);
        return save(course);
    }

    @Override
    public boolean updateCourse(CourseDTO dto) {
        Course course = super.getById(dto.getId());
        if (course == null) return false;
        BeanUtils.copyProperties(dto, course);
        course.setUpdatedAt(LocalDateTime.now());
        return updateById(course);
    }

    @Override
    public boolean removeCourse(Integer id) {
        return removeById(id);
    }

    private CourseDTO toDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        BeanUtils.copyProperties(course, dto);
        
        // 设置讲师姓名
        if (course.getInstructorId() != null) {
            dto.setInstructorName(memberService.getById(course.getInstructorId()).getName());
        }
        
        // 设置机构名称
        if (course.getOrganizationId() != null) {
            dto.setOrganizationName(organizationService.getById(course.getOrganizationId()).getName());
        }
        
        return dto;
    }
} 