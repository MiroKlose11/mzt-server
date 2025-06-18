CREATE TABLE `member` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL COMMENT '姓名',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像 URL',
  `gender` TINYINT UNSIGNED DEFAULT NULL COMMENT '性别 0=未知 1=男 2=女',
  `organization` VARCHAR(255) DEFAULT NULL COMMENT '所属单位/机构',
  `city_id` INT(7) DEFAULT NULL COMMENT '职业所在地ID',
  `introduction` TEXT COMMENT '个人简介',
  `status` TINYINT UNSIGNED DEFAULT 1 COMMENT '状态：1=启用 0=禁用',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_city_id` (`city_id`),
  CONSTRAINT `fk_member_city` FOREIGN KEY (`city_id`) REFERENCES `city`(`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成员表';

--加入一个user_id，通过id查询用户表
ALTER TABLE member ADD COLUMN user_id INT UNSIGNED DEFAULT NULL;
ALTER TABLE `member` ADD CONSTRAINT `fk_member_user` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE SET NULL;

--加入一个organization_id，通过id查询机构表
ALTER TABLE `member`
ADD COLUMN `organization_id` INT UNSIGNED DEFAULT NULL COMMENT '所属机构ID',
ADD CONSTRAINT `fk_member_organization` FOREIGN KEY (`organization_id`) REFERENCES `organization`(`id`) ON DELETE SET NULL ON UPDATE CASCADE,
ADD INDEX `idx_organization_id` (`organization_id`);

CREATE TABLE `role` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(50) NOT NULL COMMENT '唯一标识，如 doctor、lecturer',
  `name` VARCHAR(50) NOT NULL COMMENT '角色名称（中文）',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '说明',
  `sort` SMALLINT UNSIGNED DEFAULT 0 COMMENT '排序值，越大越靠前',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色字典表';

CREATE TABLE `member_role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `member_id` INT UNSIGNED NOT NULL,
  `role_id` TINYINT UNSIGNED NOT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_member_role` (`member_id`, `role_id`),
  KEY `idx_member_id` (`member_id`),
  KEY `idx_role_id` (`role_id`),
  CONSTRAINT `fk_member_role_member` FOREIGN KEY (`member_id`) REFERENCES `member`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_member_role_role` FOREIGN KEY (`role_id`) REFERENCES `role`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成员-角色关联表';

CREATE TABLE `title` (
  `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL COMMENT '头衔名称，如主任医师',
  `type` VARCHAR(50) DEFAULT NULL COMMENT '适用角色类型，可选，如 doctor',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '说明',
  `sort` SMALLINT UNSIGNED DEFAULT 0 COMMENT '排序值，越大越靠前',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='头衔字典表';

CREATE TABLE `member_title` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `member_id` INT UNSIGNED NOT NULL,
  `title_id` SMALLINT UNSIGNED NOT NULL,
  `sort` SMALLINT UNSIGNED DEFAULT 0 COMMENT '自定义显示顺序',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_member_title` (`member_id`, `title_id`),
  KEY `idx_member_id` (`member_id`),
  KEY `idx_title_id` (`title_id`),
  CONSTRAINT `fk_member_title_member` FOREIGN KEY (`member_id`) REFERENCES `member`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_member_title_title` FOREIGN KEY (`title_id`) REFERENCES `title`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成员-头衔关联表';

INSERT INTO `role` (`code`, `name`, `description`, `sort`) VALUES
('doctor', '医生', '具有执业资格的临床医生', 100),
('lecturer', '讲师', '开展授课、培训的专业人员', 90),
('consultant', '咨询师', '提供专业建议的顾问', 80);

INSERT INTO `title` (`name`, `type`, `description`, `sort`) VALUES
('主任医师', 'doctor', '高级医疗职称', 100),
('副主任医师', 'doctor', '中高级医疗职称', 90),
('博士', 'doctor', '获得博士学位者', 80),
('副教授', 'lecturer', '高校中级教师职称', 70);

INSERT INTO `member` (`name`, `gender`, `title_text`, `organization`, `city_id`, `introduction`) VALUES
('张三', 1, '主任医师，博士', '北京协和医院', 110100, '长期从事心血管疾病研究与治疗。'),
('李四', 2, '副主任医师，副教授', '上海瑞金医院', 310100, '擅长糖尿病及内分泌相关疾病治疗。'),
('王五', 1, '博士', '广州南方医科大学', 440100, '医学教育及健康咨询方向专家。');

-- 张三 是 医生
INSERT INTO `member_role` (`member_id`, `role_id`) VALUES (1, 1);
-- 李四 是 医生 + 讲师
INSERT INTO `member_role` (`member_id`, `role_id`) VALUES (2, 1), (2, 2);
-- 王五 是 咨询师 + 讲师
INSERT INTO `member_role` (`member_id`, `role_id`) VALUES (3, 2), (3, 3);


-- 张三 有 主任医师 + 博士
INSERT INTO `member_title` (`member_id`, `title_id`, `sort`) VALUES (1, 1, 1), (1, 3, 2);
-- 李四 有 副主任医师 + 副教授
INSERT INTO `member_title` (`member_id`, `title_id`, `sort`) VALUES (2, 2, 1), (2, 4, 2);
-- 王五 有 博士
INSERT INTO `member_title` (`member_id`, `title_id`, `sort`) VALUES (3, 3, 1);


