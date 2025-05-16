package com.example.mzt_server.service.impl;

import com.example.mzt_server.common.exception.BusinessException;
import com.example.mzt_server.common.exception.ErrorEnum;
import com.example.mzt_server.common.vo.KeyValue;
import com.example.mzt_server.common.vo.MenuForm;
import com.example.mzt_server.common.vo.MenuVO;
import com.example.mzt_server.common.vo.OptionLong;
import com.example.mzt_server.common.vo.RouteVO;
import com.example.mzt_server.entity.SysMenu;
import com.example.mzt_server.mapper.SysMenuMapper;
import com.example.mzt_server.service.IMenuService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单服务实现类
 */
@Service
public class MenuServiceImpl implements IMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<MenuVO> listMenus(String keywords, String status) {
        List<SysMenu> menuList = sysMenuMapper.listMenus(keywords, status);
        // 转为VO
        List<MenuVO> voList = menuList.stream().map(this::toVO).collect(Collectors.toList());
        // 构建树
        return buildTree(voList, 0L);
    }

    private MenuVO toVO(SysMenu menu) {
        MenuVO vo = new MenuVO();
        BeanUtils.copyProperties(menu, vo);
        return vo;
    }

    private List<MenuVO> buildTree(List<MenuVO> list, Long parentId) {
        List<MenuVO> tree = new ArrayList<>();
        for (MenuVO node : list) {
            if (Objects.equals(node.getParentId(), parentId)) {
                List<MenuVO> children = buildTree(list, node.getId());
                node.setChildren(children.isEmpty() ? null : children);
                tree.add(node);
            }
        }
        return tree;
    }

    /**
     * 新增菜单
     * 
     * @param form 菜单表单
     * @return 是否成功
     */
    @Override
    public boolean addMenu(MenuForm form) {
        // 转为实体
        SysMenu menu = toEntity(form);
        
        // 如果是目录类型，自动设置component为"Layout"
        if (form.getType() != null && form.getType() == 2) {
            menu.setComponent("Layout");
        }
        
        // 设置树路径
        if (menu.getParentId() != null && menu.getParentId() > 0) {
            SysMenu parentMenu = sysMenuMapper.getById(menu.getParentId());
            if (parentMenu != null) {
                menu.setTreePath(parentMenu.getTreePath() + "," + parentMenu.getId());
            } else {
                menu.setTreePath("0");
            }
        } else {
            menu.setTreePath("0");
            menu.setParentId(0L); // 确保根菜单的parentId为0
        }
        
        // 处理路由参数
        if (form.getParams() != null && !form.getParams().isEmpty()) {
            menu.setParams(new Gson().toJson(form.getParams()));
        }
        
        // 保存到数据库
        return sysMenuMapper.insert(menu) > 0;
    }
    
    /**
     * 更新菜单
     * 
     * @param id 菜单ID
     * @param form 菜单表单
     * @return 是否成功
     */
    @Override
    public boolean updateMenu(Long id, MenuForm form) {
        // 检查菜单是否存在
        SysMenu existMenu = sysMenuMapper.getById(id);
        if (existMenu == null) {
            throw new BusinessException(ErrorEnum.RESOURCE_NOT_FOUND);
        }
        
        // 转为实体
        SysMenu menu = toEntity(form);
        menu.setId(id);
        
        // 检查上级菜单是否存在且不是自己
        if (menu.getParentId() != null && menu.getParentId() > 0) {
            if (menu.getParentId().equals(id)) {
                throw new BusinessException(ErrorEnum.PARAM_ERROR.getCode(), "上级菜单不能是自己");
            }
            
            SysMenu parentMenu = sysMenuMapper.getById(menu.getParentId());
            if (parentMenu == null) {
                throw new BusinessException(ErrorEnum.PARAM_ERROR.getCode(), "上级菜单不存在");
            }
            
            // 更新树路径
            menu.setTreePath(parentMenu.getTreePath() + "," + parentMenu.getId());
        } else if (menu.getParentId() != null) {
            menu.setTreePath("0");
            menu.setParentId(0L); // 确保根菜单的parentId为0
        }
        
        // 处理路由参数
        if (form.getParams() != null) {
            menu.setParams(new Gson().toJson(form.getParams()));
        }
        
        // 更新到数据库
        return sysMenuMapper.update(menu) > 0;
    }
    
    /**
     * 删除菜单
     * 
     * @param id 菜单ID
     * @return 是否成功
     */
    @Override
    public boolean deleteMenu(Long id) {
        // 检查菜单是否存在
        SysMenu menu = sysMenuMapper.getById(id);
        if (menu == null) {
            throw new BusinessException(ErrorEnum.RESOURCE_NOT_FOUND);
        }
        
        // 检查是否有子菜单
        int childCount = sysMenuMapper.countByParentId(id);
        if (childCount > 0) {
            throw new BusinessException(ErrorEnum.PARAM_ERROR.getCode(), "存在子菜单，不能删除");
        }
        
        // 删除菜单
        return sysMenuMapper.deleteById(id) > 0;
    }
    
    /**
     * 批量删除菜单
     * 
     * @param ids 菜单ID列表，逗号分隔
     * @return 是否成功
     */
    @Override
    public boolean deleteMenus(String ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        
        // 检查是否有子菜单
        for (Long id : idList) {
            int childCount = sysMenuMapper.countByParentId(id);
            if (childCount > 0) {
                throw new BusinessException(ErrorEnum.PARAM_ERROR.getCode(), "菜单ID " + id + " 存在子菜单，不能删除");
            }
        }
        
        // 批量删除
        return sysMenuMapper.deleteBatchIds(idList) > 0;
    }
    
    /**
     * 表单转实体
     */
    private SysMenu toEntity(MenuForm form) {
        SysMenu menu = new SysMenu();
        menu.setId(form.getId());
        menu.setParentId(form.getParentId());
        menu.setName(form.getName());
        menu.setType(form.getType());
        menu.setRouteName(form.getRouteName());
        menu.setRoutePath(form.getRoutePath());
        menu.setComponent(form.getComponent());
        menu.setPerm(form.getPerm());
        menu.setVisible(form.getVisible());
        menu.setSort(form.getSort());
        menu.setIcon(form.getIcon());
        menu.setRedirect(form.getRedirect());
        menu.setKeepAlive(form.getKeepAlive());
        menu.setAlwaysShow(form.getAlwaysShow());
        return menu;
    }

    /**
     * 获取菜单详情
     *
     * @param id 菜单ID
     * @return 菜单详情
     */
    @Override
    public SysMenu getMenuById(Long id) {
        if (id == null) {
            return null;
        }
        return sysMenuMapper.getById(id);
    }

    /**
     * 获取菜单表单数据
     *
     * @param id 菜单ID
     * @return 菜单表单数据
     */
    @Override
    public MenuForm getMenuFormData(Long id) {
        // 如果ID为空，则返回空表单
        if (id == null || id <= 0) {
            return new MenuForm();
        }
        
        // 获取菜单详情
        SysMenu menu = sysMenuMapper.getById(id);
        if (menu == null) {
            return new MenuForm();
        }
        
        // 转换为表单对象
        MenuForm form = new MenuForm();
        form.setId(menu.getId());
        form.setParentId(menu.getParentId());
        form.setName(menu.getName());
        form.setType(menu.getType());
        form.setRouteName(menu.getRouteName());
        form.setRoutePath(menu.getRoutePath());
        form.setComponent(menu.getComponent());
        form.setPerm(menu.getPerm());
        form.setVisible(menu.getVisible());
        form.setSort(menu.getSort());
        form.setIcon(menu.getIcon());
        form.setRedirect(menu.getRedirect());
        form.setKeepAlive(menu.getKeepAlive());
        form.setAlwaysShow(menu.getAlwaysShow());
        
        // 解析路由参数
        if (menu.getParams() != null && !menu.getParams().isEmpty()) {
            try {
                form.setParams(new Gson().fromJson(menu.getParams(), new TypeToken<List<KeyValue>>(){}.getType()));
            } catch (Exception e) {
                // 解析失败，忽略
            }
        }
        
        return form;
    }

    /**
     * 获取菜单选项列表
     *
     * @param onlyParent 是否只获取父级菜单
     * @return 菜单选项列表
     */
    @Override
    public List<OptionLong> listMenuOptions(Boolean onlyParent) {
        // 获取菜单列表
        Integer[] types = null;
        if (Boolean.TRUE.equals(onlyParent)) {
            // 如果只查询父级菜单，则只查询目录和菜单类型
            types = new Integer[]{1, 2}; // 假设1是目录，2是菜单
        }
        
        List<SysMenu> menuList = sysMenuMapper.listMenusByTypes(types);
        if (menuList == null || menuList.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 转换为选项列表
        List<OptionLong> options = menuList.stream()
                .map(menu -> {
                    OptionLong option = new OptionLong();
                    option.setValue(menu.getId());
                    option.setLabel(menu.getName());
                    option.setParentId(menu.getParentId());
                    return option;
                })
                .collect(Collectors.toList());
                
        // 构建树形结构
        if (Boolean.FALSE.equals(onlyParent)) {
            return buildOptionTree(options, 0L);
        }
        
        return options;
    }
    
    /**
     * 构建选项树
     */
    private List<OptionLong> buildOptionTree(List<OptionLong> options, Long parentId) {
        List<OptionLong> tree = new ArrayList<>();
        for (OptionLong option : options) {
            if (Objects.equals(option.getParentId(), parentId)) {
                List<OptionLong> children = buildOptionTree(options, option.getValue());
                if (!children.isEmpty()) {
                    option.setChildren(children);
                }
                tree.add(option);
            }
        }
        return tree;
    }

    /**
     * 更新菜单可见性
     *
     * @param menuId 菜单ID
     * @param visible 是否可见
     * @return 是否成功
     */
    @Override
    public boolean updateMenuVisible(Long menuId, Integer visible) {
        if (menuId == null || visible == null) {
            return false;
        }
        
        return sysMenuMapper.updateVisible(menuId, visible) > 0;
    }

    /**
     * 获取角色的菜单ID列表
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    @Override
    public List<Long> getRoleMenuIds(Long roleId) {
        if (roleId == null) {
            return new ArrayList<>();
        }
        
        return sysMenuMapper.selectMenuIdsByRoleId(roleId);
    }
    
    /**
     * 获取菜单类型选项
     *
     * @return 菜单类型选项列表
     */
    @Override
    public List<KeyValue> getMenuTypeOptions() {
        List<KeyValue> options = new ArrayList<>();
        
        options.add(new KeyValue("1", "目录"));
        options.add(new KeyValue("2", "菜单"));
        options.add(new KeyValue("3", "按钮"));
        
        return options;
    }
    
    /**
     * 获取当前用户的路由列表
     *
     * @return 路由列表
     */
    @Override
    public List<RouteVO> getCurrentUserRoutes() {
        // 从数据库中查询菜单
        List<SysMenu> menuList = sysMenuMapper.listMenus(null, "1"); // 只查询显示状态为1的菜单
        
        // 按照父子关系组织菜单
        List<SysMenu> rootMenus = buildMenuTree(menuList, 0L);
        
        // 转换为路由对象
        return convertMenuToRoutes(rootMenus);
    }
    
    /**
     * 构建菜单树结构
     */
    private List<SysMenu> buildMenuTree(List<SysMenu> menuList, Long parentId) {
        List<SysMenu> treeList = new ArrayList<>();
        for (SysMenu menu : menuList) {
            if (Objects.equals(menu.getParentId(), parentId)) {
                menu.setChildren(buildMenuTree(menuList, menu.getId()));
                treeList.add(menu);
            }
        }
        return treeList;
    }
    
    /**
     * 将菜单树转换为前端路由格式
     */
    private List<RouteVO> convertMenuToRoutes(List<SysMenu> menuList) {
        List<RouteVO> routeList = new ArrayList<>();
        
        for (SysMenu menu : menuList) {
            // 跳过按钮类型(类型为4)
            if (menu.getType() != null && menu.getType() == 4) {
                continue;
            }
            
            RouteVO route = new RouteVO();
            
            // 设置路由基本属性
            route.setPath(menu.getRoutePath());
            route.setComponent(menu.getComponent());
            route.setRedirect(menu.getRedirect());
            route.setName(menu.getRouteName());
            
            // 设置Meta信息
            RouteVO.Meta meta = new RouteVO.Meta();
            meta.setTitle(menu.getName());
            meta.setIcon(menu.getIcon());
            meta.setHidden(menu.getVisible() != null && menu.getVisible() == 0);
            meta.setAlwaysShow(menu.getAlwaysShow() != null && menu.getAlwaysShow() == 1);
            meta.setKeepAlive(menu.getKeepAlive() != null && menu.getKeepAlive() == 1);
            
            // 处理路由参数
            if (menu.getParams() != null && !menu.getParams().isEmpty()) {
                try {
                    Map<String, Object> paramsMap = new Gson().fromJson(
                        menu.getParams(), 
                        new TypeToken<Map<String, Object>>(){}.getType()
                    );
                    meta.setParams(paramsMap);
                } catch (Exception e) {
                    // 解析失败，忽略参数
                }
            }
            
            route.setMeta(meta);
            
            // 处理子路由
            if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
                route.setChildren(convertMenuToRoutes(menu.getChildren()));
            }
            
            routeList.add(route);
        }
        
        return routeList;
    }
} 