-- 机构类型字典表
CREATE TABLE `organization_type` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(50) NOT NULL UNIQUE COMMENT '类型唯一标识，如 hospital、medical_beauty',
  `name` VARCHAR(50) NOT NULL COMMENT '类型名称，如医院、医美机构',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '类型说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='机构类型字典表';

-- 机构表
CREATE TABLE `organization` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL COMMENT '机构名称',
  `type_id` INT UNSIGNED NOT NULL COMMENT '机构类型ID，关联organization_type表',
  `address` VARCHAR(255) DEFAULT NULL COMMENT '机构地址',
  `city_id` INT(7) DEFAULT NULL COMMENT '所在城市ID，关联city表',
  `phone` VARCHAR(50) DEFAULT NULL COMMENT '联系电话',
  `status` TINYINT UNSIGNED DEFAULT 1 COMMENT '状态，1=启用，0=禁用',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_type_id` (`type_id`),
  KEY `idx_city_id` (`city_id`),
  CONSTRAINT `fk_org_type` FOREIGN KEY (`type_id`) REFERENCES `organization_type`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_org_city` FOREIGN KEY (`city_id`) REFERENCES `city`(`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='机构表';


-- 插入机构类型
INSERT INTO `organization_type` (`code`, `name`, `description`) VALUES
('hospital', '医院', '医疗机构'),
('medical_beauty', '医美机构', '医学美容机构'),
('school', '学校', '教育机构'),
('other', '其他', '其他类型机构');

-- 插入机构
INSERT INTO `organization` (`name`, `type_id`, `address`, `city_id`, `phone`) VALUES
('北京三甲医院', 1, '北京市朝阳区某街道123号', 110100, '010-12345678'),
('上海丽人医美', 2, '上海市黄浦区某路45号', 310100, '021-87654321'),
('广州职业技术学院', 3, '广州市天河区学院路88号', 440100, '020-11223344'),
('某未知机构', 4, '未知地址', NULL, NULL);
