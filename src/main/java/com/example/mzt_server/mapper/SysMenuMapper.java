package com.example.mzt_server.mapper;

import com.example.mzt_server.entity.SysMenu;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    
    /**
     * 查询菜单列表
     */
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
    
    /**
     * 根据菜单类型查询菜单列表
     */
    @Select({
        "<script>",
        "SELECT * FROM sys_menu",
        "<where>",
        "<if test='types != null and types.length > 0'>",
        "  AND type IN",
        "  <foreach collection='types' item='type' open='(' separator=',' close=')'>",
        "    #{type}",
        "  </foreach>",
        "</if>",
        "</where>",
        "ORDER BY sort ASC, id ASC",
        "</script>"
    })
    List<SysMenu> listMenusByTypes(@Param("types") Integer[] types);
    
    /**
     * 根据ID查询菜单
     */
    @Select("SELECT * FROM sys_menu WHERE id = #{id}")
    SysMenu getById(Long id);
    
    /**
     * 查询子菜单数量
     */
    @Select("SELECT COUNT(*) FROM sys_menu WHERE parent_id = #{parentId}")
    int countByParentId(Long parentId);
    
    /**
     * 新增菜单
     */
    @Insert({
        "INSERT INTO sys_menu (parent_id, tree_path, name, type, route_name, route_path, component, perm, always_show, keep_alive, visible, sort, icon, redirect, create_time, update_time, params)",
        "VALUES (",
        "#{parentId}, #{treePath}, #{name}, #{type}, #{routeName}, #{routePath}, #{component}, #{perm}, #{alwaysShow}, #{keepAlive}, #{visible}, #{sort}, #{icon}, #{redirect}, NOW(), NOW(), #{params})",
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SysMenu menu);
    
    /**
     * 更新菜单
     */
    @Update({
        "<script>",
        "UPDATE sys_menu",
        "<set>",
        "  <if test='parentId != null'>parent_id = #{parentId},</if>",
        "  <if test='treePath != null'>tree_path = #{treePath},</if>",
        "  <if test='name != null'>name = #{name},</if>",
        "  <if test='type != null'>type = #{type},</if>",
        "  <if test='routeName != null'>route_name = #{routeName},</if>",
        "  <if test='routePath != null'>route_path = #{routePath},</if>",
        "  <if test='component != null'>component = #{component},</if>",
        "  <if test='perm != null'>perm = #{perm},</if>",
        "  <if test='alwaysShow != null'>always_show = #{alwaysShow},</if>",
        "  <if test='keepAlive != null'>keep_alive = #{keepAlive},</if>",
        "  <if test='visible != null'>visible = #{visible},</if>",
        "  <if test='sort != null'>sort = #{sort},</if>",
        "  <if test='icon != null'>icon = #{icon},</if>",
        "  <if test='redirect != null'>redirect = #{redirect},</if>",
        "  <if test='params != null'>params = #{params},</if>",
        "  update_time = NOW()",
        "</set>",
        "WHERE id = #{id}",
        "</script>"
    })
    int update(SysMenu menu);
    
    /**
     * 更新菜单可见性
     */
    @Update("UPDATE sys_menu SET visible = #{visible}, update_time = NOW() WHERE id = #{menuId}")
    int updateVisible(@Param("menuId") Long menuId, @Param("visible") Integer visible);
    
    /**
     * 删除菜单
     */
    @Delete("DELETE FROM sys_menu WHERE id = #{id}")
    int deleteById(Long id);
    
    /**
     * 批量删除菜单
     */
    @Delete({
        "<script>",
        "DELETE FROM sys_menu WHERE id IN",
        "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
        "  #{id}",
        "</foreach>",
        "</script>"
    })
    int deleteBatchIds(@Param("ids") List<Long> ids);
    
    /**
     * 根据角色ID查询菜单ID列表
     */
    @Select("SELECT menu_id FROM sys_role_menu WHERE role_id = #{roleId}")
    List<Long> selectMenuIdsByRoleId(@Param("roleId") Long roleId);
} 