@echo off
chcp 65001 >nul
echo ========================================
echo 日志配置测试脚本
echo ========================================
echo.

echo 检查 logback-spring.xml 配置文件...
echo.

set count=0

for /d %%D in (Agent-Workflow-Demo All-Dependencies-Demo Function-Calling-Demo Multi-LLM-Provider Prompt-Engineering-Demo RAG-Demo Sample-Spring-AI Spring-AI-Dependency Streaming-Demo) do (
    if exist "%%D\src\main\resources\logback-spring.xml" (
        echo [✓] %%D - 配置文件存在
        set /a count+=1
    ) else (
        echo [✗] %%D - 配置文件缺失
    )
)

echo.
echo ========================================
echo 总计: %count%/9 个子模块已配置日志
echo ========================================
echo.

if %count%==9 (
    echo [成功] 所有子模块都已配置日志！
    echo.
    echo 下一步:
    echo 1. 启动任意子模块应用
    echo 2. 查看 logs/ 目录下的日志文件
    echo 3. 参考 LOGGING_GUIDE.md 了解更多信息
) else (
    echo [警告] 部分子模块缺少日志配置
)

echo.
pause
