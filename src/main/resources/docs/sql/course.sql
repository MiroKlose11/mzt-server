DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `title` VARCHAR(255) NOT NULL COMMENT '课程标题',
  `description` TEXT DEFAULT NULL COMMENT '课程简介',
  `cover_url` VARCHAR(255) DEFAULT NULL COMMENT '封面图片URL',
  `instructor_id` INT UNSIGNED DEFAULT NULL COMMENT '讲师ID，关联member表',
  `organization_id` INT UNSIGNED DEFAULT NULL COMMENT '所属机构ID，关联organization表',
  `course_type` ENUM('video', 'article', 'live', 'other') DEFAULT 'video' COMMENT '课程类型',
  `video_url` VARCHAR(255) DEFAULT NULL COMMENT '视频播放地址',
  `video_duration` INT UNSIGNED DEFAULT NULL COMMENT '视频时长（单位秒）',
  `price` DECIMAL(10,2) DEFAULT 0.00 COMMENT '价格，0=免费',
  `is_paid` TINYINT(1) DEFAULT 0 COMMENT '是否付费课程，0=免费，1=付费',
  `status` TINYINT(1) DEFAULT 1 COMMENT '状态：1=上架，0=下架',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_instructor_id` (`instructor_id`),
  KEY `idx_organization_id` (`organization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';


INSERT INTO `course` (`title`, `description`, `cover_url`, `instructor_id`, `organization_id`, `course_type`, `video_url`, `video_duration`, `price`, `is_paid`, `status`)
VALUES
('双眼皮整形入门教程', '适合初学者的基础课程，从术前评估到术后护理全流程讲解。', 'https://example.com/cover1.jpg', 1, 1, 'video', 'https://example.com/video1.mp4', 1250, 0.00, 0, 1),
('鼻整形高阶操作技巧', '深入剖析鼻综合整形的操作要点和注意事项。', 'https://example.com/cover2.jpg', 2, 1, 'video', 'https://example.com/video2.mp4', 1780, 299.00, 1, 1),
('脂肪填充实操直播', '医美脂肪移植操作演示，直播回放内容。', 'https://example.com/cover3.jpg', 3, 2, 'live', 'https://example.com/live1.mp4', 3600, 199.00, 1, 1);
