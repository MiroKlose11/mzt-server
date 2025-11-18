# MZT后端API完整文档

## 概述

本文档为MZT项目后端API接口完整文档，包含所有业务模块的接口说明。

## 基础信息

### 服务器配置
- **基础URL**: `http://localhost:8080`
- **内容类型**: `application/json`
- **字符编码**: `UTF-8`

### 通用响应格式

```json
{
  "code": "200",
  "message": "操作成功",
  "data": {},
  "timestamp": "2024-01-20T10:30:00"
}
```

### 认证说明
除公开接口外，其他接口需要在请求头中携带JWT令牌：
```
Authorization: Bearer {accessToken}
```

---

## 目录

1. [认证管理](#一认证管理)
2. [用户管理](#二用户管理)
3. [成员管理](#三成员管理)
4. [学生管理](#四学生管理)
5. [文章管理](#五文章管理)
6. [轮播图管理](#六轮播图管理)
7. [分类条目管理](#七分类条目管理)
8. [首页接口](#八首页接口)
9. [课程管理](#九课程管理)
10. [机构管理](#十机构管理)
11. [机构类型管理](#十一机构类型管理)
12. [岗位管理](#十二岗位管理)
13. [角色管理](#十三角色管理)
14. [头衔管理](#十四头衔管理)
15. [城市数据](#十五城市数据)
16. [文件管理](#十六文件管理)
17. [菜单管理](#十七菜单管理)
18. [错误码说明](#十八错误码说明)

---

## 一、认证管理

### 1.1 后台管理系统登录

**接口地址**: `POST /auth/login`

**请求参数**:
```json
{
  "username": "admin",
  "password": "123456",
  "captchaKey": "8f7e6d5c4b3a2910",
  "captchaCode": "a1b2",
  "rememberMe": false
}
```

**参数说明**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| username | String | 是 | 用户名 |
| password | String | 是 | 密码 |
| captchaKey | String | 是 | 验证码key |
| captchaCode | String | 是 | 验证码 |
| rememberMe | Boolean | 否 | 记住我 |

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "expiresIn": 86400
  }
}
```

### 1.2 获取验证码

**接口地址**: `GET /auth/captcha`

**请求参数**: 无

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": {
    "captchaKey": "8f7e6d5c4b3a2910",
    "captchaImage": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA..."
  }
}
```

### 1.3 微信小程序登录

**接口地址**: `POST /auth/wechat-login`

**请求参数**:
```json
{
  "code": "0f3f4d8c7b2a19e0",
  "nickName": "微信用户",
  "avatarUrl": "https://example.com/avatar.jpg",
  "encryptedData": "CiyLU1Aw2KjvrjMdj...",
  "iv": "r7BXXKkLb8qrSNn05n0qiA=="
}
```

**参数说明**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| code | String | 是 | 微信小程序登录code |
| nickName | String | 否 | 用户昵称 |
| avatarUrl | String | 否 | 用户头像URL |
| encryptedData | String | 否 | 手机号加密数据 |
| iv | String | 否 | 加密算法的初始向量 |

### 1.4 小程序多方式登录

**接口地址**: `POST /auth/miniapp-login`

**支持的登录类型**:
1. 微信登录 (loginType: "wechat")
2. 短信验证码登录 (loginType: "sms")
3. 账号密码登录 (loginType: "password")

**请求参数示例 - 短信登录**:
```json
{
  "loginType": "sms",
  "phone": "13800138000",
  "smsCode": "123456",
  "smsKey": "sms_1705123456789"
}
```

**请求参数示例 - 密码登录**:
```json
{
  "loginType": "password",
  "username": "user123",
  "password": "123456"
}
```

### 1.5 发送短信验证码

**接口地址**: `POST /auth/send-sms`

**请求参数**: 
```json
{
  "phone": "13800138000"
}
```

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": "sms_1705123456789"
}
```

### 1.6 手机号快速注册

**接口地址**: `POST /auth/register`

**请求参数**:
```json
{
  "phone": "13800138000",
  "smsCode": "123456",
  "password": "123456",
  "smsKey": "sms_1705123456789"
}
```

**参数说明**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| phone | String | 是 | 手机号码 |
| smsCode | String | 是 | 6位短信验证码 |
| password | String | 是 | 密码，6-20位 |
| smsKey | String | 是 | 验证码key |

### 1.7 发送重置密码验证码

**接口地址**: `POST /auth/send-reset-sms`

**请求参数**:
```json
{
  "phone": "13800138000"
}
```

### 1.8 重置密码

**接口地址**: `POST /auth/reset-password`

**请求参数**:
```json
{
  "phone": "13800138000",
  "smsCode": "123456",
  "newPassword": "newPassword123",
  "confirmPassword": "newPassword123",
  "smsKey": "sms:reset:13800138000:uuid-key"
}
```

**参数说明**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| phone | String | 是 | 手机号码 |
| smsCode | String | 是 | 6位短信验证码 |
| newPassword | String | 是 | 新密码，6-20位 |
| confirmPassword | String | 是 | 确认密码 |
| smsKey | String | 是 | 验证码key |

### 1.9 刷新令牌

**接口地址**: `POST /auth/refresh-token`

**请求参数**:
```json
{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 1.10 注销登录

**接口地址**: `DELETE /auth/logout`

**请求头**:
```
Authorization: Bearer {accessToken}
```

---

## 二、用户管理

### 2.1 获取当前用户信息

**接口地址**: `GET /users/me`

**请求头**:
```
Authorization: Bearer {accessToken}
```

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": {
    "id": 1,
    "username": "admin",
    "nickname": "管理员",
    "avatar": "https://example.com/avatar.jpg",
    "email": "admin@example.com",
    "mobile": "13800138000",
    "gender": 1,
    "status": 1,
    "roles": ["ROLE_ADMIN"],
    "permissions": ["*:*:*"]
  }
}
```

### 2.2 获取用户个人信息

**接口地址**: `GET /users/{userId}/profile`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| userId | Long | 是 | 用户ID |

**请求头**:
```
Authorization: Bearer {accessToken}
```

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": {
    "id": 1,
    "username": "admin",
    "nickname": "管理员",
    "avatar": "https://example.com/avatar.jpg",
    "gender": 1,
    "mobile": "13800138000",
    "email": "admin@example.com",
    "createTime": "2024-01-20 10:30:00"
  }
}
```

---

## 三、成员管理

### 3.1 分页查询成员列表

**接口地址**: `GET /member/page`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| current | Long | 否 | 当前页码，默认1 |
| size | Long | 否 | 每页大小，默认10 |
| name | String | 否 | 姓名，支持模糊查询 |
| gender | Integer | 否 | 性别 0=未知 1=男 2=女 |
| cityId | Integer | 否 | 职业所在地ID |
| roleId | Integer | 否 | 角色ID |
| status | Integer | 否 | 状态：1=启用 0=禁用 |
| weight | Integer | 否 | 权重 |
| isElite | Integer | 否 | 是否为精英成员：0=否，1=是 |
| userId | Integer | 否 | 用户ID |

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": {
    "records": [
      {
        "id": 1,
        "name": "张三",
        "avatar": "https://example.com/avatar.jpg",
        "gender": 1,
        "organizationId": 1,
        "organizationName": "某某医院",
        "cityId": 110100,
        "introduction": "资深医美专家",
        "status": 1,
        "weight": 100,
        "isElite": 1,
        "userId": 1,
        "roleIds": [1, 2],
        "titleIds": [1, 2],
        "roles": [
          {
            "id": 1,
            "name": "主任医师"
          }
        ],
        "titles": [
          {
            "id": 1,
            "name": "专家"
          }
        ],
        "createdAt": "2024-01-20T10:30:00",
        "updatedAt": "2024-01-20T10:30:00"
      }
    ],
    "total": 100,
    "size": 10,
    "current": 1,
    "pages": 10
  }
}
```

### 3.2 获取成员详情

**接口地址**: `GET /member/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Integer | 是 | 成员ID |

**成功响应**: 与分页查询中的单条记录格式相同

### 3.3 新增成员

**接口地址**: `POST /member`

**请求参数**:
```json
{
  "name": "张三",
  "avatar": "https://example.com/avatar.jpg",
  "gender": 1,
  "organizationId": 1,
  "organization": "某某医院",
  "cityId": 110100,
  "introduction": "资深医美专家",
  "status": 1,
  "weight": 100,
  "isElite": 1,
  "userId": 1,
  "roleIds": [1, 2],
  "titleIds": [1, 2]
}
```

**字段说明**:
| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| name | String | 是 | 姓名 |
| avatar | String | 否 | 头像URL |
| gender | Integer | 否 | 性别 0=未知 1=男 2=女 |
| organizationId | Integer | 否 | 机构ID |
| organization | String | 否 | 备用机构名称 |
| cityId | Integer | 否 | 职业所在地ID |
| introduction | String | 否 | 个人简介 |
| status | Integer | 否 | 状态：1=启用 0=禁用 |
| weight | Integer | 否 | 权重，越大越靠前 |
| isElite | Integer | 否 | 是否为精英成员：0=否，1=是 |
| userId | Integer | 否 | 用户ID |
| roleIds | List<Integer> | 否 | 角色ID列表 |
| titleIds | List<Integer> | 否 | 头衔ID列表 |

### 3.4 更新成员

**接口地址**: `PUT /member`

**请求参数**: 与新增成员相同，但必须包含id字段

### 3.5 删除成员

**接口地址**: `DELETE /member/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Integer | 是 | 成员ID |

---

## 四、学生管理

### 4.1 获取学生列表

**接口地址**: `GET /student/list`

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "name": "李四",
      "avatar": "https://example.com/avatar.jpg",
      "gender": 1,
      "signature": "努力学习中",
      "organizationId": 1,
      "organizationName": "某某学院",
      "userId": 2,
      "status": 1,
      "createdAt": "2024-01-20T10:30:00",
      "updatedAt": "2024-01-20T10:30:00"
    }
  ]
}
```

### 4.2 分页获取学生列表

**接口地址**: `GET /student/page`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| current | Integer | 否 | 页码，默认1 |
| size | Integer | 否 | 每页大小，默认10 |
| name | String | 否 | 学生姓名，支持模糊查询 |
| gender | Integer | 否 | 性别：0=未知，1=男，2=女 |
| organizationId | Integer | 否 | 所属机构ID |
| userId | Integer | 否 | 用户ID |
| status | Integer | 否 | 状态：1=启用，0=禁用 |

**成功响应**: 分页格式，records为学生列表

### 4.3 获取学生详情

**接口地址**: `GET /student/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Integer | 是 | 学生ID |

### 4.4 新增学生

**接口地址**: `POST /student`

**请求参数**:
```json
{
  "name": "李四",
  "avatar": "https://example.com/avatar.jpg",
  "gender": 1,
  "signature": "努力学习中",
  "organizationId": 1,
  "userId": 2,
  "status": 1
}
```

**字段说明**:
| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| name | String | 是 | 姓名 |
| avatar | String | 否 | 头像URL |
| gender | Integer | 否 | 性别：0=未知，1=男，2=女 |
| signature | String | 否 | 个性签名 |
| organizationId | Integer | 否 | 所属机构ID |
| userId | Integer | 否 | 用户ID |
| status | Integer | 否 | 状态：1=启用，0=禁用 |

### 4.5 更新学生

**接口地址**: `PUT /student`

**请求参数**: 与新增学生相同，但必须包含id字段

### 4.6 删除学生

**接口地址**: `DELETE /student/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Integer | 是 | 学生ID |

---

## 五、文章管理

### 5.1 新增文章

**接口地址**: `POST /article`

**请求参数**:
```json
{
  "title": "文章标题",
  "coverImage": "https://example.com/cover.jpg",
  "description": "文章简介",
  "weight": 100,
  "status": 0,
  "isGuestVisible": 1,
  "channelId": 1,
  "authorId": 1,
  "authorType": 0
}
```

**字段说明**:
| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| title | String | 是 | 文章标题 |
| coverImage | String | 否 | 封面图地址 |
| description | String | 否 | 简要描述 |
| weight | Integer | 否 | 排序权重，值越大越靠前 |
| status | Integer | 否 | 状态：0=草稿，1=待审核，2=已发布，3=已驳回 |
| isGuestVisible | Integer | 否 | 是否允许未登录用户查看：1=允许，0=不允许 |
| channelId | Long | 否 | 主栏目ID |
| authorId | Long | 否 | 作者ID，默认1 |
| authorType | Integer | 否 | 作者类型：0=管理员，1=用户，默认0 |

### 5.2 修改文章

**接口地址**: `PUT /article`

**请求参数**: 与新增文章相同，但必须包含id字段

### 5.3 删除文章

**接口地址**: `DELETE /article/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 文章ID |

### 5.4 分页获取文章列表

**接口地址**: `GET /article/page`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| current | Integer | 否 | 页码，默认1 |
| size | Integer | 否 | 每页条数，默认10 |
| status | Integer | 否 | 状态 |
| channelId | Long | 否 | 栏目ID |

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": {
    "records": [
      {
        "id": 1,
        "title": "文章标题",
        "coverImage": "https://example.com/cover.jpg",
        "description": "文章简介",
        "weight": 100,
        "status": 2,
        "isGuestVisible": 1,
        "channelId": 1,
        "authorId": 1,
        "authorType": 0,
        "views": 100,
        "likes": 10,
        "comments": 5,
        "createtime": "2024-01-20 10:30:00",
        "updatetime": "2024-01-20 10:30:00",
        "publishtime": "2024-01-20 10:30:00"
      }
    ],
    "total": 100,
    "size": 10,
    "current": 1,
    "pages": 10
  }
}
```

### 5.5 获取文章详情

**接口地址**: `GET /article/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 文章ID |

### 5.6 获取文章内容

**接口地址**: `GET /article/{id}/content`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 文章ID |

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": {
    "articleId": 1,
    "content": "文章正文内容，支持HTML格式"
  }
}
```

### 5.7 保存文章内容

**接口地址**: `POST /article/{id}/content`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 文章ID |

**请求参数**:
```json
{
  "content": "文章正文内容，支持HTML格式"
}
```

### 5.8 前台-分页获取已发布文章

**接口地址**: `GET /article/frontend/page`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| current | Integer | 否 | 页码，默认1 |
| size | Integer | 否 | 每页条数，默认10 |
| channelId | Long | 否 | 栏目ID |

**说明**: 只返回已发布(status=2)的文章

### 5.9 前台-获取文章详情

**接口地址**: `GET /article/frontend/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 文章ID |

**说明**: 只返回已发布的文章，并自动增加浏览量

---

## 六、轮播图管理

### 6.1 前台-获取轮播图列表

**接口地址**: `GET /index/banner/list`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| position | Integer | 否 | 轮播图位置(1:顶部Banner, 2:平台介绍Banner) |

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "title": "Banner标题",
      "imageUrl": "https://example.com/banner.jpg",
      "link": "https://example.com",
      "position": 1,
      "sort": 1,
      "status": 1,
      "createTime": "2024-01-20T10:30:00",
      "updateTime": "2024-01-20T10:30:00"
    }
  ]
}
```

**字段说明**:
| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Long | 主键ID |
| title | String | Banner标题 |
| imageUrl | String | 图片URL |
| link | String | 链接地址 |
| position | Integer | Banner位置 (1:顶部Banner, 2:平台介绍Banner) |
| sort | Integer | 排序号 |
| status | Integer | 是否启用 (0:禁用, 1:启用) |
| createTime | LocalDateTime | 创建时间 |
| updateTime | LocalDateTime | 更新时间 |

### 6.2 管理端-添加轮播图

**接口地址**: `POST /homepage/banner`

**请求参数**:
```json
{
  "title": "Banner标题",
  "imageUrl": "https://example.com/banner.jpg",
  "link": "https://example.com",
  "position": 1,
  "sort": 1,
  "status": 1
}
```

### 6.3 管理端-更新轮播图

**接口地址**: `PUT /homepage/banner`

**请求参数**: 与添加轮播图相同，但必须包含id字段

### 6.4 管理端-删除轮播图

**接口地址**: `DELETE /homepage/banner/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 轮播图ID |

### 6.5 管理端-获取轮播图详情

**接口地址**: `GET /homepage/banner/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 轮播图ID |

### 6.6 管理端-分页获取轮播图列表

**接口地址**: `GET /homepage/banner/page`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| current | Integer | 否 | 页码，默认1 |
| size | Integer | 否 | 每页条数，默认10 |
| position | Integer | 否 | 轮播图位置 |

### 6.7 管理端-获取所有轮播图列表

**接口地址**: `GET /homepage/banner/list`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| position | Integer | 否 | 轮播图位置 |

---

## 七、分类条目管理

### 7.1 前台-获取分类条目列表

**接口地址**: `GET /index/category-item`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| type | String | 是 | 分类类型(service/platform/course/job) |

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "categoryType": "service",
      "name": "服务项目1",
      "icon": "icon-service",
      "sort": 1,
      "description": "服务描述",
      "status": 1,
      "createTime": "2024-01-20T10:30:00",
      "updateTime": "2024-01-20T10:30:00"
    }
  ]
}
```

**字段说明**:
| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Long | 主键ID |
| categoryType | String | 分类类型（如service/platform/course/job） |
| name | String | 条目名称 |
| icon | String | 图标路径或icon名 |
| sort | Integer | 排序号 |
| description | String | 描述 |
| status | Integer | 是否启用(0:禁用, 1:启用) |
| createTime | LocalDateTime | 创建时间 |
| updateTime | LocalDateTime | 更新时间 |

### 7.2 管理端-添加分类条目

**接口地址**: `POST /homepage/category-item`

**请求参数**:
```json
{
  "categoryType": "service",
  "name": "服务项目1",
  "icon": "icon-service",
  "sort": 1,
  "description": "服务描述",
  "status": 1
}
```

### 7.3 管理端-更新分类条目

**接口地址**: `PUT /homepage/category-item`

**请求参数**: 与添加分类条目相同，但必须包含id字段

### 7.4 管理端-删除分类条目

**接口地址**: `DELETE /homepage/category-item/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 分类条目ID |

### 7.5 管理端-获取分类条目详情

**接口地址**: `GET /homepage/category-item/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 分类条目ID |

### 7.6 管理端-分页获取分类条目列表

**接口地址**: `GET /homepage/category-item/page`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| current | Integer | 否 | 页码，默认1 |
| size | Integer | 否 | 每页条数，默认10 |
| type | String | 否 | 分类类型 |

### 7.7 管理端-获取所有分类条目列表

**接口地址**: `GET /homepage/category-item/list`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| type | String | 否 | 分类类型 |

### 7.8 管理端-获取所有分类类型

**接口地址**: `GET /homepage/category-item/types`

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": ["service", "platform", "course", "job"]
}
```

---

## 八、首页接口

### 8.1 获取首页顶部Banner

**接口地址**: `GET /index/banner/top`

**成功响应**: 返回position=1的Banner列表

### 8.2 获取平台介绍Banner

**接口地址**: `GET /index/banner/introduction`

**成功响应**: 返回position=2的Banner列表

### 8.3 获取分类列表

**接口地址**: `GET /index/category`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| type | String | 是 | 分类类型(service/platform/course/job) |

### 8.4 获取所有分类数据

**接口地址**: `GET /index/categories`

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": {
    "service": [...],
    "platform": [...],
    "course": [...],
    "job": [...]
  }
}
```

### 8.5 获取首页所有数据（一次性）

**接口地址**: `GET /index/all`

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": {
    "topBanners": [...],
    "introductionBanners": [...],
    "service": [...],
    "platform": [...],
    "course": [...],
    "job": [...]
  }
}
```

---

## 九、课程管理

### 9.1 获取课程列表

**接口地址**: `GET /course/list`

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "name": "课程名称",
      "coverImage": "https://example.com/cover.jpg",
      "description": "课程描述",
      "price": 99.99,
      "status": 1,
      "createdAt": "2024-01-20T10:30:00",
      "updatedAt": "2024-01-20T10:30:00"
    }
  ]
}
```

### 9.2 获取课程详情

**接口地址**: `GET /course/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Integer | 是 | 课程ID |

### 9.3 新增课程

**接口地址**: `POST /course`

**请求参数**:
```json
{
  "name": "课程名称",
  "coverImage": "https://example.com/cover.jpg",
  "description": "课程描述",
  "price": 99.99,
  "status": 1
}
```

### 9.4 更新课程

**接口地址**: `PUT /course`

**请求参数**: 与新增课程相同，但必须包含id字段

### 9.5 删除课程

**接口地址**: `DELETE /course/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Integer | 是 | 课程ID |

---

## 十、机构管理

### 10.1 获取机构列表

**接口地址**: `GET /organization/list`

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "name": "机构名称",
      "typeId": 1,
      "typeName": "机构类型",
      "logo": "https://example.com/logo.jpg",
      "description": "机构描述",
      "address": "机构地址",
      "contact": "联系方式",
      "status": 1,
      "createdAt": "2024-01-20T10:30:00",
      "updatedAt": "2024-01-20T10:30:00"
    }
  ]
}
```

### 10.2 获取机构详情

**接口地址**: `GET /organization/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Integer | 是 | 机构ID |

### 10.3 新增机构

**接口地址**: `POST /organization`

**请求参数**:
```json
{
  "name": "机构名称",
  "typeId": 1,
  "logo": "https://example.com/logo.jpg",
  "description": "机构描述",
  "address": "机构地址",
  "contact": "联系方式",
  "status": 1
}
```

### 10.4 更新机构

**接口地址**: `PUT /organization`

**请求参数**: 与新增机构相同，但必须包含id字段

### 10.5 删除机构

**接口地址**: `DELETE /organization/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Integer | 是 | 机构ID |

---

## 十一、机构类型管理

### 11.1 获取机构类型列表

**接口地址**: `GET /organization-type/list`

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "code": "hospital",
      "name": "医院",
      "description": "医疗机构",
      "status": 1,
      "createdAt": "2024-01-20T10:30:00",
      "updatedAt": "2024-01-20T10:30:00"
    }
  ]
}
```

### 11.2 获取机构类型详情

**接口地址**: `GET /organization-type/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Integer | 是 | 机构类型ID |

### 11.3 新增机构类型

**接口地址**: `POST /organization-type`

**请求参数**:
```json
{
  "code": "hospital",
  "name": "医院",
  "description": "医疗机构",
  "status": 1
}
```

### 11.4 更新机构类型

**接口地址**: `PUT /organization-type`

**请求参数**: 与新增机构类型相同，但必须包含id字段

### 11.5 删除机构类型

**接口地址**: `DELETE /organization-type/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Integer | 是 | 机构类型ID |

---

## 十二、岗位管理

### 12.1 获取岗位列表

**接口地址**: `GET /position/list`

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "name": "岗位名称",
      "description": "岗位描述",
      "requirements": "岗位要求",
      "responsibilities": "岗位职责",
      "status": 1,
      "createdAt": "2024-01-20T10:30:00",
      "updatedAt": "2024-01-20T10:30:00"
    }
  ]
}
```

### 12.2 获取岗位详情

**接口地址**: `GET /position/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Integer | 是 | 岗位ID |

### 12.3 新增岗位

**接口地址**: `POST /position`

**请求参数**:
```json
{
  "name": "岗位名称",
  "description": "岗位描述",
  "requirements": "岗位要求",
  "responsibilities": "岗位职责",
  "status": 1
}
```

### 12.4 更新岗位

**接口地址**: `PUT /position`

**请求参数**: 与新增岗位相同，但必须包含id字段

### 12.5 删除岗位

**接口地址**: `DELETE /position/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Integer | 是 | 岗位ID |

---

## 十三、角色管理

### 13.1 获取角色列表

**接口地址**: `GET /role/list`

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "name": "角色名称",
      "code": "ROLE_ADMIN",
      "description": "角色描述",
      "status": 1,
      "createdAt": "2024-01-20T10:30:00",
      "updatedAt": "2024-01-20T10:30:00"
    }
  ]
}
```

### 13.2 获取角色详情

**接口地址**: `GET /role/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Integer | 是 | 角色ID |

