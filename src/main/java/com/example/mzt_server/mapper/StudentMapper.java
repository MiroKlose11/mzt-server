package com.example.mzt_server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mzt_server.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 学生Mapper接口
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    
    /**
     * 分页查询学生列表
     *
     * @param page 分页参数
     * @param name 姓名（可选）
     * @param gender 性别（可选）
     * @param organizationId 机构ID（可选）
     * @param userId 用户ID（可选）
     * @param status 状态（可选）
     * @return 分页结果
     */
    IPage<Student> selectStudentPage(Page<Student> page, 
                                    @Param("name") String name,
                                    @Param("gender") Integer gender,
                                    @Param("organizationId") Integer organizationId,
                                    @Param("userId") Integer userId,
                                    @Param("status") Integer status);
} 