#!/bin/bash

echo "========================================"
echo "AI 智能客服系统启动脚本"
echo "========================================"
echo ""

# 检查 OPENAI_API_KEY
if [ -z "$OPENAI_API_KEY" ]; then
    echo "[错误] 请先设置 OPENAI_API_KEY 环境变量"
    echo ""
    echo "设置方法："
    echo "export OPENAI_API_KEY=your-api-key-here"
    echo ""
    exit 1
fi

echo "[信息] 检测到 OPENAI_API_KEY 已设置"
echo ""

echo "[信息] 正在启动应用..."
echo ""

mvn clean spring-boot:run
