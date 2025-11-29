@echo off
REM ==========================================
REM Spring AI Demo 环境变量配置脚本
REM ==========================================
REM 
REM 使用方法：
REM 1. 编辑此文件，填入你的配置
REM 2. 运行此脚本：set-env.bat
REM 3. 在同一个命令行窗口中启动项目
REM
REM 注意：环境变量只在当前命令行窗口有效
REM ==========================================

echo.
echo ==========================================
echo 正在配置 Spring AI Demo 环境变量...
echo ==========================================
echo.

REM ==================== 必填配置 ====================
REM OpenAI API Key（必填）
set OPENAI_API_KEY=sk-your-api-key-here

REM ==================== 可选配置 ====================
REM OpenAI Base URL（如果使用代理或自定义端点，请修改此项）
set OPENAI_BASE_URL=https://api.openai.com

REM OpenAI 模型（默认：gpt-3.5-turbo）
set OPENAI_MODEL=gpt-3.5-turbo

REM Temperature 参数（默认：0.7）
set OPENAI_TEMPERATURE=0.7

REM 嵌入模型（默认：text-embedding-ada-002）
set OPENAI_EMBEDDING_MODEL=text-embedding-ada-002

REM 服务器端口（默认：8080）
set SERVER_PORT=8080

REM 日志级别
set LOG_LEVEL_AI=INFO
set LOG_LEVEL_APP=INFO

REM ==================== 其他 LLM 配置（仅 Multi-LLM-Provider 需要） ====================
REM Azure OpenAI
REM set AZURE_OPENAI_API_KEY=your-azure-api-key
REM set AZURE_OPENAI_ENDPOINT=https://your-resource.openai.azure.com
REM set AZURE_OPENAI_DEPLOYMENT=gpt-35-turbo

REM Anthropic Claude
REM set ANTHROPIC_API_KEY=sk-ant-your-api-key

REM Google Gemini
REM set VERTEX_AI_PROJECT_ID=your-gcp-project-id
REM set VERTEX_AI_LOCATION=us-central1

REM Ollama
REM set OLLAMA_BASE_URL=http://localhost:11434

REM ==================== 数据库配置（仅 RAG-Demo 使用 PGVector 时需要） ====================
REM set POSTGRES_HOST=localhost
REM set POSTGRES_PORT=5432
REM set POSTGRES_DB=vectordb
REM set POSTGRES_USER=postgres
REM set POSTGRES_PASSWORD=postgres
REM set VECTOR_STORE_TYPE=simple

echo ✓ 环境变量配置完成！
echo.
echo 已配置的环境变量：
echo   OPENAI_API_KEY = %OPENAI_API_KEY%
echo   OPENAI_BASE_URL = %OPENAI_BASE_URL%
echo   OPENAI_MODEL = %OPENAI_MODEL%
echo   OPENAI_TEMPERATURE = %OPENAI_TEMPERATURE%
echo   SERVER_PORT = %SERVER_PORT%
echo.
echo ==========================================
echo 现在可以启动项目了！
echo ==========================================
echo.
echo 启动方式：
echo   1. 使用快速启动脚本：start-project.bat
echo   2. 手动启动：cd [项目目录] ^& mvn spring-boot:run
echo.
echo 示例：
echo   cd Sample-Spring-AI
echo   mvn spring-boot:run
echo.
echo 注意：请在当前命令行窗口中运行，环境变量只在此窗口有效！
echo.
