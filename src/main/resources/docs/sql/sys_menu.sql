-- ----------------------------
-- Records of sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
                             `parent_id` bigint NOT NULL COMMENT '父菜单ID',
                             `tree_path` varchar(255) COMMENT '父节点ID路径',
                             `name` varchar(64) NOT NULL COMMENT '菜单名称',
                             `type` tinyint NOT NULL COMMENT '菜单类型（1-菜单 2-目录 3-外链 4-按钮）',
                             `route_name` varchar(255) COMMENT '路由名称（Vue Router 中用于命名路由）',
                             `route_path` varchar(128) COMMENT '路由路径（Vue Router 中定义的 URL 路径）',
                             `component` varchar(128) COMMENT '组件路径（组件页面完整路径，相对于 src/views/，缺省后缀 .vue）',
                             `perm` varchar(128) COMMENT '【按钮】权限标识',
                             `always_show` tinyint DEFAULT 0 COMMENT '【目录】只有一个子路由是否始终显示（1-是 0-否）',
                             `keep_alive` tinyint DEFAULT 0 COMMENT '【菜单】是否开启页面缓存（1-是 0-否）',
                             `visible` tinyint(1) DEFAULT 1 COMMENT '显示状态（1-显示 0-隐藏）',
                             `sort` int DEFAULT 0 COMMENT '排序',
                             `icon` varchar(64) COMMENT '菜单图标',
                             `redirect` varchar(128) COMMENT '跳转路径',
                             `create_time` datetime NULL COMMENT '创建时间',
                             `update_time` datetime NULL COMMENT '更新时间',
                             `params` varchar(255) NULL COMMENT '路由参数',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT = '菜单管理';

