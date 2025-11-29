@echo off
REM RAG 功能测试脚本 (Windows)

set BASE_URL=http://localhost:8080

echo ==========================================
echo RAG Demo 功能测试
echo ==========================================
echo.

echo 1. 摄取文本内容
curl -X POST "%BASE_URL%/api/documents/ingest/text" -H "Content-Type: application/json" -d "{\"title\": \"Spring AI 介绍\", \"text\": \"Spring AI 是一个用于构建 AI 应用的 Java 框架。它提供了统一的 API 来集成各种 LLM 提供商，包括 OpenAI、Azure OpenAI、Anthropic Claude 等。Spring AI 支持向量数据库、文档处理、RAG 架构等功能。\"}"
echo.
echo.

echo 2. 摄取更多文本
curl -X POST "%BASE_URL%/api/documents/ingest/text" -H "Content-Type: application/json" -d "{\"title\": \"向量数据库\", \"text\": \"向量数据库用于存储和检索高维向量。常见的向量数据库包括 PGVector、Pinecone、Weaviate、Milvus 等。向量数据库支持相似度搜索，可以快速找到与查询向量最相似的向量。\"}"
echo.
echo.

echo 3. 基础 RAG 查询
curl -X POST "%BASE_URL%/api/rag/query" -H "Content-Type: application/json" -d "{\"query\": \"什么是 Spring AI？\"}"
echo.
echo.

echo 4. 相似度搜索
curl -X POST "%BASE_URL%/api/rag/search" -H "Content-Type: application/json" -d "{\"query\": \"向量数据库\", \"topK\": 3}"
echo.
echo.

echo 5. 带引用的查询
curl -X POST "%BASE_URL%/api/rag/query/with-citations" -H "Content-Type: application/json" -d "{\"query\": \"Spring AI 支持哪些 LLM？\"}"
echo.
echo.

echo ==========================================
echo 测试完成！
echo ==========================================
pause
