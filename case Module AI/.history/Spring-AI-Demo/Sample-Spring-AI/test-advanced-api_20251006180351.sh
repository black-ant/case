#!/bin/bash

# ChatClient 高级配置 API 测试脚本

BASE_URL="http://localhost:8080"

echo "=========================================="
echo "ChatClient 高级配置 API 测试"
echo "=========================================="
echo ""

# 1. 基础选项配置
echo "1. 测试基础选项配置 (temperature + maxTokens)"
curl -X POST "$BASE_URL/api/advanced/basic-options" \
  -H "Content-Type: text/plain" \
  -d "什么是 Spring AI？" \
  -w "\n\n"

# 2. 系统提示词
echo "2. 测试系统提示词"
curl -X POST "$BASE_URL/api/advanced/system-prompt" \
  -H "Content-Type: application/json" \
  -d '{
    "systemPrompt": "你是一个幽默风趣的编程导师",
    "message": "解释一下什么是递归"
  }' \
  -w "\n\n"

# 3. 模板和变量
echo "3. 测试模板和变量"
curl -X POST "$BASE_URL/api/advanced/template" \
  -H "Content-Type: application/json" \
  -d '{
    "userName": "李明",
    "topic": "微服务架构"
  }' \
  -w "\n\n"

# 4. 高级选项配置
echo "4. 测试高级选项配置 (topP + penalties)"
curl -X POST "$BASE_URL/api/advanced/advanced-options" \
  -H "Content-Type: text/plain" \
  -d "写一篇关于人工智能的文章" \
  -w "\n\n"

# 5. 流式响应
echo "5. 测试流式响应配置"
curl -N -X POST "$BASE_URL/api/advanced/stream-options" \
  -H "Content-Type: text/plain" \
  -d "请详细介绍 Spring AI 的核心功能" \
  -w "\n\n"

# 6. Prompt 对象
echo "6. 测试 Prompt 对象"
curl -X POST "$BASE_URL/api/advanced/prompt-object" \
  -H "Content-Type: application/json" \
  -d '{
    "systemPrompt": "你是一个 Python 专家",
    "message": "解释装饰器的原理"
  }' \
  -w "\n\n"

# 7. 完整响应信息
echo "7. 测试完整响应信息（包括 token 使用）"
curl -X POST "$BASE_URL/api/advanced/full-response" \
  -H "Content-Type: text/plain" \
  -d "什么是 Spring Boot？" \
  -w "\n\n"

# 8. 指定模型
echo "8. 测试指定模型"
curl -X POST "$BASE_URL/api/advanced/specific-model" \
  -H "Content-Type: application/json" \
  -d '{
    "message": "解释量子计算的原理",
    "model": "gpt-3.5-turbo"
  }' \
  -w "\n\n"

# 9. 创造性写作
echo "9. 测试创造性写作配置"
curl -X POST "$BASE_URL/api/advanced/creative-writing" \
  -H "Content-Type: application/json" \
  -d '{
    "topic": "未来世界的程序员"
  }' \
  -w "\n\n"

# 10. 精确回答
echo "10. 测试精确回答配置"
curl -X POST "$BASE_URL/api/advanced/precise-answer" \
  -H "Content-Type: text/plain" \
  -d "Java 中 HashMap 的时间复杂度是多少？" \
  -w "\n\n"

echo "=========================================="
echo "测试完成！"
echo "=========================================="