### 13.3 新增角色

**接口地址**: `POST /role`

**请求参数**:
```json
{
  "name": "角色名称",
  "code": "ROLE_ADMIN",
  "description": "角色描述",
  "status": 1
}
```

### 13.4 更新角色

**接口地址**: `PUT /role`

**请求参数**: 与新增角色相同，但必须包含id字段

### 13.5 删除角色

**接口地址**: `DELETE /role/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Integer | 是 | 角色ID |

---

## 十四、头衔管理

### 14.1 获取头衔列表

**接口地址**: `GET /title/list`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| type | String | 否 | 类型 |

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "name": "头衔名称",
      "type": "专家",
      "description": "头衔描述",
      "status": 1,
      "createdAt": "2024-01-20T10:30:00",
      "updatedAt": "2024-01-20T10:30:00"
    }
  ]
}
```

### 14.2 获取头衔详情

**接口地址**: `GET /title/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Integer | 是 | 头衔ID |

### 14.3 新增头衔

**接口地址**: `POST /title`

**请求参数**:
```json
{
  "name": "头衔名称",
  "type": "专家",
  "description": "头衔描述",
  "status": 1
}
```

### 14.4 更新头衔

**接口地址**: `PUT /title`

**请求参数**: 与新增头衔相同，但必须包含id字段

### 14.5 删除头衔

**接口地址**: `DELETE /title/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Integer | 是 | 头衔ID |

