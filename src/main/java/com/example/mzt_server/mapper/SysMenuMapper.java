package com.example.mzt_server.mapper;

import com.example.mzt_server.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface SysMenuMapper {
    @Select({
        "<script>",
        "SELECT * FROM sys_menu",
        "<where>",
        "<if test='keywords != null and keywords != \"\"'>",
        "  AND name LIKE CONCAT('%', #{keywords}, '%')",
        "</if>",
        "<if test='status != null and status != \"\"'>",
        "  AND visible = #{status}",
        "</if>",
        "</where>",
        "ORDER BY sort ASC, id ASC",
        "</script>"
    })
    List<SysMenu> listMenus(@Param("keywords") String keywords, @Param("status") String status);
} 