@echo off
REM Function Calling 测试脚本 (Windows)

set BASE_URL=http://localhost:8080

echo ==========================================
echo Function Calling 功能测试
echo ==========================================
echo.

echo 1. 测试天气查询函数
curl -X POST "%BASE_URL%/api/function/auto" -H "Content-Type: application/json" -d "{\"message\": \"北京今天天气怎么样？\"}"
echo.
echo.

echo 2. 测试股票查询函数
curl -X POST "%BASE_URL%/api/function/auto" -H "Content-Type: application/json" -d "{\"message\": \"苹果公司的股票现在多少钱？\"}"
echo.
echo.

echo 3. 测试计算器函数
curl -X POST "%BASE_URL%/api/function/auto" -H "Content-Type: application/json" -d "{\"message\": \"帮我算一下 123 加 456\"}"
echo.
echo.

echo 4. 测试链式调用（多个函数）
curl -X POST "%BASE_URL%/api/function/chain" -H "Content-Type: application/json" -d "{\"message\": \"北京和上海今天哪个城市更热？温差是多少？\"}"
echo.
echo.

echo 5. 测试复杂查询
curl -X POST "%BASE_URL%/api/function/auto" -H "Content-Type: application/json" -d "{\"message\": \"深圳天气如何？顺便帮我算一下 100 乘以 50\"}"
echo.
echo.

echo ==========================================
echo 测试完成！
echo ==========================================
pause
