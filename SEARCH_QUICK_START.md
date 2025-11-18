# 🔍 搜索功能快速开始

> 本文档提供搜索功能的快速使用指南

## 📋 功能概述

全局搜索功能已成功集成到项目中，支持以下类型的搜索：

- ✅ **文章** (article)
- ✅ **成员** (member)
- ✅ **课程** (course)
- ✅ **机构** (organization)
- ✅ **学生** (student)

## 🚀 快速测试

### 1. 启动项目

```bash
mvn spring-boot:run
```

### 2. 访问 Swagger 文档

浏览器打开：http://localhost:8080/doc.html

在"搜索管理"分组下可以看到搜索接口。

### 3. 测试接口

#### 使用 curl（POST 方式）

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

#### 使用 curl（GET 方式）

```bash
curl "http://localhost:8080/search?keyword=医美&types=article,member&page=1&size=10"
```

#### 使用测试脚本

```bash
bash docs/test_search_api.sh
```

## 📖 请求参数说明

| 参数 | 类型 | 必填 | 说明 | 默认值 |
|------|------|------|------|--------|
| keyword | String | ✅ | 搜索关键词 | - |
| types | Array | ❌ | 搜索类型数组 | 全部类型 |
| page | Integer | ❌ | 页码（从1开始） | 1 |
| size | Integer | ❌ | 每页数量 | 10 |

## 📦 响应数据格式

```json
{
  "code": "00000",
  "data": {
    "results": [
      {
        "type": "article",
        "id": 1,
        "title": "文章标题",
        "desc": "文章描述",
        "extra": { /* 其他字段 */ }
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

## 🔧 新增文件列表

### Java 代码（6个文件）

```
src/main/java/com/example/mzt_server/
├── controller/
│   └── SearchController.java          # 搜索控制器
├── service/
│   ├── SearchService.java             # 搜索服务接口
│   └── impl/
│       └── SearchServiceImpl.java     # 搜索服务实现
├── dto/
│   ├── SearchRequest.java             # 请求参数
│   └── SearchResponse.java            # 响应结果
└── common/vo/
    └── SearchResult.java               # 搜索结果项
```

### 文档文件（4个文件）

```
docs/
├── SEARCH_API.md                       # API使用文档
├── SEARCH_FEATURE_SUMMARY.md          # 功能实现总结
├── FRONTEND_INTEGRATION.md            # 前端集成指南
├── NEW_FILES_LIST.md                  # 新增文件清单
└── test_search_api.sh                 # 测试脚本
```

## 💡 使用示例

### JavaScript/TypeScript

```javascript
// 使用 fetch
const response = await fetch('http://localhost:8080/search', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    keyword: '医美',
    types: ['article', 'member'],
    page: 1,
    size: 10
  })
});

const result = await response.json();
console.log(result.data.results);
```

### Python

```python
import requests

response = requests.post('http://localhost:8080/search', json={
    'keyword': '医美',
    'types': ['article', 'member'],
    'page': 1,
    'size': 10
})

result = response.json()
print(result['data']['results'])
```

## 📚 详细文档

- 📄 [API 使用文档](docs/SEARCH_API.md) - 完整的 API 说明
- 🔧 [功能实现总结](docs/SEARCH_FEATURE_SUMMARY.md) - 技术实现细节
- 💻 [前端集成指南](docs/FRONTEND_INTEGRATION.md) - React/Vue 集成示例
- 📋 [新增文件清单](docs/NEW_FILES_LIST.md) - 所有新增文件列表

## ✅ 功能特性

- ✅ 多表联合搜索
- ✅ 统一返回格式
- ✅ 分页支持
- ✅ GET/POST 双方式支持
- ✅ 参数验证
- ✅ Swagger 文档集成
- ✅ 模糊查询（LIKE）
- ✅ 状态过滤
- ✅ 无 linter 错误

## 🎯 接口地址

- **POST 方式**：`POST /search`
- **GET 方式**：`GET /search`
- **Swagger 文档**：http://localhost:8080/doc.html

## ⚠️ 注意事项

1. 关键词不能为空
2. 类型参数可选，不传则搜索全部类型
3. 页码从 1 开始
4. 只搜索已发布/启用状态的数据

## 🐛 问题反馈

如遇到问题，请检查：
1. ✅ 项目是否正常启动
2. ✅ 数据库连接是否正常
3. ✅ 各个表是否有测试数据
4. ✅ 关键词是否与数据库中的数据匹配

---

**创建日期**: 2025-11-18  
**状态**: ✅ 已完成  
**版本**: v1.0.0

