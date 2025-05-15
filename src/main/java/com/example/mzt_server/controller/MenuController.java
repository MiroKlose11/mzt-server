package com.example.mzt_server.controller;

import com.example.mzt_server.common.Result;
import com.example.mzt_server.common.vo.RouteVO;
import com.example.mzt_server.common.vo.MenuVO;
import com.example.mzt_server.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "菜单接口", description = "菜单路由相关接口")
@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Operation(summary = "菜单路由列表", description = "获取当前用户菜单路由列表")
    @GetMapping("/routes")
    public Result<List<RouteVO>> getCurrentUserRoutes(@RequestHeader("Authorization") String token) {
        // 这里只返回静态示例数据，实际可根据用户角色动态生成
        List<RouteVO> routes = new ArrayList<>();
        RouteVO dashboard = new RouteVO();
        dashboard.setPath("/dashboard");
        dashboard.setComponent("dashboard/index");
        dashboard.setName("Dashboard");
        RouteVO.Meta meta = new RouteVO.Meta();
        meta.setTitle("首页");
        meta.setIcon("dashboard");
        dashboard.setMeta(meta);
        routes.add(dashboard);
        // 可继续添加更多路由
        return Result.success(routes);
    }

    @Operation(summary = "菜单列表", description = "菜单列表")
    @GetMapping("")
    public Result<List<MenuVO>> listMenus(@RequestParam(required = false) String keywords,
                                          @RequestParam(required = false) String status) {
        List<MenuVO> list = menuService.listMenus(keywords, status);
        return Result.success(list);
    }
} 