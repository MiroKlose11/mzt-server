# 搜索功能验证清单

本清单用于验证搜索功能是否正常工作。

## ✅ 代码质量检查

### 编译检查
- [ ] 项目无编译错误
- [ ] 无 linter 警告（除已知警告外）
- [ ] 所有导入正确

### 代码规范
- [ ] 使用 Lombok 简化代码
- [ ] 统一使用 Jakarta 验证注解
- [ ] 遵循 Spring Boot 最佳实践
- [ ] 完整的注释和文档

## ✅ 功能测试

### 基础功能
- [ ] POST 方式搜索正常
- [ ] GET 方式搜索正常
- [ ] 分页功能正常
- [ ] 关键词验证正常

### 搜索类型测试

#### 1. 文章搜索
- [ ] 可以搜索文章标题
- [ ] 可以搜索文章描述
- [ ] 只返回已发布的文章（status=2）
- [ ] 按更新时间倒序排列

#### 2. 成员搜索
- [ ] 可以搜索成员姓名
- [ ] 可以搜索成员简介
- [ ] 可以搜索机构名称
- [ ] 只返回启用的成员（status=1）
- [ ] 按权重和更新时间排序

#### 3. 课程搜索
- [ ] 可以搜索课程标题
- [ ] 可以搜索课程描述
- [ ] 只返回启用的课程（status=true）
- [ ] 按更新时间倒序排列

#### 4. 机构搜索
- [ ] 可以搜索机构名称
- [ ] 可以搜索机构地址
- [ ] 只返回启用的机构（status=1）
- [ ] 按权重和更新时间排序

#### 5. 学生搜索
- [ ] 可以搜索学生姓名
- [ ] 可以搜索学生签名
- [ ] 只返回启用的学生（status=1）
- [ ] 按更新时间倒序排列

### 多类型搜索
- [ ] 可以同时搜索多个类型
- [ ] 不指定类型时搜索全部类型
- [ ] 指定无效类型时返回空结果
- [ ] 结果正确合并

### 分页测试
- [ ] 第1页数据正确
- [ ] 第2页数据正确
- [ ] 最后一页数据正确
- [ ] 总记录数正确
- [ ] 总页数计算正确

### 边界测试
- [ ] 关键词为空时返回错误
- [ ] page=0 时使用默认值1
- [ ] size=0 时使用默认值10
- [ ] 没有匹配结果时返回空数组
- [ ] 超出总页数时返回空数组

## ✅ API 文档检查

### Swagger 集成
- [ ] Swagger UI 可以正常访问
- [ ] 搜索接口在"搜索管理"分组下
- [ ] 接口描述清晰完整
- [ ] 参数说明准确
- [ ] 响应示例正确

### 接口文档
- [ ] POST /search 接口文档完整
- [ ] GET /search 接口文档完整
- [ ] 参数类型标注正确
- [ ] 必填/可选标注正确
- [ ] 默认值标注正确

## ✅ 响应格式检查

### 成功响应
- [ ] code 为 "00000"
- [ ] data 包含 results 数组
- [ ] data 包含 total 总数
- [ ] data 包含 page 当前页
- [ ] data 包含 size 每页数量
- [ ] data 包含 totalPages 总页数
- [ ] msg 为 "一切OK!"

### 搜索结果格式
- [ ] type 字段正确
- [ ] id 字段存在
- [ ] title 字段有值
- [ ] desc 字段有值
- [ ] extra 包含其他字段
- [ ] extra 不包含 id 字段

### 错误响应
- [ ] 关键词为空时返回错误码
- [ ] 错误消息清晰明确

## ✅ 性能测试

### 响应时间
- [ ] 单类型搜索 < 500ms
- [ ] 多类型搜索 < 1000ms
- [ ] 大数据量分页 < 1000ms

### 并发测试
- [ ] 10 并发请求正常
- [ ] 50 并发请求正常
- [ ] 100 并发请求正常

## ✅ 兼容性测试

