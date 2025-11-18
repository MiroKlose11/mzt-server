# 前端集成指南

本文档说明如何在前端项目中集成搜索功能。

## 接口信息

**基础URL**: `http://localhost:8080` （根据实际部署环境调整）

**接口路径**: `/search`

## 请求方式

### 方式1：POST 请求（推荐）

```javascript
// 使用 fetch
async function searchData(keyword, types = [], page = 1, size = 10) {
  try {
    const response = await fetch('http://localhost:8080/search', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        keyword: keyword,
        types: types,  // 例如: ["article", "member"]
        page: page,
        size: size
      })
    });
    
    const result = await response.json();
    
    if (result.code === '00000') {
      return result.data;
    } else {
      throw new Error(result.msg);
    }
  } catch (error) {
    console.error('搜索失败:', error);
    throw error;
  }
}

// 使用示例
searchData('医美', ['article', 'member'], 1, 10)
  .then(data => {
    console.log('搜索结果:', data.results);
    console.log('总数:', data.total);
    console.log('总页数:', data.totalPages);
  })
  .catch(error => {
    console.error('Error:', error);
  });
```

### 方式2：使用 axios

```javascript
import axios from 'axios';

// 搜索函数
async function searchData(keyword, types = [], page = 1, size = 10) {
  try {
    const response = await axios.post('http://localhost:8080/search', {
      keyword,
      types,
      page,
      size
    });
    
    if (response.data.code === '00000') {
      return response.data.data;
    } else {
      throw new Error(response.data.msg);
    }
  } catch (error) {
    console.error('搜索失败:', error);
    throw error;
  }
}

// 使用示例
searchData('医美', ['article', 'member'], 1, 10)
  .then(data => {
    console.log('搜索结果:', data);
  });
```

### 方式3：GET 请求

```javascript
// 使用 fetch
async function searchDataByGet(keyword, types = [], page = 1, size = 10) {
  const params = new URLSearchParams({
    keyword: keyword,
    page: page,
    size: size
  });
  
  // 添加多个 types 参数
  if (types.length > 0) {
    params.append('types', types.join(','));
  }
  
  try {
    const response = await fetch(`http://localhost:8080/search?${params}`);
    const result = await response.json();
    
    if (result.code === '00000') {
      return result.data;
    } else {
      throw new Error(result.msg);
    }
  } catch (error) {
    console.error('搜索失败:', error);
    throw error;
  }
}

// 使用示例
searchDataByGet('医美', ['article', 'member'], 1, 10)
  .then(data => {
    console.log('搜索结果:', data);
  });
```

## React 示例

### 搜索组件

```jsx
import React, { useState } from 'react';
import axios from 'axios';

