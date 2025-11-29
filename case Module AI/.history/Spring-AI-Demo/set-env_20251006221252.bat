@echo off
REM ==========================================
REM Spring AI Demo 环境变量配置脚本
REM ==========================================

echo.
echo ==========================================
echo 正在配置 Spring AI Demo 环境变量...
echo ==========================================
echo.

REM ==================== SiliconFlow + DeepSeek 配置 ====================
REM API Key（已配置为 SiliconFlow）
set OPENAI_API_KEY=sk-vgerpwjpbonxajygdzkqjkitstqjshsikflawxzqhcgzecum

REM Base URL（使用 SiliconFlow）
set OPENAI_BASE_URL=https://api.siliconflow.cn

REM 模型（使用 DeepSeek-V3）
set OPENAI_MODEL=deepseek-ai/DeepSeek-V3

REM Temperature 参数
set OPENAI_TEMPERATURE=0.7

REM 嵌入模型（使用中文嵌入模型）
set OPENAI_EMBEDDING_MODEL=BAAI/bge-large-zh-v1.5

REM 服务器端口
set SERVER_PORT=8080

REM 日志级别
set LOG_LEVEL_AI=INFO
set LOG_LEVEL_APP=INFO

echo ✓ 环境变量配置完成！
echo.
echo 已配置的环境变量：
echo   OPENAI_API_KEY = %OPENAI_API_KEY%
echo   OPENAI_BASE_URL = %OPENAI_BASE_URL%
echo   OPENAI_MODEL = %OPENAI_MODEL%
echo   OPENAI_TEMPERATURE = %OPENAI_TEMPERATURE%
echo   OPENAI_EMBEDDING_MODEL = %OPENAI_EMBEDDING_MODEL%
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
