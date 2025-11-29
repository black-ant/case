@echo off
REM 快速启动脚本 (Windows)

echo ==========================================
echo Spring AI Demo 快速启动
echo ==========================================
echo.

REM 检查 .env 文件
if not exist .env (
    echo [错误] 找不到 .env 文件
    echo.
    echo 请先配置环境变量：
    echo   1. 复制配置模板: copy .env.example .env
    echo   2. 编辑 .env 文件，填入你的 API Key
    echo   3. 重新运行此脚本
    echo.
    pause
    exit /b 1
)

echo [1/3] 加载环境变量...
for /f "tokens=1,2 delims==" %%a in (.env) do (
    set "line=%%a"
    if not "!line:~0,1!"=="#" (
        set %%a=%%b
        echo   ✓ %%a
    )
)

echo.
echo [2/3] 选择要启动的项目：
echo.
echo   1. Sample-Spring-AI          - 基础入门
echo   2. Multi-LLM-Provider        - 多提供商集成
echo   3. Function-Calling-Demo     - 函数调用
echo   4. RAG-Demo                  - 检索增强生成
echo   5. Streaming-Demo            - 流式响应
echo   6. Agent-Workflow-Demo       - 智能体工作流
echo   7. Prompt-Engineering-Demo   - 提示工程
echo.

set /p choice="请输入项目编号 (1-7): "

if "%choice%"=="1" set project=Sample-Spring-AI
if "%choice%"=="2" set project=Multi-LLM-Provider
if "%choice%"=="3" set project=Function-Calling-Demo
if "%choice%"=="4" set project=RAG-Demo
if "%choice%"=="5" set project=Streaming-Demo
if "%choice%"=="6" set project=Agent-Workflow-Demo
if "%choice%"=="7" set project=Prompt-Engineering-Demo

if not defined project (
    echo [错误] 无效的选择
    pause
    exit /b 1
)

echo.
echo [3/3] 启动项目: %project%
echo.
cd %project%
mvn spring-boot:run

pause
