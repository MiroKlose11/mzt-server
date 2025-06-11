CREATE TABLE `student` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(100) NOT NULL COMMENT '姓名',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像 URL',
  `gender` TINYINT UNSIGNED DEFAULT NULL COMMENT '性别：0=未知，1=男，2=女',
  `signature` VARCHAR(255) DEFAULT NULL COMMENT '个性签名',
  `organization_id` INT UNSIGNED DEFAULT NULL COMMENT '所属机构ID（可为空）',
  `status` TINYINT UNSIGNED DEFAULT 1 COMMENT '状态：1=启用，0=禁用',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_organization_id` (`organization_id`),
  CONSTRAINT `fk_student_organization` FOREIGN KEY (`organization_id`) REFERENCES `organization`(`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

INSERT INTO `student` (`name`, `avatar`, `gender`, `signature`, `organization_id`)
VALUES 
('张小美', NULL, 2, '热爱美学，追求极致～', NULL),
('李晨阳', NULL, 1, '每天进步一点点！', 3),
('王可欣', NULL, 2, '立志成为高级皮肤管理师', 1);
