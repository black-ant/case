#!/bin/bash
# 快速启动脚本 (Linux/Mac)

echo "=========================================="
echo "Spring AI Demo 快速启动"
echo "=========================================="
echo ""

# 检查 .env 文件
if [ ! -f .env ]; then
    echo "[错误] 找不到 .env 文件"
    echo ""
    echo "请先配置环境变量："
    echo "  1. 复制配置模板: cp .env.example .env"
    echo "  2. 编辑 .env 文件，填入你的 API Key"
    echo "  3. 重新运行此脚本"
    echo ""
    exit 1
fi

echo "[1/3] 加载环境变量..."
export $(grep -v '^#' .env | xargs)
echo "  ✓ 环境变量已加载"

echo ""
echo "[2/3] 选择要启动的项目："
echo ""
echo "  1. Sample-Spring-AI          - 基础入门"
echo "  2. Multi-LLM-Provider        - 多提供商集成"
echo "  3. Function-Calling-Demo     - 函数调用"
echo "  4. RAG-Demo                  - 检索增强生成"
echo "  5. Streaming-Demo            - 流式响应"
echo "  6. Agent-Workflow-Demo       - 智能体工作流"
echo "  7. Prompt-Engineering-Demo   - 提示工程"
echo ""

read -p "请输入项目编号 (1-7): " choice

case $choice in
    1) project="Sample-Spring-AI" ;;
    2) project="Multi-LLM-Provider" ;;
    3) project="Function-Calling-Demo" ;;
    4) project="RAG-Demo" ;;
    5) project="Streaming-Demo" ;;
    6) project="Agent-Workflow-Demo" ;;
    7) project="Prompt-Engineering-Demo" ;;
    *)
        echo "[错误] 无效的选择"
        exit 1
        ;;
esac

echo ""
echo "[3/3] 启动项目: $project"
echo ""
cd "$project"
mvn spring-boot:run