const SearchComponent = () => {
  const [keyword, setKeyword] = useState('');
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [total, setTotal] = useState(0);
  const [page, setPage] = useState(1);
  const [pageSize] = useState(10);

  const handleSearch = async () => {
    if (!keyword.trim()) {
      alert('请输入搜索关键词');
      return;
    }

    setLoading(true);
    try {
      const response = await axios.post('http://localhost:8080/search', {
        keyword: keyword,
        types: ['article', 'member', 'course'], // 可根据需要调整
        page: page,
        size: pageSize
      });

      if (response.data.code === '00000') {
        setResults(response.data.data.results);
        setTotal(response.data.data.total);
      } else {
        alert(response.data.msg);
      }
    } catch (error) {
      console.error('搜索失败:', error);
      alert('搜索失败，请稍后重试');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <div>
        <input
          type="text"
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
          onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
          placeholder="请输入搜索关键词"
        />
        <button onClick={handleSearch} disabled={loading}>
          {loading ? '搜索中...' : '搜索'}
        </button>
      </div>

      <div>
        <p>找到 {total} 条结果</p>
        {results.map((item, index) => (
          <div key={index} style={{ border: '1px solid #ccc', margin: '10px', padding: '10px' }}>
            <div style={{ color: '#999' }}>{item.type}</div>
            <h3>{item.title}</h3>
            <p>{item.desc}</p>
            <div>
              <small>ID: {item.id}</small>
            </div>
          </div>
        ))}
      </div>

      {/* 分页控件 */}
      <div>
        <button 
          onClick={() => setPage(page - 1)} 
          disabled={page === 1 || loading}
        >
          上一页
        </button>
        <span> 第 {page} 页 </span>
        <button 
          onClick={() => setPage(page + 1)} 
          disabled={page * pageSize >= total || loading}
        >
          下一页
        </button>
      </div>
    </div>
  );
};

export default SearchComponent;
```

## Vue 3 示例

### 搜索组件

```vue
<template>
  <div class="search-component">
    <div class="search-box">
      <input
        v-model="keyword"
        @keyup.enter="handleSearch"
        type="text"
        placeholder="请输入搜索关键词"
      />
      <button @click="handleSearch" :disabled="loading">
        {{ loading ? '搜索中...' : '搜索' }}
      </button>
    </div>

    <div class="search-results">
      <p>找到 {{ total }} 条结果</p>
      <div
        v-for="(item, index) in results"
        :key="index"
        class="result-item"
      >
        <div class="result-type">{{ item.type }}</div>
        <h3>{{ item.title }}</h3>
        <p>{{ item.desc }}</p>
        <div>
          <small>ID: {{ item.id }}</small>
        </div>
      </div>
    </div>

    <div class="pagination">
      <button @click="page--" :disabled="page === 1 || loading">
        上一页
      </button>
      <span>第 {{ page }} 页</span>
      <button @click="page++" :disabled="page * pageSize >= total || loading">
        下一页
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import axios from 'axios';

const keyword = ref('');
const results = ref([]);
const loading = ref(false);
const total = ref(0);
const page = ref(1);
const pageSize = ref(10);

const handleSearch = async () => {
  if (!keyword.value.trim()) {
    alert('请输入搜索关键词');
    return;
  }

  loading.value = true;
  try {
    const response = await axios.post('http://localhost:8080/search', {
      keyword: keyword.value,
      types: ['article', 'member', 'course'],
      page: page.value,
      size: pageSize.value
    });

    if (response.data.code === '00000') {
      results.value = response.data.data.results;
      total.value = response.data.data.total;
    } else {
      alert(response.data.msg);
    }
  } catch (error) {
    console.error('搜索失败:', error);
    alert('搜索失败，请稍后重试');
  } finally {
    loading.value = false;
  }
};

// 监听页码变化自动搜索
watch(page, () => {
  if (keyword.value.trim()) {
    handleSearch();
  }
});
</script>

<style scoped>
.search-component {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.search-box {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.search-box input {
  flex: 1;
  padding: 10px;
  font-size: 16px;
}

.search-box button {
  padding: 10px 20px;
  font-size: 16px;
}

.result-item {
  border: 1px solid #ccc;
  margin: 10px 0;
  padding: 15px;
  border-radius: 5px;
}

.result-type {
  color: #999;
  font-size: 12px;
  margin-bottom: 5px;
}

.pagination {
  margin-top: 20px;
  text-align: center;
}

.pagination button {
  margin: 0 10px;
  padding: 5px 15px;
}
</style>
```

## 响应数据结构

### 成功响应

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
        "extra": {
          "coverImage": "https://...",
          "weight": 100,
          "status": 2,
          "views": 1000
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

### 错误响应

```json
{
  "code": "B0001",
  "data": null,
  "msg": "搜索关键词不能为空"
}
```

## TypeScript 类型定义

```typescript
// 搜索请求参数
interface SearchRequest {
  keyword: string;
  types?: string[];
  page?: number;
  size?: number;
}

// 搜索结果项
interface SearchResult {
  type: string;
  id: number | string;
  title: string;
  desc: string;
  extra: Record<string, any>;
}

// 搜索响应
interface SearchResponse {
  results: SearchResult[];
  total: number;
  page: number;
  size: number;
  totalPages: number;
}

// API 响应
interface ApiResponse<T> {
  code: string;
  data: T;
  msg: string;
}

// 使用示例
async function search(params: SearchRequest): Promise<SearchResponse> {
  const response = await axios.post<ApiResponse<SearchResponse>>(
    'http://localhost:8080/search',
    params
  );
  
  if (response.data.code === '00000') {
    return response.data.data;
  } else {
    throw new Error(response.data.msg);
  }
}
```

## 支持的搜索类型

| 类型值 | 说明 | 搜索字段 |
|--------|------|----------|
| `article` | 文章 | title, description |
| `member` | 成员 | name, introduction, organization |
| `course` | 课程 | title, description |
| `organization` | 机构 | name, address |
| `student` | 学生 | name, signature |

## 常见问题

### 1. 跨域问题

如果遇到跨域问题，需要在后端配置 CORS：

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // 前端地址
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
```

### 2. 关键词为空

后端已添加验证，如果关键词为空会返回错误：

```json
{
  "code": "B0001",
  "msg": "搜索关键词不能为空"
}
```

前端应该在发送请求前进行验证。

### 3. 分页处理

- `page` 从 1 开始
- `totalPages` 表示总页数
- 可以根据 `total` 和 `size` 计算总页数：`Math.ceil(total / size)`

## 完整示例项目

更多示例代码请参考：
- [API 文档](SEARCH_API.md)
- [测试脚本](test_search_api.sh)

## 联系方式

如有问题，请联系后端开发团队。

