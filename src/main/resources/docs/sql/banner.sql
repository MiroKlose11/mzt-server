-- 创建Banner轮播图表
CREATE TABLE IF NOT EXISTS `banner` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` VARCHAR(64) NOT NULL COMMENT 'Banner标题',
  `image_url` VARCHAR(255) NOT NULL COMMENT '图片URL',
  `link` VARCHAR(255) DEFAULT NULL COMMENT '链接地址',
  `position` INT NOT NULL COMMENT 'Banner位置 (1:顶部Banner, 2:平台介绍Banner)',
  `sort` INT DEFAULT 1 COMMENT '排序号',
  `status` TINYINT DEFAULT 1 COMMENT '是否启用 (0:禁用, 1:启用)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '是否删除 (0:未删除, 1:已删除)',
  PRIMARY KEY (`id`),
  KEY `idx_position` (`position`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Banner轮播图表';

-- 插入测试数据
INSERT INTO `banner` (`title`, `image_url`, `link`, `position`, `sort`, `status`)
VALUES 
('美职通平台', '/static/images/banner1.jpg', 'https://example.com/page1', 1, 1, 1),
('全国医美执业规范平台', '/static/images/banner2.jpg', 'https://example.com/page2', 1, 2, 1),
('线下课程', '/static/images/banner3.jpg', 'https://example.com/page3', 1, 3, 1),
('健身房环境', '/static/images/intro1.jpg', NULL, 2, 1, 1),
('教学场地', '/static/images/intro2.jpg', NULL, 2, 2, 1),
('活动现场', '/static/images/intro3.jpg', NULL, 2, 3, 1); 