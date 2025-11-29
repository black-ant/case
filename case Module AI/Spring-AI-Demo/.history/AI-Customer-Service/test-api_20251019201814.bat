@echo off
setlocal enabledelayedexpansion

set BASE_URL=http://localhost:8080/api/customer-service

echo ========================================
echo AI 客服系统 API 测试脚本
echo ========================================
echo.

REM 1. 创建会话
echo [1] 创建会话...
curl -X POST "%BASE_URL%/session" ^
  -H "Content-Type: application/json" ^
  -d "{\"customerId\":\"CUST001\",\"customerName\":\"张三\"}"
echo.
echo.

REM 提示用户输入 sessionId
set /p SESSION_ID="请输入上面返回的 sessionId: "
echo.

REM 2. 查询订单
echo [2] 测试订单查询...
curl -X POST "%BASE_URL%/chat" ^
  -H "Content-Type: application/json" ^
  -d "{\"sessionId\":\"%SESSION_ID%\",\"customerId\":\"CUST001\",\"message\":\"查询订单号 ORD20251019001\"}"
echo.
echo.

REM 3. 知识库查询
echo [3] 测试知识库查询...
curl -X POST "%BASE_URL%/chat" ^
  -H "Content-Type: application/json" ^
  -d "{\"sessionId\":\"%SESSION_ID%\",\"customerId\":\"CUST001\",\"message\":\"退货政策是什么？\"}"
echo.
echo.

REM 4. 结束会话
echo [4] 结束会话...
curl -X POST "%BASE_URL%/session/%SESSION_ID%/end"
echo.
echo.

echo ========================================
echo 测试完成
echo ========================================
pause
