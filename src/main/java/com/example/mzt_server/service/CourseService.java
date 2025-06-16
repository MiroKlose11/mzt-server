package com.example.mzt_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mzt_server.dto.CourseDTO;
import com.example.mzt_server.entity.Course;

import java.util.List;

/**
 * 课程服务接口
 */
public interface CourseService extends IService<Course> {
    /**
     * 获取所有课程列表
     * @return 课程DTO列表
     */
    List<CourseDTO> listAll();
    
    /**
     * 根据ID获取课程
     * @param id 课程ID
     * @return 课程DTO
     */
    CourseDTO getById(Integer id);
    
    /**
     * 保存课程
     * @param dto 课程DTO
     * @return 是否成功
     */
    boolean saveCourse(CourseDTO dto);
    
    /**
     * 更新课程
     * @param dto 课程DTO
     * @return 是否成功
     */
    boolean updateCourse(CourseDTO dto);
    
    /**
     * 删除课程
     * @param id 课程ID
     * @return 是否成功
     */
    boolean removeCourse(Integer id);
} 