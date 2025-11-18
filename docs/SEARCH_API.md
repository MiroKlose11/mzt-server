# 全局搜索 API 文档

## 概述

全局搜索功能支持在多个表中进行关键词搜索，目前支持以下类型：
- `article` - 文章
- `member` - 成员
- `course` - 课程
- `organization` - 机构
- `student` - 学生

## 接口信息

### 1. POST 方式搜索

**接口地址：** `POST /search`

**请求参数：**

```json
{
  "keyword": "医美",
  "types": ["article", "member", "course"],
  "page": 1,
  "size": 10
}
```

**参数说明：**

| 参数名 | 类型 | 必填 | 说明 | 默认值 |
|--------|------|------|------|--------|
| keyword | String | 是 | 搜索关键词 | - |
| types | Array | 否 | 搜索类型数组，不传则搜索全部类型 | 全部 |
| page | Integer | 否 | 页码（从1开始） | 1 |
| size | Integer | 否 | 每页数量 | 10 |

**响应示例：**

```json
{
  "code": "00000",
  "data": {
    "results": [
      {
        "type": "article",
        "id": 1,
        "title": "如何选择适合自己的医美项目",
        "desc": "本文介绍如何根据自己的需求选择合适的医美项目",
        "extra": {
          "coverImage": "https://example.com/cover.jpg",
          "weight": 100,
          "status": 2,
          "views": 1000,
          "likes": 100,
          "createtime": "2024-01-01T00:00:00",
          "updatetime": "2024-01-02T00:00:00"
        }
      },
      {
        "type": "member",
        "id": 2,
        "title": "张三",
        "desc": "资深医美专家，具有10年从业经验",
        "extra": {
          "avatar": "https://example.com/avatar.jpg",
          "gender": 1,
          "organizationId": 1,
          "status": 1,
          "weight": 100,
          "isElite": 1
        }
      }
    ],
    "total": 100,
    "page": 1,
    "size": 10,
    "totalPages": 10
  },
  "msg": "一切OK!"
}
```

### 2. GET 方式搜索

**接口地址：** `GET /search`

**请求参数：**

| 参数名 | 类型 | 必填 | 说明 | 示例 |
|--------|------|------|------|------|
| keyword | String | 是 | 搜索关键词 | 医美 |
| types | String | 否 | 搜索类型，多个用逗号分隔 | article,member,course |
| page | Integer | 否 | 页码（从1开始） | 1 |
| size | Integer | 否 | 每页数量 | 10 |

**请求示例：**

```
GET /search?keyword=医美&types=article,member&page=1&size=10
```

**响应格式：** 与 POST 方式相同

## 搜索规则

### 各类型搜索字段

1. **文章 (article)**
   - 标题：`title`
   - 描述：`description`
   - 筛选条件：只搜索已发布的文章（status=2）

2. **成员 (member)**
   - 姓名：`name`
   - 简介：`introduction`
   - 机构名称：`organization`
   - 筛选条件：只搜索启用的成员（status=1）

3. **课程 (course)**
   - 标题：`title`
   - 描述：`description`
   - 筛选条件：只搜索启用的课程（status=true）

4. **机构 (organization)**
   - 名称：`name`
   - 地址：`address`
   - 筛选条件：只搜索启用的机构（status=1）

5. **学生 (student)**
   - 姓名：`name`
   - 签名：`signature`
   - 筛选条件：只搜索启用的学生（status=1）

### 排序规则

- **文章**：按更新时间倒序
- **成员**：按权重倒序、更新时间倒序
- **课程**：按更新时间倒序
- **机构**：按权重倒序、更新时间倒序
- **学生**：按更新时间倒序

## 使用示例

### 示例1：搜索全部类型

```bash
curl -X POST http://localhost:8080/search \
  -H "Content-Type: application/json" \
  -d '{
    "keyword": "医美",
    "page": 1,
    "size": 10
  }'
```

### 示例2：只搜索文章和成员

```bash
curl -X POST http://localhost:8080/search \
  -H "Content-Type: application/json" \
  -d '{
    "keyword": "医美",
    "types": ["article", "member"],
    "page": 1,
    "size": 10
  }'
```

### 示例3：使用 GET 方式搜索

```bash
curl -X GET "http://localhost:8080/search?keyword=医美&types=article,member&page=1&size=10"
```

## 返回字段说明

### SearchResult 对象

| 字段名 | 类型 | 说明 |
|--------|------|------|
| type | String | 结果类型（article/member/course/organization/student） |
| id | Object | 主键ID |
| title | String | 标题（显示名称） |
| desc | String | 描述（简介） |
| extra | Map | 额外字段（除id外的全部字段） |

### SearchResponse 对象

| 字段名 | 类型 | 说明 |
|--------|------|------|
| results | Array | 搜索结果列表 |
| total | Long | 总记录数 |
| page | Integer | 当前页码 |
| size | Integer | 每页数量 |
| totalPages | Long | 总页数 |

## 注意事项

1. **关键词不能为空**：必须提供搜索关键词
2. **类型过滤**：如果指定的类型不在支持列表中，将被自动过滤
3. **分页**：页码从1开始，如果传入0或负数，默认为1
4. **模糊匹配**：使用 MySQL LIKE 进行模糊匹配
5. **结果合并**：多个类型的结果会合并后统一分页返回

## Swagger 文档

启动项目后，访问以下地址查看完整的 API 文档：

- Swagger UI: `http://localhost:8080/doc.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

