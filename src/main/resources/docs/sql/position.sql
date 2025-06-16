DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `name` VARCHAR(100) NOT NULL COMMENT '岗位名称，如脂肪、鼻子等',
  `description` TEXT DEFAULT NULL COMMENT '岗位描述',
  `sort` INT DEFAULT 0 COMMENT '排序值，越小越靠前',
  `status` TINYINT(1) DEFAULT 1 COMMENT '状态：1启用，0禁用',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位表';

INSERT INTO `position` (`name`, `description`, `sort`, `status`) VALUES
('脂肪', '脂肪填充、抽脂等相关项目', 1, 1),
('鼻整形', '包含隆鼻、鼻综合等鼻类手术', 2, 1),
('眼整形', '双眼皮、去眼袋等眼部整形', 3, 1);

