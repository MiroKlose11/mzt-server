# 搜索功能实现总结

## 功能概述

为 mzt-server 项目添加了全局搜索功能，支持跨多个表进行关键词搜索，统一返回格式，并集成到 Swagger 文档中。

## 实现架构

### 分层架构

```
Controller 层（SearchController）
    ↓
Service 层（SearchService / SearchServiceImpl）
    ↓
Repository 层（使用现有的各个 Service）
```

## 创建的文件列表

### 1. DTO 层

#### `src/main/java/com/example/mzt_server/dto/SearchRequest.java`
- **作用**：搜索请求参数
- **字段**：
  - `keyword`: 搜索关键词（必填）
  - `types`: 搜索类型数组（可选）
  - `page`: 页码（默认1）
  - `size`: 每页数量（默认10）

#### `src/main/java/com/example/mzt_server/dto/SearchResponse.java`
- **作用**：搜索响应结果
- **字段**：
  - `results`: 搜索结果列表
  - `total`: 总记录数
  - `page`: 当前页码
  - `size`: 每页数量
  - `totalPages`: 总页数

### 2. VO 层

#### `src/main/java/com/example/mzt_server/common/vo/SearchResult.java`
- **作用**：统一搜索结果项
- **字段**：
  - `type`: 结果类型（article/member/course/organization/student）
  - `id`: 主键ID
  - `title`: 标题（显示名称）
  - `desc`: 描述（简介）
  - `extra`: 额外字段（除id外的全部字段）

### 3. Service 层

#### `src/main/java/com/example/mzt_server/service/SearchService.java`
- **作用**：搜索服务接口
- **方法**：
  - `SearchResponse search(SearchRequest request)`: 执行全局搜索

#### `src/main/java/com/example/mzt_server/service/impl/SearchServiceImpl.java`
- **作用**：搜索服务实现类
- **核心功能**：
  - 根据类型分发搜索请求
  - 使用 MyBatis-Plus 的 LambdaQueryWrapper 进行模糊查询
  - 合并多个表的搜索结果
  - 统一分页处理
  - 将实体对象转换为统一的搜索结果格式

### 4. Controller 层

#### `src/main/java/com/example/mzt_server/controller/SearchController.java`
- **作用**：搜索控制器
- **接口**：
  - `POST /search`: POST 方式搜索（推荐）
  - `GET /search`: GET 方式搜索（便于浏览器直接访问）
- **Swagger 集成**：已添加详细的接口文档注解

## 技术实现细节

### 1. 搜索策略

每个表的搜索字段和筛选条件：

| 表名 | 搜索字段 | 筛选条件 | 排序规则 |
|------|----------|----------|----------|
| Article | title, description | status=2（已发布） | updatetime DESC |
| Member | name, introduction, organization | status=1（启用） | weight DESC, updatedAt DESC |
| Course | title, description | status=true（启用） | updatedAt DESC |
| Organization | name, address | status=1（启用） | weight DESC, updatedAt DESC |
| Student | name, signature | status=1（启用） | updatedAt DESC |

### 2. 模糊查询实现

使用 MyBatis-Plus 的 `LambdaQueryWrapper` 实现：

```java
LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
wrapper.and(w -> w.like(Article::getTitle, keyword)
        .or().like(Article::getDescription, keyword));
```

### 3. 结果合并与分页

- **合并策略**：将所有类型的搜索结果合并到一个列表
- **分页处理**：在合并后的结果上进行内存分页
- **总数统计**：分别统计各类型的总数后累加

### 4. 实体转 Map

使用 Java 反射将实体对象转换为 Map：

```java
private Map<String, Object> entityToMap(Object entity) {
    Map<String, Object> map = new HashMap<>();
    Field[] fields = entity.getClass().getDeclaredFields();
    for (Field field : fields) {
        field.setAccessible(true);
        if (!"id".equals(field.getName()) && !"serialVersionUID".equals(field.getName())) {
            map.put(field.getName(), field.get(entity));
        }
    }
    return map;
}
```