---

## 十五、城市数据

### 15.1 获取城市列表

**接口地址**: `GET /city/list`

**描述**: 获取省市区三级树形结构，用于级联选择器

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": [
    {
      "value": 110000,
      "label": "北京市",
      "children": [
        {
          "value": 110100,
          "label": "市辖区",
          "children": [
            {
              "value": 110101,
              "label": "东城区"
            }
          ]
        }
      ]
    }
  ]
}
```

---

## 十六、文件管理

### 16.1 上传文件

**接口地址**: `POST /file/upload`

**请求方式**: multipart/form-data

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| file | MultipartFile | 是 | 要上传的文件 |
| directory | String | 否 | 上传到COS的目录路径 (可选, e.g., 'images/articles/') |

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": "https://example.com/path/to/file.jpg"
}
```

**说明**:
- 如果未指定directory，系统会根据文件类型自动分类：
  - 图片文件：images/
  - 视频文件：videos/
  - 其他文件：others/
- 返回的data是文件的完整访问URL

---

## 十七、菜单管理

### 17.1 获取菜单路由列表

**接口地址**: `GET /menus/routes`

**请求头**:
```
Authorization: Bearer {accessToken}
```

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": [
    {
      "path": "/system",
      "component": "Layout",
      "redirect": "/system/user",
      "name": "System",
      "meta": {
        "title": "系统管理",
        "icon": "system",
        "hidden": false
      },
      "children": [...]
    }
  ]
}
```

### 17.2 获取菜单列表

**接口地址**: `GET /menus`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| keywords | String | 否 | 关键词搜索 |
| status | String | 否 | 状态 |

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "name": "系统管理",
      "parentId": 0,
      "type": "CATALOG",
      "path": "/system",
      "component": "Layout",
      "icon": "system",
      "sort": 1,
      "visible": 1,
      "children": [...]
    }
  ]
}
```

