@echo off
REM ========================================
REM  AI 智能客服系统 - 后端启动（UTF-8）
REM ========================================

REM 设置控制台编码为 UTF-8
chcp 65001 >nul

echo ========================================
echo    AI 智能客服系统 - 后端启动
echo ========================================
echo.

echo [1/3] 检查 Java 环境...
java -version >nul 2>&1
if errorlevel 1 (
    echo ❌ 错误: 未检测到 Java，请先安装 Java 17+
    pause
    exit /b 1
)
echo ✓ Java 环境正常

echo.
echo [2/3] 检查 Maven 环境...
where mvn >nul 2>&1
if errorlevel 1 (
    echo ❌ 错误: 未检测到 Maven
    echo.
    echo 请使用 IDE (IntelliJ IDEA / Eclipse) 直接运行
    pause
    exit /b 1
)
echo ✓ Maven 环境正常

echo.
echo [3/3] 启动 Spring Boot 应用...
echo.
echo ========================================
echo  🚀 后端服务启动中...
echo  🔧 端口: 8080
echo ========================================
echo.

REM 设置 Maven 和 Java 编码选项
set MAVEN_OPTS=-Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8 -Dproject.build.sourceEncoding=UTF-8
set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8

mvn spring-boot:run

pause
