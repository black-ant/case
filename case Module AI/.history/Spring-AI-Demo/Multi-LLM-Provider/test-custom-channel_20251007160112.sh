#!/bin/bash

# 自定义渠道测试脚本

echo "=========================================="
echo "自定义渠道测试"
echo "=========================================="
echo ""

# 配置（请修改为你的实际配置）
BASE_URL="https://api.openai.com/v1"
API_TOKEN="sk-xxx"  # 替换为你的 API Token
MODEL="gpt-3.5-turbo"

echo "配置信息："
echo "Base URL: $BASE_URL"
echo "Model: $MODEL"
echo ""

# 测试 1: 普通对话
echo "测试 1: 普通对话"
echo "----------------------------------------"
curl -X POST http://localhost:8080/api/llm/custom/chat \
  -H "Content-Type: application/json" \
  -d "{
    \"message\": \"你好，请用一句话介绍自己\",
    \"baseUrl\": \"$BASE_URL\",
    \"apiToken\": \"$API_TOKEN\",
    \"model\": \"$MODEL\"
  }"
echo ""
echo ""

# 测试 2: 流式对话
echo "测试 2: 流式对话"
echo "----------------------------------------"
curl -N -X POST http://localhost:8080/api/llm/custom/stream \
  -H "Content-Type: application/json" \
  -d "{
    \"message\": \"请用三句话介绍 Spring AI\",
    \"baseUrl\": \"$BASE_URL\",
    \"apiToken\": \"$API_TOKEN\",
    \"model\": \"$MODEL\"
  }"
echo ""
echo ""

echo "=========================================="
echo "测试完成"
echo "=========================================="
