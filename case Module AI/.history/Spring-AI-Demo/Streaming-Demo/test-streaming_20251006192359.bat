@echo off
REM 流式响应测试脚本 (Windows)

set BASE_URL=http://localhost:8080

echo ==========================================
echo 流式响应功能测试
echo ==========================================
echo.

echo 1. 基础流式响应
curl -N -X POST "%BASE_URL%/api/stream/basic" -H "Content-Type: application/json" -d "{\"message\": \"请用一句话介绍 Spring AI\"}"
echo.
echo.

echo 2. 带延迟的流式响应（打字机效果）
curl -N -X POST "%BASE_URL%/api/stream/delayed" -H "Content-Type: application/json" -d "{\"message\": \"你好\", \"delayMillis\": 100}"
echo.
echo.

echo 3. 带系统提示词的流式响应
curl -N -X POST "%BASE_URL%/api/stream/with-system" -H "Content-Type: application/json" -d "{\"systemPrompt\": \"你是一个幽默的助手\", \"message\": \"介绍一下 Java\"}"
echo.
echo.

echo ==========================================
echo 测试完成！
echo ==========================================
pause
