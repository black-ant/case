@echo off
chcp 65001 >nul
echo ========================================
echo   AI 智能客服系统 - 智能启动脚本
echo ========================================
echo.

echo 请选择启动方式:
echo.
echo  1. 启动后端 (Spring Boot)
echo  2. 启动前端 (React)
echo  3. 同时启动前后端
echo  4. 退出
echo.
set /p choice=请输入选项 (1-4): 

if "%choice%"=="1" goto backend
if "%choice%"=="2" goto frontend
if "%choice%"=="3" goto both
if "%choice%"=="4" goto end

echo 无效选项，请重新运行脚本
pause
goto end

:backend
echo.
echo [启动后端服务]
echo ========================================
call start-backend.bat
goto end

:frontend
echo.
echo [启动前端服务]
echo ========================================
call start-web.bat
goto end

:both
echo.
echo [同时启动前后端]
echo ========================================
echo.
echo 检查环境...

REM 检查 Java
java -version >nul 2>&1
if errorlevel 1 (
    echo ❌ Java 未安装
    pause
    goto end
)
echo ✓ Java 环境正常

REM 检查 Maven
where mvn >nul 2>&1
if errorlevel 1 (
    echo ❌ Maven 未安装，请使用 IDE 启动后端
    echo.
    echo 是否只启动前端? (Y/N)
    set /p only_frontend=
    if /i "%only_frontend%"=="Y" goto frontend
    goto end
)
echo ✓ Maven 环境正常

REM 检查 Node.js
node -v >nul 2>&1
if errorlevel 1 (
    echo ❌ Node.js 未安装
    pause
    goto end
)
echo ✓ Node.js 环境正常

echo.
echo 启动后端服务...
start "AI客服-后端" cmd /k "chcp 65001 >nul && set MAVEN_OPTS=-Dfile.encoding=UTF-8 && mvn spring-boot:run"
echo ✓ 后端服务启动中... (端口 8080)

timeout /t 5 /nobreak >nul

echo.
echo 启动前端服务...
start "AI客服-前端" cmd /k "chcp 65001 >nul && cd web && npm start"
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
echo    - 两个服务窗口已打开
echo    - 关闭窗口即可停止对应服务
echo    - 首次启动可能需要下载依赖
echo.
pause
goto end

:end
