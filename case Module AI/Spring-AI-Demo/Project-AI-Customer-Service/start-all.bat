@echo off
chcp 65001 >nul
echo ========================================
echo   AI 智能客服系统 - 一键启动脚本
echo ========================================
echo.

echo 此脚本将启动:
echo   1. 后端服务 (Spring Boot - 端口 8080)
echo   2. 前端服务 (React - 端口 3000)
echo.
echo 请确保:
echo   ✓ 已安装 Java 17+
echo   ✓ 已安装 Node.js 16+
echo   ✓ 已配置 application.properties
echo.
pause

echo.
echo [启动后端服务]
echo ========================================
echo 检查 Maven 环境...
where mvn >nul 2>&1
if errorlevel 1 (
    echo ❌ 错误: 未检测到 Maven，请先安装 Maven
    echo 下载地址: https://maven.apache.org/download.cgi
    pause
    exit /b 1
)
echo ✓ Maven 环境正常
start "AI客服-后端" cmd /k "chcp 65001 >nul && set MAVEN_OPTS=-Dfile.encoding=UTF-8 && mvn spring-boot:run"
echo ✓ 后端服务启动中... (端口 8080)
echo.

timeout /t 5 /nobreak >nul

echo [启动前端服务]
echo ========================================
start "AI客服-前端" cmd /k "chcp 65001 >nul && cd /d %~dp0 && start-web.bat"
echo ✓ 前端服务启动中... (端口 3721)
echo.

echo ========================================
echo  🎉 系统启动完成！
echo ========================================
echo.
echo  📱 前端访问: http://localhost:3721
echo  🔧 后端接口: http://localhost:8080
echo.
echo  提示: 
echo    - 两个服务窗口会自动打开
echo    - 关闭窗口即可停止对应服务
echo    - 首次启动可能需要下载依赖
echo.
pause
