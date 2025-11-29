@echo off
REM 智能体工作流测试脚本 (Windows)

set BASE_URL=http://localhost:8080

echo ==========================================
echo 智能体工作流功能测试
echo ==========================================
echo.

echo 1. 测试文档分析链
curl -X POST "%BASE_URL%/api/workflow/chain/analyze-document" -H "Content-Type: application/json" -d "{\"content\": \"人工智能正在改变世界。AI 技术在医疗、教育、交通等领域都有广泛应用。未来，AI 将继续发展，为人类带来更多便利。\"}"
echo.
echo.

echo 2. 测试并行市场分析
curl -X POST "%BASE_URL%/api/workflow/parallel/analyze-market" -H "Content-Type: application/json" -d "{\"marketData\": \"股票代码 AAPL，当前价格 $150，市盈率 25，近期上涨 5%%\"}"
echo.
echo.

echo 3. 测试智能路由
curl -X POST "%BASE_URL%/api/workflow/routing/query" -H "Content-Type: application/json" -d "{\"query\": \"如何优化 Java 代码性能？\"}"
echo.
echo.

echo ==========================================
echo 测试完成！
echo ==========================================
pause
