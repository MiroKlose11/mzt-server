#!/bin/bash

# 搜索API测试脚本
# 使用方法: bash test_search_api.sh

BASE_URL="http://localhost:8080"

echo "================================"
echo "搜索API测试脚本"
echo "================================"
echo ""

# 测试1: POST方式搜索全部类型
echo "测试1: POST方式搜索全部类型"
echo "----------------------------"
curl -X POST "${BASE_URL}/search" \
  -H "Content-Type: application/json" \
  -d '{
    "keyword": "医美",
    "page": 1,
    "size": 5
  }' | jq '.'
echo ""
echo ""

# 测试2: POST方式搜索指定类型
echo "测试2: POST方式搜索指定类型（文章和成员）"
echo "----------------------------"
curl -X POST "${BASE_URL}/search" \
  -H "Content-Type: application/json" \
  -d '{
    "keyword": "医美",
    "types": ["article", "member"],
    "page": 1,
    "size": 5
  }' | jq '.'
echo ""
echo ""

# 测试3: GET方式搜索
echo "测试3: GET方式搜索"
echo "----------------------------"
curl -X GET "${BASE_URL}/search?keyword=医美&types=article,member&page=1&size=5" | jq '.'
echo ""
echo ""

# 测试4: 搜索单个类型（课程）
echo "测试4: 搜索单个类型（课程）"
echo "----------------------------"
curl -X POST "${BASE_URL}/search" \
  -H "Content-Type: application/json" \
  -d '{
    "keyword": "课程",
    "types": ["course"],
    "page": 1,
    "size": 5
  }' | jq '.'
echo ""
echo ""

# 测试5: 分页测试（第2页）
echo "测试5: 分页测试（第2页）"
echo "----------------------------"
curl -X POST "${BASE_URL}/search" \
  -H "Content-Type: application/json" \
  -d '{
    "keyword": "医美",
    "page": 2,
    "size": 5
  }' | jq '.'
echo ""
echo ""

echo "================================"
echo "测试完成！"
echo "================================"

