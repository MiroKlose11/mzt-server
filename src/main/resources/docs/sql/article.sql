-- 文章主表
CREATE TABLE article (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '文章ID',
  title VARCHAR(255) NOT NULL COMMENT '文章标题',
  cover_image VARCHAR(512) DEFAULT NULL COMMENT '封面图地址',
  description TEXT COMMENT '简要描述',
  weight INT DEFAULT 0 COMMENT '排序权重，值越大越靠前',
  status TINYINT DEFAULT 0 COMMENT '状态：0=草稿，1=待审核，2=已发布，3=已驳回',
  is_guest_visible TINYINT DEFAULT 1 COMMENT '是否允许未登录用户查看：1=允许，0=不允许',
  channel_id BIGINT DEFAULT NULL COMMENT '主栏目ID',
  author_id BIGINT NOT NULL COMMENT '作者ID（管理员或用户）',
  author_type TINYINT NOT NULL COMMENT '作者类型：0=管理员，1=用户',
  views INT DEFAULT 0 COMMENT '浏览量',
  likes INT DEFAULT 0 COMMENT '点赞数',
  comments INT DEFAULT 0 COMMENT '评论数',
  createtime DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updatetime DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  publishtime DATETIME DEFAULT NULL COMMENT '发布时间',
  deletetime DATETIME DEFAULT NULL COMMENT '逻辑删除时间',
  INDEX idx_channel (channel_id),
  INDEX idx_status (status)
) COMMENT='文章主表';

-- 文章内容表
CREATE TABLE article_content (
  article_id BIGINT PRIMARY KEY COMMENT '文章ID',
  content LONGTEXT COMMENT '文章富文本内容'
) COMMENT='文章内容表';

-- 栏目表
CREATE TABLE channel (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '栏目ID',
  name VARCHAR(100) NOT NULL COMMENT '栏目名称',
  description TEXT COMMENT '栏目描述',
  createtime DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT='文章栏目表';

-- 点赞记录表
CREATE TABLE article_like (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  article_id BIGINT NOT NULL COMMENT '文章ID',
  user_id BIGINT DEFAULT NULL COMMENT '用户ID（登录用户）',
  fingerprint VARCHAR(128) DEFAULT NULL COMMENT '访客唯一标识',
  createtime DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
  UNIQUE KEY unique_like (article_id, user_id, fingerprint)
) COMMENT='文章点赞记录';

-- 评论表
CREATE TABLE article_comment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  article_id BIGINT NOT NULL COMMENT '文章ID',
  user_id BIGINT DEFAULT NULL COMMENT '用户ID',
  nickname VARCHAR(100) DEFAULT NULL COMMENT '昵称（访客用）',
  content TEXT NOT NULL COMMENT '评论内容',
  status TINYINT DEFAULT 1 COMMENT '状态：0=隐藏，1=正常',
  createtime DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  INDEX idx_article (article_id)
) COMMENT='文章评论表';

-- 插入测试栏目
INSERT INTO channel (name, description) VALUES
('新闻资讯', '最新新闻相关内容'),
('技术分享', '开发技术文章');

-- 插入测试文章
INSERT INTO article (title, cover_image, description, weight, status, is_guest_visible, channel_id, author_id, author_type)
VALUES
('第一篇文章', 'https://example.com/image1.jpg', '这是一篇测试文章', 10, 2, 1, 1, 1001, 1),
('第二篇文章', 'https://example.com/image2.jpg', '第二篇文章简介', 5, 1, 1, 2, 1002, 0);

-- 插入文章内容
INSERT INTO article_content (article_id, content) VALUES
(1, '<p>这里是第一篇文章的正文内容</p>'),
(2, '<p>这里是第二篇文章的正文内容</p>');

-- 插入点赞记录
INSERT INTO article_like (article_id, user_id, fingerprint) VALUES
(1, 1001, NULL),
(2, NULL, 'abc123xyz');

-- 插入评论记录
INSERT INTO article_comment (article_id, user_id, nickname, content) VALUES
(1, 1001, NULL, '写得真好！'),
(1, NULL, '匿名用户', '支持一下');
