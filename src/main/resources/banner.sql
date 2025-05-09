-- 创建banner表
CREATE TABLE IF NOT EXISTS `banner` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `title` varchar(100) NOT NULL COMMENT 'Banner标题',
  `image_url` varchar(255) NOT NULL COMMENT '图片URL',
  `link` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `position` tinyint NOT NULL DEFAULT '1' COMMENT 'Banner位置(1:顶部Banner, 2:平台介绍Banner)',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序号',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '是否启用(0:禁用, 1:启用)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除(0:未删除, 1:已删除)',
  PRIMARY KEY (`id`),
  KEY `idx_position_status` (`position`, `status`, `deleted`) COMMENT '位置和状态索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Banner轮播图表';

-- 插入顶部Banner示例数据
INSERT INTO `banner` (`id`, `title`, `image_url`, `link`, `position`, `sort`, `status`, `create_time`, `update_time`, `deleted`)
VALUES
(1, '美职通平台', '/static/images/banner1.jpg', 'https://example.com/page1', 1, 1, 1, NOW(), NOW(), 0),
(2, '全国医美执业规范平台', '/static/images/banner2.jpg', 'https://example.com/page2', 1, 2, 1, NOW(), NOW(), 0);

-- 插入平台介绍Banner示例数据
INSERT INTO `banner` (`id`, `title`, `image_url`, `link`, `position`, `sort`, `status`, `create_time`, `update_time`, `deleted`)
VALUES
(3, '健身房环境', '/static/images/intro1.jpg', NULL, 2, 1, 1, NOW(), NOW(), 0),
(4, '教学场地', '/static/images/intro2.jpg', NULL, 2, 2, 1, NOW(), NOW(), 0); 