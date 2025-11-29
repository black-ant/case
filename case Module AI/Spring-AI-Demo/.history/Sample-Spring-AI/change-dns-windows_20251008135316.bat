@echo off
echo ========================================
echo Windows DNS 修改工具（需要管理员权限）
echo ========================================
echo.
echo 此脚本将修改你的网络适配器 DNS 设置
echo 建议使用公共 DNS 服务器以提高解析成功率
echo.
echo 可选的公共 DNS:
echo 1. 阿里云 DNS (推荐国内使用)
echo    首选: 223.5.5.5
echo    备用: 223.6.6.6
echo.
echo 2. 腾讯 DNS
echo    首选: 119.29.29.29
echo    备用: 182.254.116.116
echo.
echo 3. Google DNS (需要国际网络)
echo    首选: 8.8.8.8
echo    备用: 8.8.4.4
echo.
echo ========================================
echo.

REM 检查管理员权限
net session >nul 2>&1
if %errorLevel% NEQ 0 (
    echo [错误] 需要管理员权限！
    echo 请右键点击此文件，选择"以管理员身份运行"
    pause
    exit /b 1
)

echo 请选择要使用的 DNS:
echo 1 - 阿里云 DNS (223.5.5.5)
echo 2 - 腾讯 DNS (119.29.29.29)
echo 3 - Google DNS (8.8.8.8)
echo 4 - 恢复自动获取 DNS
echo.
set /p choice="请输入选项 (1-4): "

if "%choice%"=="1" (
    set DNS1=223.5.5.5
    set DNS2=223.6.6.6
    set DNS_NAME=阿里云 DNS
) else if "%choice%"=="2" (
    set DNS1=119.29.29.29
    set DNS2=182.254.116.116
    set DNS_NAME=腾讯 DNS
) else if "%choice%"=="3" (
    set DNS1=8.8.8.8
    set DNS2=8.8.4.4
    set DNS_NAME=Google DNS
) else if "%choice%"=="4" (
    echo.
    echo 正在恢复自动获取 DNS...
    for /f "tokens=1,2,*" %%a in ('netsh interface show interface ^| findstr "已连接"') do (
        netsh interface ip set dns "%%c" dhcp
        echo 已恢复 "%%c" 为自动获取 DNS
    )
    echo.
    echo 完成！
    pause
    exit /b 0
) else (
    echo 无效的选项！
    pause
    exit /b 1
)

echo.
echo 正在设置 DNS 为 %DNS_NAME%...
echo.

REM 获取所有已连接的网络接口并设置 DNS
for /f "tokens=1,2,*" %%a in ('netsh interface show interface ^| findstr "已连接"') do (
    echo 正在配置网络接口: %%c
    netsh interface ip set dns "%%c" static %DNS1% primary
    netsh interface ip add dns "%%c" %DNS2% index=2
    echo   首选 DNS: %DNS1%
    echo   备用 DNS: %DNS2%
    echo.
)

echo ========================================
echo DNS 设置完成！
echo ========================================
echo.
echo 现在测试 DNS 解析...
nslookup api.siliconflow.cn
echo.
echo 如果上面显示了 IP 地址，说明 DNS 配置成功！
echo.
pause