### 17.3 新增菜单

**接口地址**: `POST /menus`

**请求参数**:
```json
{
  "name": "菜单名称",
  "parentId": 0,
  "type": "CATALOG",
  "path": "/system",
  "component": "Layout",
  "icon": "system",
  "sort": 1,
  "visible": 1
}
```

### 17.4 修改菜单

**接口地址**: `PUT /menus/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 菜单ID |

**请求参数**: 与新增菜单相同

### 17.5 删除菜单

**接口地址**: `DELETE /menus/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | String | 是 | 菜单ID，可以是单个ID或多个ID的逗号分隔字符串 |

### 17.6 获取菜单下拉列表

**接口地址**: `GET /menus/options`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| onlyParent | Boolean | 否 | 是否只获取父级菜单 |

**成功响应**:
```json
{
  "code": "200",
  "message": "操作成功",
  "data": [
    {
      "value": 1,
      "label": "系统管理"
    }
  ]
}
```

### 17.7 获取菜单详情

**接口地址**: `GET /menus/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 菜单ID |

### 17.8 获取菜单表单数据

**接口地址**: `GET /menus/{id}/form`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 菜单ID |

### 17.9 修改菜单显示状态

**接口地址**: `PATCH /menus/{menuId}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| menuId | Long | 是 | 菜单ID |

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| visible | Integer | 是 | 显示状态 |

