@echo off
chcp 65001 >nul
echo ========================================
echo 日志查看工具
echo ========================================
echo.
echo 请选择要查看的子模块:
echo.
echo 1. Sample-Spring-AI
echo 2. Multi-LLM-Provider
echo 3. Spring-AI-Dependency
echo 4. Streaming-Demo
echo 5. Function-Calling-Demo
echo 6. Prompt-Engineering-Demo
echo 7. Agent-Workflow-Demo
echo 8. RAG-Demo
echo 9. All-Dependencies-Demo
echo 0. 退出
echo.

set /p choice="请输入选项 (0-9): "

if "%choice%"=="1" set module=Sample-Spring-AI
if "%choice%"=="2" set module=Multi-LLM-Provider
if "%choice%"=="3" set module=Spring-AI-Dependency
if "%choice%"=="4" set module=Streaming-Demo
if "%choice%"=="5" set module=Function-Calling-Demo
if "%choice%"=="6" set module=Prompt-Engineering-Demo
if "%choice%"=="7" set module=Agent-Workflow-Demo
if "%choice%"=="8" set module=RAG-Demo
if "%choice%"=="9" set module=All-Dependencies-Demo
if "%choice%"=="0" exit /b

if not defined module (
    echo 无效的选项！
    pause
    exit /b
)

echo.
echo 查看 %module% 的日志...
echo.

if not exist "%module%\logs" (
    echo [提示] logs 目录不存在，请先启动应用
    pause
    exit /b
)

echo 日志文件列表:
echo.
dir /b "%module%\logs\*.log"
echo.

set /p logchoice="请选择操作: [1]查看所有日志 [2]查看错误日志 [3]实时监控: "

if "%logchoice%"=="1" (
    if exist "%module%\logs\*.log" (
        for %%f in ("%module%\logs\*.log") do (
            if not "%%~nxf"=="error.log" (
                echo.
                echo ========== %%~nxf ==========
                type "%%f"
            )
        )
    )
)

if "%logchoice%"=="2" (
    if exist "%module%\logs\error.log" (
        type "%module%\logs\error.log"
    ) else (
        echo 没有错误日志
    )
)

if "%logchoice%"=="3" (
    echo.
    echo 实时监控日志 (Ctrl+C 退出)...
    echo.
    powershell -Command "Get-Content '%module%\logs\*.log' -Wait -Tail 50"
)

echo.
pause
