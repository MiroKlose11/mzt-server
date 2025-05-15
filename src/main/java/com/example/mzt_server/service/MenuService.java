package com.example.mzt_server.service;

import com.example.mzt_server.common.vo.MenuVO;
import com.example.mzt_server.entity.SysMenu;
import com.example.mzt_server.mapper.SysMenuMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

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
} 