---

## 十八、错误码说明

### 认证相关错误码

| 错误码 | 错误信息 | 说明 |
|--------|----------|------|
| A0400 | 请求参数错误 | 请求参数格式或内容错误 |
| A0002 | 用户不存在 | 用户账号不存在 |
| A0003 | 用户名或密码错误 | 登录凭证错误 |
| A0004 | 验证码错误或已过期 | 图形验证码错误 |
| A0005 | 账号已被禁用 | 用户账号被禁用 |
| A0006 | 无效的令牌 | Token无效 |
| A0007 | 令牌已过期 | Token过期 |
| A0401 | 未授权 | 需要登录 |
| A0403 | 禁止访问 | 权限不足 |
| A0413 | 微信登录失败 | 微信授权失败 |
| A0414 | 短信发送过于频繁 | 短信发送限制 |
| A0415 | 短信发送失败 | 短信服务异常 |
| A0416 | 手机号格式错误 | 手机号不符合规范 |
| A0417 | 验证码错误或已过期 | 短信验证码错误 |
| A0418 | 手机号已注册 | 手机号已存在 |
| A0419 | 两次输入的密码不一致 | 重置密码时确认密码不匹配 |

### 系统错误码

| 错误码 | 错误信息 | 说明 |
|--------|----------|------|
| B0001 | 系统异常 | 服务器内部错误 |