### 浏览器测试
- [ ] Chrome 浏览器正常
- [ ] Firefox 浏览器正常
- [ ] Safari 浏览器正常
- [ ] Edge 浏览器正常

### 客户端测试
- [ ] curl 命令行工具正常
- [ ] Postman 正常
- [ ] JavaScript fetch 正常
- [ ] axios 正常

## ✅ 文档完整性检查

### 代码文档
- [ ] SearchController 注释完整
- [ ] SearchService 注释完整
- [ ] SearchServiceImpl 注释完整
- [ ] SearchRequest 注释完整
- [ ] SearchResponse 注释完整
- [ ] SearchResult 注释完整

### 使用文档
- [ ] SEARCH_API.md 完整
- [ ] SEARCH_FEATURE_SUMMARY.md 完整
- [ ] FRONTEND_INTEGRATION.md 完整
- [ ] NEW_FILES_LIST.md 完整
- [ ] VERIFICATION_CHECKLIST.md 完整
- [ ] README.md 已更新

### 测试脚本
- [ ] test_search_api.sh 可执行
- [ ] 测试脚本输出正确

## ✅ 数据库检查

### 数据准备
- [ ] article 表有测试数据
- [ ] member 表有测试数据
- [ ] course 表有测试数据
- [ ] organization 表有测试数据
- [ ] student 表有测试数据

### 索引检查（可选优化）
- [ ] 搜索字段是否需要添加索引
- [ ] 查询性能是否满足要求

## ✅ 安全检查

### 参数验证
- [ ] 关键词验证正常
- [ ] 类型验证正常
- [ ] 分页参数验证正常

### SQL 注入防护
- [ ] 使用参数化查询
- [ ] 无 SQL 注入风险

### XSS 防护
- [ ] 返回数据无需 HTML 转义
- [ ] 前端需要自行转义

## 测试命令

### 启动项目
```bash
mvn spring-boot:run
```

### 运行测试脚本
```bash
bash docs/test_search_api.sh
```

### 手动测试

#### 测试 1: POST 搜索全部类型
```bash
curl -X POST http://localhost:8080/search \
  -H "Content-Type: application/json" \
  -d '{"keyword":"医美","page":1,"size":10}'
```

#### 测试 2: POST 搜索指定类型
```bash
curl -X POST http://localhost:8080/search \
  -H "Content-Type: application/json" \
  -d '{"keyword":"医美","types":["article","member"],"page":1,"size":10}'
```

#### 测试 3: GET 搜索
```bash
curl "http://localhost:8080/search?keyword=医美&types=article,member&page=1&size=10"
```

#### 测试 4: 测试分页
```bash
curl -X POST http://localhost:8080/search \
  -H "Content-Type: application/json" \
  -d '{"keyword":"医美","page":2,"size":5}'
```

#### 测试 5: 测试空关键词（应该返回错误）
```bash
curl -X POST http://localhost:8080/search \
  -H "Content-Type: application/json" \
  -d '{"keyword":"","page":1,"size":10}'
```

## 问题排查

### 常见问题

1. **搜索无结果**
   - 检查数据库是否有测试数据
   - 检查关键词是否匹配
   - 检查数据状态是否正确

2. **返回 400 错误**
   - 检查请求参数格式
   - 检查关键词是否为空
   - 检查 Content-Type 是否正确

3. **返回 500 错误**
   - 检查日志文件
   - 检查数据库连接
   - 检查服务依赖注入

4. **Swagger 无法访问**
   - 检查项目是否启动
   - 检查端口是否被占用
   - 检查 Swagger 配置

## 验证完成

- [ ] 所有功能测试通过
- [ ] 所有文档完整
- [ ] 代码质量检查通过
- [ ] 性能测试通过
- [ ] 安全检查通过

---

**验证人**：_____________  
**验证日期**：_____________  
**验证结果**：□ 通过  □ 不通过  

**备注**：
```
（记录测试过程中发现的问题和解决方案）
```

