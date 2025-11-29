@echo off
REM 动态函数注册测试脚本 (Windows)

set BASE_URL=http://localhost:8080

echo ==========================================
echo 动态函数注册功能测试
echo ==========================================
echo.

echo 1. 批量注册示例函数
curl -X POST "%BASE_URL%/api/dynamic/register/batch"
echo.
echo.

echo 2. 查看已注册的函数列表
curl "%BASE_URL%/api/dynamic/list"
echo.
echo.

echo 3. 测试问候函数
curl -X POST "%BASE_URL%/api/dynamic/chat/all" -H "Content-Type: application/json" -d "{\"message\": \"你好，我叫张三\"}"
echo.
echo.

echo 4. 测试时间函数
curl -X POST "%BASE_URL%/api/dynamic/chat/all" -H "Content-Type: application/json" -d "{\"message\": \"现在几点了？\"}"
echo.
echo.

echo 5. 测试随机数函数
curl -X POST "%BASE_URL%/api/dynamic/chat/all" -H "Content-Type: application/json" -d "{\"message\": \"帮我生成一个 1 到 100 的随机数\"}"
echo.
echo.

echo 6. 注册自定义函数
curl -X POST "%BASE_URL%/api/dynamic/register/example" -H "Content-Type: application/json" -d "{\"functionName\": \"myGreeting\", \"description\": \"自定义问候\", \"functionType\": \"greeting\"}"
echo.
echo.

echo 7. 检查函数是否存在
curl "%BASE_URL%/api/dynamic/check/myGreeting"
echo.
echo.

echo 8. 注销函数
curl -X DELETE "%BASE_URL%/api/dynamic/unregister/myGreeting"
echo.
echo.

echo 9. 再次检查函数
curl "%BASE_URL%/api/dynamic/check/myGreeting"
echo.
echo.

echo ==========================================
echo 测试完成！
echo ==========================================
pause
