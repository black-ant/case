@echo off
REM ==========================================
REM Spring AI Demo 环境变量配置示例
REM ==========================================

REM ========== 基础配置示例 ==========
REM 最简单的配置，只需要 API Key
set OPENAI_API_KEY=sk-proj-xxxxxxxxxxxxxxxxxxxxx

REM ========== 使用代理示例 ==========
REM 如果你使用代理或中转服务
REM set OPENAI_API_KEY=sk-xxxxxxxxxxxxxxxxxxxxx
REM set OPENAI_BASE_URL=https://api.openai-proxy.com/v1

REM ========== 使用 GPT-4 示例 ==========
REM 如果你想使用 GPT-4 模型
REM set OPENAI_API_KEY=sk-xxxxxxxxxxxxxxxxxxxxx
REM set OPENAI_MODEL=gpt-4

REM ========== 使用本地 LLM 示例 ==========
REM 如果你使用 LocalAI 或其他本地 LLM
REM set OPENAI_API_KEY=not-needed
REM set OPENAI_BASE_URL=http://localhost:8080/v1

REM ========== 使用 Azure OpenAI 示例 ==========
REM 如果你使用 Azure OpenAI（仅 Multi-LLM-Provider 项目）
REM set AZURE_OPENAI_API_KEY=your-azure-key
REM set AZURE_OPENAI_ENDPOINT=https://your-resource.openai.azure.com
REM set AZURE_OPENAI_DEPLOYMENT=gpt-35-turbo

echo 环境变量配置完成！
echo.
echo 当前配置：
echo   OPENAI_API_KEY = %OPENAI_API_KEY%
if defined OPENAI_BASE_URL echo   OPENAI_BASE_URL = %OPENAI_BASE_URL%
if defined OPENAI_MODEL echo   OPENAI_MODEL = %OPENAI_MODEL%
echo.
echo 现在可以启动项目了：
echo   cd Sample-Spring-AI
echo   mvn spring-boot:run
echo.
