#!/bin/bash
# Bash 脚本：加载 .env 文件中的环境变量

ENV_FILE=".env"

if [ -f "$ENV_FILE" ]; then
    echo "正在加载环境变量从 $ENV_FILE ..."
    
    # 导出环境变量
    export $(grep -v '^#' $ENV_FILE | xargs)
    
    echo "✓ 环境变量加载完成！"
    echo ""
    echo "已加载的配置："
    grep -v '^#' $ENV_FILE | grep -v '^$' | while IFS='=' read -r key value; do
        if [ ! -z "$key" ]; then
            echo "  ✓ $key"
        fi
    done
    
    echo ""
    echo "现在可以运行: mvn spring-boot:run"
else
    echo "错误：找不到 .env 文件"
    echo "请先复制 .env.example 为 .env 并填入配置"
    echo "  cp .env.example .env"
    exit 1
fi
