-- 创建访问日志表
CREATE TABLE IF NOT EXISTS `visit_log` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `ip` varchar(50) COMMENT '访问IP',
    `user_agent` varchar(500) COMMENT '用户代理',
    `browser` varchar(50) COMMENT '浏览器',
    `os` varchar(50) COMMENT '操作系统',
    `device` varchar(50) COMMENT '设备类型',
    `request_url` varchar(500) COMMENT '请求URL',
    `visit_time` datetime COMMENT '访问时间',
    `user_id` varchar(50) COMMENT '用户ID',
    `session_id` varchar(100) COMMENT '会话ID',
    `is_first_visit` tinyint(1) DEFAULT 0 COMMENT '是否首次访问',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='访问日志表';

-- 创建访问统计表
CREATE TABLE IF NOT EXISTS `visit_stats` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '统计ID',
    `uv_count` int DEFAULT 0 COMMENT 'UV数量',
    `pv_count` int DEFAULT 0 COMMENT 'PV数量',
    `yesterday_uv_count` int DEFAULT 0 COMMENT '昨日UV数量',
    `yesterday_pv_count` int DEFAULT 0 COMMENT '昨日PV数量',
    `stats_date` date COMMENT '统计日期',
    `update_time` datetime COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_stats_date` (`stats_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='访问统计表';

-- 创建系统日志表
CREATE TABLE IF NOT EXISTS `sys_log` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `module` varchar(50) COMMENT '模块',
    `content` varchar(500) COMMENT '日志内容',
    `request_uri` varchar(500) COMMENT '请求URI',
    `method` varchar(50) COMMENT '请求方法',
    `ip` varchar(50) COMMENT 'IP地址',
    `region` varchar(100) COMMENT '区域',
    `browser` varchar(50) COMMENT '浏览器',
    `os` varchar(50) COMMENT '操作系统',
    `execution_time` bigint COMMENT '执行时间(毫秒)',
    `create_by` bigint COMMENT '创建人ID',
    `create_time` datetime COMMENT '创建时间',
    `operator` varchar(50) COMMENT '操作人',
    PRIMARY KEY (`id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统日志表';

-- 插入访问统计示例数据
INSERT INTO `visit_stats` 
(`uv_count`, `pv_count`, `yesterday_uv_count`, `yesterday_pv_count`, `stats_date`, `update_time`)
VALUES 
(120, 1024, 98, 860, CURDATE(), NOW());

INSERT INTO `visit_stats` 
(`uv_count`, `pv_count`, `yesterday_uv_count`, `yesterday_pv_count`, `stats_date`, `update_time`)
VALUES 
(98, 860, 102, 920, DATE_SUB(CURDATE(), INTERVAL 1 DAY), NOW());

INSERT INTO `visit_stats` 
(`uv_count`, `pv_count`, `yesterday_uv_count`, `yesterday_pv_count`, `stats_date`, `update_time`)
VALUES 
(102, 920, 95, 880, DATE_SUB(CURDATE(), INTERVAL 2 DAY), NOW());

INSERT INTO `visit_stats` 
(`uv_count`, `pv_count`, `yesterday_uv_count`, `yesterday_pv_count`, `stats_date`, `update_time`)
VALUES 
(95, 880, 105, 950, DATE_SUB(CURDATE(), INTERVAL 3 DAY), NOW());

INSERT INTO `visit_stats` 
(`uv_count`, `pv_count`, `yesterday_uv_count`, `yesterday_pv_count`, `stats_date`, `update_time`)
VALUES 
(105, 950, 110, 1100, DATE_SUB(CURDATE(), INTERVAL 4 DAY), NOW());

-- 插入系统日志示例数据
INSERT INTO `sys_log` 
(`module`, `content`, `request_uri`, `method`, `ip`, `region`, `browser`, `os`, `execution_time`, `create_by`, `create_time`, `operator`)
VALUES 
('LOGIN', '用户登录', '/auth/login', 'POST', '192.168.1.1', '北京', 'Chrome', 'Windows 10', 128, 1, NOW(), 'admin');

INSERT INTO `sys_log` 
(`module`, `content`, `request_uri`, `method`, `ip`, `region`, `browser`, `os`, `execution_time`, `create_by`, `create_time`, `operator`)
VALUES 
('USER', '创建用户', '/user', 'POST', '192.168.1.2', '上海', 'Firefox', 'macOS', 245, 1, NOW(), 'admin');

INSERT INTO `sys_log` 
(`module`, `content`, `request_uri`, `method`, `ip`, `region`, `browser`, `os`, `execution_time`, `create_by`, `create_time`, `operator`)
VALUES 
('ROLE', '更新角色', '/role/1', 'PUT', '192.168.1.3', '广州', 'Edge', 'Windows 11', 158, 1, NOW(), 'admin');

INSERT INTO `sys_log` 
(`module`, `content`, `request_uri`, `method`, `ip`, `region`, `browser`, `os`, `execution_time`, `create_by`, `create_time`, `operator`)
VALUES 
('MENU', '删除菜单', '/menu/5', 'DELETE', '192.168.1.4', '深圳', 'Safari', 'iOS', 98, 1, NOW(), 'admin');

INSERT INTO `sys_log` 
(`module`, `content`, `request_uri`, `method`, `ip`, `region`, `browser`, `os`, `execution_time`, `create_by`, `create_time`, `operator`)
VALUES 
('EXCEPTION', '系统异常', '/api/error', 'GET', '192.168.1.5', '杭州', 'Chrome', 'Android', 356, 2, NOW(), 'test_user'); 