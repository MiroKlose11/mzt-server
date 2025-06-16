package com.example.mzt_server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mzt_server.entity.Course;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程Mapper接口
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
} 