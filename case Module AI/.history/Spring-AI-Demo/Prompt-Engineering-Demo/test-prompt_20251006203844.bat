@echo off
REM 提示工程测试脚本 (Windows)

set BASE_URL=http://localhost:8080

echo ==========================================
echo 提示工程功能测试
echo ==========================================
echo.

echo 1. 测试角色扮演模板
curl -X POST "%BASE_URL%/api/prompt/template/role-play" -H "Content-Type: application/json" -d "{\"role\": \"资深软件架构师\", \"scenario\": \"正在进行系统架构评审\", \"userInput\": \"我们应该使用微服务架构吗？\"}"
echo.
echo.

echo 2. 测试带记忆的对话（第一轮）
curl -X POST "%BASE_URL%/api/prompt/memory/chat" -H "Content-Type: application/json" -d "{\"sessionId\": \"test123\", \"message\": \"我叫张三，是一名Java开发工程师\"}"
echo.
echo.

echo 3. 测试带记忆的对话（第二轮）
curl -X POST "%BASE_URL%/api/prompt/memory/chat" -H "Content-Type: application/json" -d "{\"sessionId\": \"test123\", \"message\": \"我叫什么名字？我是做什么的？\"}"
echo.
echo.

echo 4. 获取对话历史
curl "%BASE_URL%/api/prompt/memory/history/test123"
echo.
echo.

echo ==========================================
echo 测试完成！
echo ==========================================
pause
