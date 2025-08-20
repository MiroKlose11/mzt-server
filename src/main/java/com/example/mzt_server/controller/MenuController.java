package com.example.mzt_server.controller;

import com.example.mzt_server.common.Result;
import com.example.mzt_server.common.vo.RouteVO;
import com.example.mzt_server.common.vo.MenuVO;
import com.example.mzt_server.common.vo.MenuForm;
import com.example.mzt_server.common.vo.OptionLong;
import com.example.mzt_server.entity.SysMenu;
import com.example.mzt_server.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Tag(name = "菜单接口", description = "菜单路由相关接口")
@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Operation(summary = "菜单路由列表", description = "获取当前用户菜单路由列表")
    @GetMapping("/routes")
    public Result<List<RouteVO>> getCurrentUserRoutes(@RequestHeader(value = "Authorization", required = false) String token) {
        List<RouteVO> routes = menuService.getCurrentUserRoutes();
        return Result.success(routes);
    }

    @Operation(summary = "菜单列表", description = "菜单列表")
    @GetMapping("")
    public Result<List<MenuVO>> listMenus(@RequestParam(required = false) String keywords,
                                          @RequestParam(required = false) String status) {
        List<MenuVO> list = menuService.listMenus(keywords, status);
        return Result.success(list);
    }

    @Operation(summary = "新增菜单", description = "新增系统菜单")
    @PostMapping("")
    public Result<Object> addMenu(@Validated @RequestBody MenuForm form) {
        boolean success = menuService.addMenu(form);
        return success ? Result.success() : Result.failed();
    }

    @Operation(summary = "修改菜单", description = "修改系统菜单")
    @PutMapping("/{id}")
    public Result<Object> updateMenu(@PathVariable Long id, @Validated @RequestBody MenuForm form) {
        boolean success = menuService.updateMenu(id, form);
        return success ? Result.success() : Result.failed();
    }

    @Operation(summary = "删除菜单", description = "删除系统菜单")
    @DeleteMapping("/{id}")
    public Result<Object> deleteMenu(@PathVariable String id) {
        // id可能是单个ID或多个ID的逗号分隔字符串
        boolean success = menuService.deleteMenus(id);
        return success ? Result.success() : Result.failed();
    }

    @Operation(summary = "菜单下拉列表", description = "获取菜单下拉列表，支持父级菜单筛选")
    @GetMapping("/options")
    public Result<List<OptionLong>> listMenuOptions(@RequestParam(required = false) Boolean onlyParent) {
        List<OptionLong> options = menuService.listMenuOptions(onlyParent);
        return Result.success(options);
    }

    @Operation(summary = "菜单详情", description = "获取菜单详情")
    @GetMapping("/{id}")
    public Result<SysMenu> getMenuDetail(@PathVariable Long id) {
        SysMenu menu = menuService.getMenuById(id);
        return Result.success(menu);
    }

    @Operation(summary = "菜单表单数据", description = "获取菜单表单数据")
    @GetMapping("/{id}/form")
    public Result<MenuForm> getMenuForm(@PathVariable Long id) {
        MenuForm form = menuService.getMenuFormData(id);
        return Result.success(form);
    }

    @Operation(summary = "修改菜单显示状态", description = "修改菜单显示状态")
    @PatchMapping("/{menuId}")
    public Result<Object> updateMenuVisible(@PathVariable Long menuId, @RequestParam Integer visible) {
        boolean success = menuService.updateMenuVisible(menuId, visible);
        return success ? Result.success() : Result.failed();
    }
}