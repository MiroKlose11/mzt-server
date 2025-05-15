-- 系统用户表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `username` varchar(64) COMMENT '用户名',
                             `nickname` varchar(64) COMMENT '昵称',
                             `gender` tinyint(1) DEFAULT 1 COMMENT '性别((1-男 2-女 0-保密)',
                             `password` varchar(100) COMMENT '密码',
                             `dept_id` int COMMENT '部门ID',
                             `avatar` varchar(255) COMMENT '用户头像',
                             `mobile` varchar(20) COMMENT '联系方式',
                             `status` tinyint(1) DEFAULT 1 COMMENT '状态(1-正常 0-禁用)',
                             `email` varchar(128) COMMENT '用户邮箱',
                             `create_time` datetime COMMENT '创建时间',
                             `create_by` bigint COMMENT '创建人ID',
                             `update_time` datetime COMMENT '更新时间',
                             `update_by` bigint COMMENT '修改人ID',
                             `is_deleted` tinyint(1) DEFAULT 0 COMMENT '逻辑删除标识(0-未删除 1-已删除)',
                             `openid` char(28) COMMENT '微信 openid',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `login_name`(`username` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT = '用户信息表';

-- 角色表
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `name` varchar(64) NOT NULL COMMENT '角色名称',
                             `code` varchar(32) NOT NULL COMMENT '角色编码',
                             `sort` int NULL COMMENT '显示顺序',
                             `status` tinyint(1) DEFAULT 1 COMMENT '角色状态(1-正常 0-停用)',
                             `data_scope` tinyint NULL COMMENT '数据权限(1-所有数据 2-部门及子部门数据 3-本部门数据 4-本人数据)',
                             `create_by` bigint NULL COMMENT '创建人 ID',
                             `create_time` datetime NULL COMMENT '创建时间',
                             `update_by` bigint NULL COMMENT '更新人ID',
                             `update_time` datetime NULL COMMENT '更新时间',
                             `is_deleted` tinyint(1) DEFAULT 0 COMMENT '逻辑删除标识(0-未删除 1-已删除)',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `uk_name`(`name` ASC) USING BTREE COMMENT '角色名称唯一索引',
                             UNIQUE INDEX `uk_code`(`code` ASC) USING BTREE COMMENT '角色编码唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT = '角色表';

-- 用户角色关联表
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
                                  `user_id` bigint NOT NULL COMMENT '用户ID',
                                  `role_id` bigint NOT NULL COMMENT '角色ID',
                                  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT = '用户和角色关联表';

-- 权限表
CREATE TABLE IF NOT EXISTS `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `name` varchar(50) NOT NULL COMMENT '权限名称',
  `permission` varchar(100) NOT NULL COMMENT '权限标识',
  `type` tinyint(4) NOT NULL COMMENT '类型(1:菜单;2:按钮)',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父权限ID',
  `path` varchar(200) DEFAULT NULL COMMENT '路由路径',
  `component` varchar(200) DEFAULT NULL COMMENT '组件路径',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `sort` int(11) DEFAULT '0' COMMENT '显示顺序',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态(0:禁用;1:正常)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_permission` (`permission`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 角色权限关联表
CREATE TABLE IF NOT EXISTS `sys_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_permission` (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 部门表
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `name` varchar(100) NOT NULL COMMENT '部门名称',
                             `code` varchar(100) NOT NULL COMMENT '部门编号',
                             `parent_id` bigint DEFAULT 0 COMMENT '父节点id',
                             `tree_path` varchar(255) NOT NULL COMMENT '父节点id路径',
                             `sort` smallint DEFAULT 0 COMMENT '显示顺序',
                             `status` tinyint DEFAULT 1 COMMENT '状态(1-正常 0-禁用)',
                             `create_by` bigint NULL COMMENT '创建人ID',
                             `create_time` datetime NULL COMMENT '创建时间',
                             `update_by` bigint NULL COMMENT '修改人ID',
                             `update_time` datetime NULL COMMENT '更新时间',
                             `is_deleted` tinyint DEFAULT 0 COMMENT '逻辑删除标识(1-已删除 0-未删除)',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `uk_code`(`code` ASC) USING BTREE COMMENT '部门编号唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT = '部门表';

-- 验证码表
CREATE TABLE IF NOT EXISTS `sys_captcha` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `captcha_key` varchar(50) NOT NULL COMMENT '验证码key',
  `captcha_code` varchar(10) NOT NULL COMMENT '验证码',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_captcha_key` (`captcha_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='验证码表';

-- 操作日志表
CREATE TABLE IF NOT EXISTS `sys_operation_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '操作',
  `method` varchar(100) DEFAULT NULL COMMENT '请求方法',
  `params` text COMMENT '请求参数',
  `time` bigint(20) DEFAULT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- 登录日志表
CREATE TABLE IF NOT EXISTS `sys_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `status` tinyint(4) DEFAULT NULL COMMENT '登录状态(0:失败;1:成功)',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '用户代理',
  `error_msg` varchar(255) DEFAULT NULL COMMENT '错误消息',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志表';

-- 初始化数据
-- 插入管理员用户(密码: 123456)
INSERT INTO `sys_user` 
VALUES (1, 'root', '有来技术', 0, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', NULL, 'https://foruda.gitee.com/images/1723603502796844527/03cdca2a_716974.gif', '18812345677', 1, 'youlaitech@163.com', now(), NULL, now(), NULL, 0,NULL);

-- 插入角色
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'ROOT', 1, 1, 1, NULL, now(), NULL, now(), 0);

-- 关联用户和角色
INSERT INTO `sys_user_role` (`user_id`, `role_id`) 
VALUES (1, 1);

-- 插入权限
INSERT INTO `sys_permission` (`name`, `permission`, `type`, `path`, `component`, `icon`, `sort`, `status`) 
VALUES 
('系统管理', 'system', 1, '/system', 'Layout', 'system', 1, 1),
('用户管理', 'system:user', 1, '/system/user', 'system/user/index', 'user', 1, 1),
('角色管理', 'system:role', 1, '/system/role', 'system/role/index', 'role', 2, 1),
('菜单管理', 'system:menu', 1, '/system/menu', 'system/menu/index', 'menu', 3, 1);

-- 关联角色和权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`) 
VALUES 
(1, 1), (1, 2), (1, 3), (1, 4); 