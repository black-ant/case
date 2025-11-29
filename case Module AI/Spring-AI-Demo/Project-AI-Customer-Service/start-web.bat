@echo off
REM 设置控制台编码为 UTF-8
chcp 65001 >nul
echo ========================================
echo    AI 智能客服系统 - Web 前端启动
echo ========================================
echo.

cd web

echo [1/3] 检查 Node.js 环境...
node -v >nul 2>&1
if errorlevel 1 (
    echo ❌ 错误: 未检测到 Node.js，请先安装 Node.js
    echo 下载地址: https://nodejs.org/
    pause
    exit /b 1
)
echo ✓ Node.js 环境正常

echo.
echo [2/3] 检查依赖包...
if not exist "node_modules" (
    echo 首次运行，正在安装依赖包...
    echo 这可能需要几分钟时间，请耐心等待...
    call npm install
    if errorlevel 1 (
        echo ❌ 依赖安装失败，请检查网络连接
        pause
        exit /b 1
    )
    echo ✓ 依赖安装完成
) else (
    echo ✓ 依赖包已存在
)

echo.
echo [3/3] 启动开发服务器...
echo.
echo ========================================
echo  🚀 前端服务启动中...
echo  📱 访问地址: http://localhost:3721
echo  🔧 后端代理: http://localhost:8080
echo ========================================
echo.
echo 提示: 按 Ctrl+C 可停止服务
echo.

call npm start

pause
