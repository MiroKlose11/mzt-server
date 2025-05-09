# 美职通CMS后台管理系统

美职通CMS后台管理系统是一个基于Spring Boot + MyBatis Plus + Spring Security开发的医美行业职业规范与培训平台的后台管理系统。

## 项目介绍

美职通平台是一个全国医美执业规范与职业发展平台，主要致力于医美行业职业发展，包含人才测评、能力认证、职业转型、职业生涯规划、IP打造、教育培训、就业指导、定向委培、劳务派遣等业务。

## 技术栈

- **后端框架**：Spring Boot 3.4.5
- **ORM框架**：MyBatis Plus 3.5.4.1
- **安全框架**：Spring Security
- **数据库**：MySQL 8.0
- **数据库连接池**：Druid 1.2.21
- **项目管理工具**：Maven

## 项目结构

```
cms-mzt
├── src/main/java/com/example/cms_mzt
│   ├── common          // 通用类
│   ├── config          // 配置类
│   ├── controller      // 控制器
│   ├── entity          // 实体类
│   ├── mapper          // 数据访问层
│   ├── service         // 服务接口
│   │   └── impl        // 服务实现
│   └── CmsMztApplication.java  // 启动类
├── src/main/resources
│   ├── mapper          // MyBatis XML映射文件
│   ├── static          // 静态资源
│   ├── templates       // 模板文件
│   ├── application.yml // 应用配置文件
│   └── banner.sql      // 数据库脚本
└── pom.xml             // Maven依赖
```

## 项目启动

### 1. 数据库准备

创建名为`cms_mzt`的数据库，执行`src/main/resources/banner.sql`脚本创建表和初始数据。

```sql
CREATE DATABASE cms_mzt CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
```

### 2. 修改配置

根据实际情况修改`application.yml`中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/cms_mzt?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root
    password: 123456
```

### 3. 启动应用

```bash
mvn spring-boot:run
```

## 接口说明

### 1. Banner接口

#### 顶部Banner轮播图
- 接口路径：`GET /api/home/banners`
- 返回数据：顶部Banner轮播图列表

#### 平台介绍Banner轮播图
- 接口路径：`GET /api/home/introduction-banners`
- 返回数据：平台介绍Banner轮播图列表 