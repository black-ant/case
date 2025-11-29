#!/bin/bash

BASE_URL="http://localhost:8080/api/customer-service"

echo "========================================"
echo "AI 客服系统 API 测试脚本"
echo "========================================"
echo ""

# 1. 创建会话
echo "[1] 创建会话..."
SESSION_RESPONSE=$(curl -s -X POST "$BASE_URL/session" \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST001",
    "customerName": "张三"
  }')

echo "响应: $SESSION_RESPONSE"
SESSION_ID=$(echo $SESSION_RESPONSE | grep -o '"sessionId":"[^"]*' | cut -d'"' -f4)
echo "会话ID: $SESSION_ID"
echo ""

# 2. 查询订单
echo "[2] 测试订单查询..."
curl -s -X POST "$BASE_URL/chat" \
  -H "Content-Type: application/json" \
  -d "{
    \"sessionId\": \"$SESSION_ID\",
    \"customerId\": \"CUST001\",
    \"message\": \"查询订单号 ORD20251019001\"
  }" | jq '.'
echo ""

# 3. 知识库查询
echo "[3] 测试知识库查询..."
curl -s -X POST "$BASE_URL/chat" \
  -H "Content-Type: application/json" \
  -d "{
    \"sessionId\": \"$SESSION_ID\",
    \"customerId\": \"CUST001\",
    \"message\": \"退货政策是什么？\"
  }" | jq '.'
echo ""

# 4. 结束会话
echo "[4] 结束会话..."
curl -s -X POST "$BASE_URL/session/$SESSION_ID/end" | jq '.'
echo ""

echo "========================================"
echo "测试完成"
echo "========================================"
