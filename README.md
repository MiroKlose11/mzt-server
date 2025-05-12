# 美职通CMS后台管理系统

这是美职通CMS后台管理系统的服务端项目，提供首页相关API接口服务。

## 技术栈

- Spring Boot 3.1.4
- Spring Security
- MyBatis-Plus 3.5.3.1
- MySQL 8.1.0
- Knife4j (Swagger)

## 项目结构

```
src/main/
├── java/com/example/cms_mzt/
│   ├── common/          - 通用工具类和常量
│   ├── config/          - 配置类
│   ├── controller/      - 控制器
│   ├── entity/          - 实体类
│   ├── mapper/          - 数据访问层
│   ├── service/         - 服务层
│   └── CmsMztApplication.java - 应用启动类
├── resources/
    ├── docs/            - 文档
    │   ├── index-api.md - 首页API接口文档
    │   └── sql/         - SQL脚本
    │       ├── banner.sql        - Banner表SQL
    │       └── category_item.sql - 分类条目表SQL
    └── application.yml  - 应用配置文件
```

## 主要功能

- 首页Banner管理
- 首页分类条目管理（服务项目、执业规范平台、主推课程、岗位百科）

## 接口设计

本项目采用REST风格的API设计，所有首页相关接口均通过`/index`统一入口提供：

- `/index/banner/top` - 获取顶部Banner
- `/index/banner/introduction` - 获取平台介绍Banner
- `/index/category?type=xxx` - 获取指定类型的分类数据
- `/index/categories` - 获取所有分类数据
- `/index/all` - 一次性获取所有首页数据

更多接口详情请参考 `docs/index-api.md` 文档。

## 快速开始

1. 创建MySQL数据库并导入SQL脚本：
   ```sql
   -- 创建数据库
   CREATE DATABASE mzt CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
   
   -- 导入SQL脚本
   source /path/to/banner.sql
   source /path/to/category_item.sql
   ```

2. 配置数据库连接信息：
   修改 `application.yml` 中的数据库连接信息：
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/mzt?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
       username: yourUsername
       password: yourPassword
   ```

3. 启动应用：
   ```bash
   mvn spring-boot:run
   ```

4. 访问API文档：
   ```
   http://localhost:8080/doc.html
   ```

## 开发环境

- JDK 21
- Maven 3.8+
- MySQL 8+
- IDE: IntelliJ IDEA 或 Eclipse

## 贡献者

- miro 