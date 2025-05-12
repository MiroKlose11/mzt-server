-- 首页分类条目表
CREATE TABLE IF NOT EXISTS `category_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `category_type` VARCHAR(32) NOT NULL COMMENT '分类类型（如service/platform/course/job）',
  `name` VARCHAR(64) NOT NULL COMMENT '条目名称',
  `icon` VARCHAR(128) DEFAULT NULL COMMENT '图标路径或icon名',
  `sort` INT DEFAULT 0 COMMENT '排序号',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '描述',
  `status` TINYINT DEFAULT 1 COMMENT '是否启用(0:禁用, 1:启用)',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_category_type` (`category_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='首页分类条目表';

-- 示例数据：我们的服务项目
INSERT INTO `category_item` (`category_type`, `name`, `icon`, `sort`, `description`) VALUES
('service', '医美综合培训', 'icon1.png', 1, NULL),
('service', '医美专科培训', 'icon2.png', 2, NULL),
('service', '职业生涯规划', 'icon3.png', 3, NULL),
('service', '临床定向委培', 'icon4.png', 4, NULL),
('service', '临床就业指导', 'icon5.png', 5, NULL),
('service', '人才交流活动', 'icon6.png', 6, NULL);

-- 示例数据：执业规范与专科化发展平台
INSERT INTO `category_item` (`category_type`, `name`, `icon`, `sort`, `description`) VALUES
('platform', '临床院校', 'icon7.png', 1, NULL),
('platform', '医美机构', 'icon8.png', 2, NULL),
('platform', '医美岗位', 'icon9.png', 3, NULL),
('platform', '医美课程', 'icon10.png', 4, NULL),
('platform', '师资队伍', 'icon11.png', 5, NULL),
('platform', '毕业生', 'icon12.png', 6, NULL),
('platform', '优秀学员', 'icon13.png', 7, NULL),
('platform', '能力认证', 'icon14.png', 8, NULL);

-- 示例数据：主推课程
INSERT INTO `category_item` (`category_type`, `name`, `icon`, `sort`, `description`) VALUES
('course', '抗衰整形', 'icon15.png', 1, NULL),
('course', '鼻整形', 'icon16.png', 2, NULL),
('course', '脂肪整形', 'icon17.png', 3, NULL),
('course', '...', 'icon18.png', 4, NULL);

-- 示例数据：医美行业岗位百科
INSERT INTO `category_item` (`category_type`, `name`, `icon`, `sort`, `description`) VALUES
('job', '医生技术岗', 'icon19.png', 1, NULL),
('job', '销售咨询岗', 'icon20.png', 2, NULL),
('job', '服务支持岗', 'icon21.png', 3, NULL),
('job', '运营营销岗', 'icon22.png', 4, NULL),
('job', '行政职能类', 'icon23.png', 5, NULL),
('job', '其他延伸岗', 'icon24.png', 6, NULL); 