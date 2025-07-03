CREATE TABLE `user` (
                        `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                        `openid` VARCHAR(64) DEFAULT NULL COMMENT '微信 openid',
                        `unionid` VARCHAR(64) DEFAULT NULL COMMENT '微信 unionid（可跨平台）',
                        `nickname` VARCHAR(100) DEFAULT NULL COMMENT '微信昵称',
                        `avatar` VARCHAR(255) DEFAULT NULL COMMENT '微信头像 URL',
                        `gender` TINYINT UNSIGNED DEFAULT 0 COMMENT '性别：0=未知，1=男，2=女',
                        `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号（用户主动绑定时）',
                        `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                        `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uk_openid` (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';