---

## 十九、安全注意事项

### 19.1 令牌安全
- 访问令牌有效期：24小时（86400秒）
- 刷新令牌有效期：7天（604800秒）
- 令牌应存储在安全位置，避免泄露
- 使用HTTPS传输，避免中间人攻击

### 19.2 密码安全
- 密码长度：6-20位
- 密码传输前建议进行MD5或SHA256加密
- 服务端使用BCrypt进行密码哈希存储

### 19.3 验证码安全
- 图形验证码有效期：5分钟
- 短信验证码有效期：5分钟
- 短信发送频率限制：
  - 每分钟最多1次
  - 每小时最多5次
  - 每天最多10次

### 19.4 接口安全
- 所有接口支持CORS跨域
- 敏感接口需要携带Authorization头
- 建议实现接口调用频率限制

---

## 二十、测试环境信息

### 20.1 服务器信息
- **测试环境地址**: `http://localhost:8080`
- **API文档地址**: `http://localhost:8080/swagger-ui.html`
- **数据库监控**: `http://localhost:8080/druid/index.html`
  - 用户名：admin
  - 密码：admin123456

### 20.2 测试账号

#### 后台管理系统测试账号
- 用户名：`admin`
- 密码：`123456`

#### 小程序测试账号
- 用户名：`testuser`
- 密码：`123456`
- 测试手机号：`13800138000`

