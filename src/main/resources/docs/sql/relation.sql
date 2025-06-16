DROP TABLE IF EXISTS `member_position`;
CREATE TABLE `member_position` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `member_id` INT UNSIGNED NOT NULL COMMENT '成员ID',
  `position_id` INT UNSIGNED NOT NULL COMMENT '岗位ID',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_member_position` (`member_id`, `position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成员与岗位关联表';


DROP TABLE IF EXISTS `course_position`;
CREATE TABLE `course_position` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `course_id` INT UNSIGNED NOT NULL COMMENT '课程ID',
  `position_id` INT UNSIGNED NOT NULL COMMENT '岗位ID',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_course_position` (`course_id`, `position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程与岗位关联表';


DROP TABLE IF EXISTS `student_member`;
CREATE TABLE `student_member` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `student_id` INT UNSIGNED NOT NULL COMMENT '学生ID',
  `member_id` INT UNSIGNED NOT NULL COMMENT '老师ID',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_student_member` (`student_id`, `member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生与老师关联表';


DROP TABLE IF EXISTS `student_course`;
CREATE TABLE `student_course` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `student_id` INT UNSIGNED NOT NULL COMMENT '学生ID',
  `course_id` INT UNSIGNED NOT NULL COMMENT '课程ID',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_student_course` (`student_id`, `course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生与课程报名表';
