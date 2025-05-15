package com.example.mzt_server.mapper;

import com.example.mzt_server.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SysLogMapper {

    /**
     * 分页查询日志
     */
    @Select({
        "<script>",
        "SELECT * FROM sys_log",
        "<where>",
        "<if test='keywords != null and keywords != \"\"'>",
        "  AND (content LIKE CONCAT('%', #{keywords}, '%') ",
        "  OR request_uri LIKE CONCAT('%', #{keywords}, '%') ",
        "  OR method LIKE CONCAT('%', #{keywords}, '%') ",
        "  OR region LIKE CONCAT('%', #{keywords}, '%') ",
        "  OR browser LIKE CONCAT('%', #{keywords}, '%') ",
        "  OR os LIKE CONCAT('%', #{keywords}, '%'))",
        "</if>",
        "<if test='startTime != null'>",
        "  AND create_time &gt;= #{startTime}",
        "</if>",
        "<if test='endTime != null'>",
        "  AND create_time &lt;= #{endTime}",
        "</if>",
        "</where>",
        "ORDER BY create_time DESC",
        "LIMIT #{offset}, #{pageSize}",
        "</script>"
    })
    List<SysLog> queryPage(@Param("keywords") String keywords,
                           @Param("startTime") LocalDateTime startTime,
                           @Param("endTime") LocalDateTime endTime,
                           @Param("offset") Integer offset,
                           @Param("pageSize") Integer pageSize);

    /**
     * 统计总记录数
     */
    @Select({
        "<script>",
        "SELECT COUNT(*) FROM sys_log",
        "<where>",
        "<if test='keywords != null and keywords != \"\"'>",
        "  AND (content LIKE CONCAT('%', #{keywords}, '%') ",
        "  OR request_uri LIKE CONCAT('%', #{keywords}, '%') ",
        "  OR method LIKE CONCAT('%', #{keywords}, '%') ",
        "  OR region LIKE CONCAT('%', #{keywords}, '%') ",
        "  OR browser LIKE CONCAT('%', #{keywords}, '%') ",
        "  OR os LIKE CONCAT('%', #{keywords}, '%'))",
        "</if>",
        "<if test='startTime != null'>",
        "  AND create_time &gt;= #{startTime}",
        "</if>",
        "<if test='endTime != null'>",
        "  AND create_time &lt;= #{endTime}",
        "</if>",
        "</where>",
        "</script>"
    })
    Long count(@Param("keywords") String keywords,
               @Param("startTime") LocalDateTime startTime,
               @Param("endTime") LocalDateTime endTime);
} 