INSERT INTO sys_menu VALUES (1, 0, '0', '系统管理', 2, '', '/system', 'Layout', NULL, NULL, NULL, 1, 1, 'system', '/system/user', now(), now(), NULL);
INSERT INTO sys_menu VALUES (2, 1, '0,1', '用户管理', 1, 'User', 'user', 'system/user/index', NULL, NULL, 1, 1, 1, 'el-icon-User', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (3, 1, '0,1', '角色管理', 1, 'Role', 'role', 'system/role/index', NULL, NULL, 1, 1, 2, 'role', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (4, 1, '0,1', '菜单管理', 1, 'SysMenu', 'menu', 'system/menu/index', NULL, NULL, 1, 1, 3, 'menu', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (5, 1, '0,1', '部门管理', 1, 'Dept', 'dept', 'system/dept/index', NULL, NULL, 1, 1, 4, 'tree', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (6, 1, '0,1', '字典管理', 1, 'Dict', 'dict', 'system/dict/index', NULL, NULL, 1, 1, 5, 'dict', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (7, 1, '0,1', '系统日志', 1, 'Log', 'log', 'system/log/index', NULL, 0, 1, 1, 6, 'document', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (8, 1, '0,1', '系统配置', 1, 'Config', 'config', 'system/config/index', NULL, 0, 1, 1, 7, 'setting', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (9, 1, '0,1', '通知公告', 1, 'Notice', 'notice', 'system/notice/index', NULL, NULL, NULL, 1, 8, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (10, 1, '0,1', '字典项', 1, 'DictItem', 'dict-item', 'system/dict/dict-item', NULL, 0, 1, 0, 9, '', NULL, now(), now(), NULL);

-- 用户管理按钮
INSERT INTO sys_menu VALUES (11, 2, '0,1,2', '用户新增', 4, NULL, '', NULL, 'sys:user:add', NULL, NULL, 1, 1, '', '', now(), now(), NULL);
INSERT INTO sys_menu VALUES (12, 2, '0,1,2', '用户编辑', 4, NULL, '', NULL, 'sys:user:edit', NULL, NULL, 1, 2, '', '', now(), now(), NULL);
INSERT INTO sys_menu VALUES (13, 2, '0,1,2', '用户删除', 4, NULL, '', NULL, 'sys:user:delete', NULL, NULL, 1, 3, '', '', now(), now(), NULL);
INSERT INTO sys_menu VALUES (14, 2, '0,1,2', '重置密码', 4, NULL, '', NULL, 'sys:user:reset-password', NULL, NULL, 1, 4, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (15, 2, '0,1,2', '用户查询', 4, NULL, '', NULL, 'sys:user:query', 0, 0, 1, 0, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (16, 2, '0,1,2', '用户导入', 4, NULL, '', NULL, 'sys:user:import', NULL, NULL, 1, 5, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (17, 2, '0,1,2', '用户导出', 4, NULL, '', NULL, 'sys:user:export', NULL, NULL, 1, 6, '', NULL, now(), now(), NULL);

-- 角色管理按钮
INSERT INTO sys_menu VALUES (18, 3, '0,1,3', '角色新增', 4, NULL, '', NULL, 'sys:role:add', NULL, NULL, 1, 2, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (19, 3, '0,1,3', '角色编辑', 4, NULL, '', NULL, 'sys:role:edit', NULL, NULL, 1, 3, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (20, 3, '0,1,3', '角色删除', 4, NULL, '', NULL, 'sys:role:delete', NULL, NULL, 1, 4, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (21, 3, '0,1,3', '角色查询', 4, NULL, '', NULL, 'sys:role:query', NULL, NULL, 1, 1, '', NULL, now(), now(), NULL);

-- 菜单管理按钮
INSERT INTO sys_menu VALUES (22, 4, '0,1,4', '菜单新增', 4, NULL, '', NULL, 'sys:menu:add', NULL, NULL, 1, 1, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (23, 4, '0,1,4', '菜单编辑', 4, NULL, '', NULL, 'sys:menu:edit', NULL, NULL, 1, 3, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (24, 4, '0,1,4', '菜单删除', 4, NULL, '', NULL, 'sys:menu:delete', NULL, NULL, 1, 3, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (25, 4, '0,1,4', '菜单查询', 4, NULL, '', NULL, 'sys:menu:query', NULL, NULL, 1, 1, '', NULL, now(), now(), NULL);

-- 部门管理按钮
INSERT INTO sys_menu VALUES (26, 5, '0,1,5', '部门新增', 4, NULL, '', NULL, 'sys:dept:add', NULL, NULL, 1, 1, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (27, 5, '0,1,5', '部门编辑', 4, NULL, '', NULL, 'sys:dept:edit', NULL, NULL, 1, 2, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (28, 5, '0,1,5', '部门删除', 4, NULL, '', NULL, 'sys:dept:delete', NULL, NULL, 1, 3, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (29, 5, '0,1,5', '部门查询', 4, NULL, '', NULL, 'sys:dept:query', NULL, NULL, 1, 1, '', NULL, now(), now(), NULL);

-- 字典管理按钮
INSERT INTO sys_menu VALUES (30, 6, '0,1,6', '字典新增', 4, NULL, '', NULL, 'sys:dict:add', NULL, NULL, 1, 1, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (31, 6, '0,1,6', '字典编辑', 4, NULL, '', NULL, 'sys:dict:edit', NULL, NULL, 1, 2, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (32, 6, '0,1,6', '字典删除', 4, NULL, '', NULL, 'sys:dict:delete', NULL, NULL, 1, 3, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (33, 6, '0,1,6', '字典查询', 4, NULL, '', NULL, 'sys:dict:query', NULL, NULL, 1, 1, '', NULL, now(), now(), NULL);

-- 字典项管理按钮
INSERT INTO sys_menu VALUES (34, 10, '0,1,10', '字典项新增', 4, NULL, '', NULL, 'sys:dict-item:add', NULL, NULL, 1, 2, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (35, 10, '0,1,10', '字典项编辑', 4, NULL, '', NULL, 'sys:dict-item:edit', NULL, NULL, 1, 3, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (36, 10, '0,1,10', '字典项删除', 4, NULL, '', NULL, 'sys:dict-item:delete', NULL, NULL, 1, 4, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (37, 10, '0,1,10', '字典项查询', 4, NULL, '', NULL, 'sys:dict-item:query', NULL, NULL, 1, 1, '', NULL, now(), now(), NULL);

-- 系统配置按钮
INSERT INTO sys_menu VALUES (38, 8, '0,1,8', '系统配置查询', 4, NULL, '', NULL, 'sys:config:query', 0, 1, 1, 1, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (39, 8, '0,1,8', '系统配置新增', 4, NULL, '', NULL, 'sys:config:add', 0, 1, 1, 2, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (40, 8, '0,1,8', '系统配置修改', 4, NULL, '', NULL, 'sys:config:update', 0, 1, 1, 3, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (41, 8, '0,1,8', '系统配置删除', 4, NULL, '', NULL, 'sys:config:delete', 0, 1, 1, 4, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (42, 8, '0,1,8', '系统配置刷新', 4, NULL, '', NULL, 'sys:config:refresh', 0, 1, 1, 5, '', NULL, now(), now(), NULL);

-- 通知公告按钮
INSERT INTO sys_menu VALUES (43, 9, '0,1,9', '通知查询', 4, NULL, '', NULL, 'sys:notice:query', NULL, NULL, 1, 1, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (44, 9, '0,1,9', '通知新增', 4, NULL, '', NULL, 'sys:notice:add', NULL, NULL, 1, 2, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (45, 9, '0,1,9', '通知编辑', 4, NULL, '', NULL, 'sys:notice:edit', NULL, NULL, 1, 3, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (46, 9, '0,1,9', '通知删除', 4, NULL, '', NULL, 'sys:notice:delete', NULL, NULL, 1, 4, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (47, 9, '0,1,9', '通知发布', 4, NULL, '', NULL, 'sys:notice:publish', 0, 1, 1, 5, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (48, 9, '0,1,9', '通知撤回', 4, NULL, '', NULL, 'sys:notice:revoke', 0, 1, 1, 6, '', NULL, now(), now(), NULL);