## API 接口说明

### POST /search

**请求示例：**

```json
{
  "keyword": "医美",
  "types": ["article", "member"],
  "page": 1,
  "size": 10
}
```

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

### GET /search

**请求示例：**

```
GET /search?keyword=医美&types=article,member&page=1&size=10
```

## Swagger 文档集成

- **标签**：搜索管理
- **描述**：全局搜索接口，支持文章、成员、课程、机构、学生等多类型搜索
- **注解**：完整的 `@Operation`、`@Parameter`、`@Schema` 注解
- **访问地址**：http://localhost:8080/doc.html

## 使用方式

### 1. 启动项目

```bash
mvn spring-boot:run
```

### 2. 访问 Swagger 文档

访问：http://localhost:8080/doc.html

在"搜索管理"分组下可以看到搜索接口。

### 3. 测试接口

#### 方式1：使用 Swagger UI 测试

在 Swagger UI 中直接测试接口。

#### 方式2：使用 curl 测试

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

#### 方式3：使用测试脚本

```bash
bash docs/test_search_api.sh
```

## 扩展说明

### 如何添加新的搜索类型

1. **在 SearchServiceImpl 中添加新的搜索方法**：

```java
private List<SearchResult> searchNewType(String keyword, SearchRequest request) {
    LambdaQueryWrapper<NewEntity> wrapper = new LambdaQueryWrapper<>();
    wrapper.like(NewEntity::getFieldName, keyword);
    
    Page<NewEntity> page = new Page<>(request.getPage(), request.getSize());
    Page<NewEntity> result = newEntityService.page(page, wrapper);
    
    return result.getRecords().stream()
            .map(entity -> SearchResult.builder()
                    .type("newtype")
                    .id(entity.getId())
                    .title(entity.getName())
                    .desc(entity.getDescription())
                    .extra(entityToMap(entity))
                    .build())
            .collect(Collectors.toList());
}

private long countNewType(String keyword) {
    LambdaQueryWrapper<NewEntity> wrapper = new LambdaQueryWrapper<>();
    wrapper.like(NewEntity::getFieldName, keyword);
    return newEntityService.count(wrapper);
}
```

2. **在 SUPPORTED_TYPES 中添加新类型**：

```java
private static final List<String> SUPPORTED_TYPES = Arrays.asList(
        "article", "member", "course", "organization", "student", "newtype"
);
```

3. **在 search 方法的 switch 中添加新的 case**：

```java
case "newtype":
    List<SearchResult> newTypeResults = searchNewType(keyword, request);
    allResults.addAll(newTypeResults);
    totalCount += countNewType(keyword);
    break;
```

## 性能优化建议

### 当前实现的局限性

1. **内存分页**：目前是将所有结果加载到内存后再分页，数据量大时可能有性能问题
2. **无缓存**：每次搜索都查询数据库
3. **无全文索引**：使用 LIKE 模糊查询，性能不如全文索引

### 优化方案

1. **使用 Elasticsearch**：
   - 将数据同步到 Elasticsearch
   - 使用 Elasticsearch 进行全文搜索
   - 支持更复杂的搜索语法和高亮

2. **添加缓存**：
   - 使用 Redis 缓存热门搜索结果
   - 设置合理的过期时间

3. **数据库优化**：
   - 为搜索字段添加索引
   - 使用 MySQL 全文索引（FULLTEXT）

4. **异步处理**：
   - 将多个表的搜索改为并行执行
   - 使用 CompletableFuture 或 @Async

## 注意事项

1. **数据库配置**：确保不修改数据库配置（按用户要求）
2. **权限控制**：当前搜索未添加权限验证，如需要请在 Controller 添加 `@PreAuthorize` 注解
3. **参数验证**：已使用 Jakarta Validation 进行参数校验
4. **异常处理**：使用全局异常处理器统一处理异常

## 相关文档

- [API 使用文档](SEARCH_API.md)
- [测试脚本](test_search_api.sh)
- [项目主文档](../README.md)