### 20.3 微信小程序配置
- AppID：需要在环境变量中配置
- AppSecret：需要在环境变量中配置
- 测试时可使用微信开发者工具的模拟登录功能

### 20.4 短信服务配置
- 使用腾讯云短信服务
- 测试环境下验证码固定为：`123456`
- 生产环境需配置真实的腾讯云密钥

---

## 二十一、常见问题

### 21.1 令牌相关
**Q: 令牌过期如何处理？**
A: 使用refresh-token接口刷新令牌，或重新登录获取新令牌。

**Q: 如何判断令牌是否有效？**
A: 调用需要认证的接口，如果返回401错误码，说明令牌无效。

### 21.2 验证码相关
**Q: 短信验证码收不到怎么办？**
A: 检查手机号格式，确认短信服务配置正确，测试环境可使用固定验证码123456。

**Q: 图形验证码看不清怎么办？**
A: 重新调用获取验证码接口获取新的验证码。

### 21.3 微信登录相关
**Q: 微信登录失败怎么办？**
A: 检查微信小程序配置，确认AppID和AppSecret正确，code是否有效。

### 21.4 文件上传相关
**Q: 支持哪些文件类型？**
A: 支持所有常见文件类型，包括图片、视频、文档等。

**Q: 文件大小限制是多少？**
A: 默认限制请参考Spring Boot配置，可通过application.yml调整。

---

## 二十二、联系方式

如有技术问题，请联系后端开发团队：
- 技术支持邮箱：support@example.com
- 开发文档：本文档将持续更新
- 更新时间：2024年11月17日

---

**注意**: 本文档基于当前代码实现编写，如有接口变更，请及时更新文档。
