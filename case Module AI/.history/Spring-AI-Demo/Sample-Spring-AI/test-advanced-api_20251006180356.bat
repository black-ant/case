@echo off
REM ChatClient 高级配置 API 测试脚本 (Windows)

set BASE_URL=http://localhost:8080

echo ==========================================
echo ChatClient 高级配置 API 测试
echo ==========================================
echo.

REM 1. 基础选项配置
echo 1. 测试基础选项配置 (temperature + maxTokens)
curl -X POST "%BASE_URL%/api/advanced/basic-options" -H "Content-Type: text/plain" -d "什么是 Spring AI？"
echo.
echo.

REM 2. 系统提示词
echo 2. 测试系统提示词
curl -X POST "%BASE_URL%/api/advanced/system-prompt" -H "Content-Type: application/json" -d "{\"systemPrompt\": \"你是一个幽默风趣的编程导师\", \"message\": \"解释一下什么是递归\"}"
echo.
echo.

REM 3. 模板和变量
echo 3. 测试模板和变量
curl -X POST "%BASE_URL%/api/advanced/template" -H "Content-Type: application/json" -d "{\"userName\": \"李明\", \"topic\": \"微服务架构\"}"
echo.
echo.

REM 7. 完整响应信息
echo 7. 测试完整响应信息（包括 token 使用）
curl -X POST "%BASE_URL%/api/advanced/full-response" -H "Content-Type: text/plain" -d "什么是 Spring Boot？"
echo.
echo.

REM 10. 精确回答
echo 10. 测试精确回答配置
curl -X POST "%BASE_URL%/api/advanced/precise-answer" -H "Content-Type: text/plain" -d "Java 中 HashMap 的时间复杂度是多少？"
echo.
echo.

echo ==========================================
echo 测试完成！
echo ==========================================
pause
