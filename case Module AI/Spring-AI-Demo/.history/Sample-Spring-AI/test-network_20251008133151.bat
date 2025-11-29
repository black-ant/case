@echo off
echo ========================================
echo 网络连接诊断工具
echo ========================================
echo.

echo [1] 测试 DNS 解析...
nslookup api.siliconflow.cn
echo.

echo [2] 测试网络连通性...
ping -n 4 api.siliconflow.cn
echo.

echo [3] 当前 DNS 配置...
ipconfig /all | findstr /C:"DNS"
echo.

echo ========================================
echo 诊断完成
echo ========================================
echo.
echo 如果上述测试失败，请参考 NETWORK-TROUBLESHOOTING.txt 文件
pause
