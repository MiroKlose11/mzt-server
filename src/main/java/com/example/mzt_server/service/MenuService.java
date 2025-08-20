package com.example.mzt_server.service;

import com.example.mzt_server.common.vo.KeyValue;
import com.example.mzt_server.common.vo.MenuForm;
import com.example.mzt_server.common.vo.MenuVO;
import com.example.mzt_server.common.vo.OptionLong;
import com.example.mzt_server.common.vo.RouteVO;
import com.example.mzt_server.entity.SysMenu;

import java.util.List;

/**
 * 菜单服务接口
 */
public interface MenuService {
    
    /**
     * 获取菜单列表
     * 
     * @param keywords 关键字
     * @param status 状态
     * @return 菜单列表
     */
    List<MenuVO> listMenus(String keywords, String status);
    
    /**
     * 新增菜单
     * 
     * @param form 菜单表单
     * @return 是否成功
     */
    boolean addMenu(MenuForm form);
    
    /**
     * 更新菜单
     * 
     * @param id 菜单ID
     * @param form 菜单表单
     * @return 是否成功
     */
    boolean updateMenu(Long id, MenuForm form);
    
    /**
     * 删除菜单
     * 
     * @param id 菜单ID
     * @return 是否成功
     */
    boolean deleteMenu(Long id);
    
    /**
     * 批量删除菜单
     * 
     * @param ids 菜单ID列表，逗号分隔
     * @return 是否成功
     */
    boolean deleteMenus(String ids);
    
    /**
     * 获取菜单详情
     *
     * @param id 菜单ID
     * @return 菜单详情
     */
    SysMenu getMenuById(Long id);
    
    /**
     * 获取菜单表单数据
     *
     * @param id 菜单ID
     * @return 菜单表单数据
     */
    MenuForm getMenuFormData(Long id);
    
    /**
     * 获取菜单选项列表
     *
     * @param onlyParent 是否只获取父级菜单
     * @return 菜单选项列表
     */
    List<OptionLong> listMenuOptions(Boolean onlyParent);
    
    /**
     * 更新菜单可见性
     *
     * @param menuId 菜单ID
     * @param visible 是否可见
     * @return 是否成功
     */
    boolean updateMenuVisible(Long menuId, Integer visible);
    
    /**
     * 获取角色的菜单ID列表
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    List<Long> getRoleMenuIds(Long roleId);
    
    /**
     * 获取菜单类型选项
     *
     * @return 菜单类型选项列表
     */
    List<KeyValue> getMenuTypeOptions();
    
    /**
     * 获取当前用户的路由列表
     *
     * @return 路由列表
     */
    List<RouteVO> getCurrentUserRoutes();
} 