package com.example.customerservice.controller;

import com.example.customerservice.dto.ChatRequest;
import com.example.customerservice.entity.Order;
import com.example.customerservice.function.*;
import com.example.customerservice.repository.OrderRepository;
import com.example.customerservice.service.CustomerServiceAgent;
import com.example.customerservice.service.KnowledgeBaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试控制器 - 展示项目所有功能
 */
@Slf4j
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TestController {

    private final CustomerServiceAgent customerServiceAgent;
    private final KnowledgeBaseService knowledgeBaseService;
    private final OrderRepository orderRepository;
    private final OrderQueryFunction orderQueryFunction;
    private final TrackingQueryFunction trackingQueryFunction;
    private final TicketCreateFunction ticketCreateFunction;

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "UP");
        result.put("service", "AI Customer Service");
        result.put("timestamp", System.currentTimeMillis());
        return result;
    }

    /**
     * 测试 1：基础对话
     */
    @GetMapping("/1-basic-chat")
    public Map<String, Object> testBasicChat() {
        log.info("=== 测试 1：基础对话 ===");

        Map<String, Object> result = new HashMap<>();
        result.put("test", "基础对话");
        result.put("description", "测试 AI 的基本对话能力");

        try {
            // 创建会话
            String sessionId = customerServiceAgent.createSession("TEST001", "测试用户");
            result.put("sessionId", sessionId);

            // 发送消息
            String response = customerServiceAgent.chat(sessionId, "TEST001", "你好，请介绍一下你自己");
            result.put("userMessage", "你好，请介绍一下你自己");
            result.put("aiResponse", response);
            result.put("status", "SUCCESS");

        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("error", e.getMessage());
            log.error("基础对话测试失败", e);
        }

        return result;
    }

    /**
     * 测试 2：知识库检索 (RAG)
     */
    @GetMapping("/2-knowledge-base")
    public Map<String, Object> testKnowledgeBase() {
        log.info("=== 测试 2：知识库检索 (RAG) ===");

        Map<String, Object> result = new HashMap<>();
        result.put("test", "知识库检索");
        result.put("description", "测试 RAG 功能，从知识库检索相关信息");

        try {
            // 测试知识库搜索
            String query = "退货";
            List<Document> docs = knowledgeBaseService.searchKnowledge(query, 3);

            result.put("query", query);
            result.put("foundDocuments", docs.size());
            result.put("documents", docs.stream()
                    .map(Document::getContent)
                    .toList());

            // 创建会话并测试知识库问答
            String sessionId = customerServiceAgent.createSession("TEST002", "测试用户");
            String response = customerServiceAgent.chat(sessionId, "TEST002", "退货政策是什么？");

            result.put("userMessage", "退货政策是什么？");
            result.put("aiResponse", response);
            result.put("status", "SUCCESS");

        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("error", e.getMessage());
            log.error("知识库检索测试失败", e);
        }

        return result;
    }

    /**
     * 测试 3：Function Calling - 订单查询
     */
    @GetMapping("/3-function-order-query")
    public Map<String, Object> testOrderQuery() {
        log.info("=== 测试 3：Function Calling - 订单查询 ===");

        Map<String, Object> result = new HashMap<>();
        result.put("test", "订单查询");
        result.put("description", "测试 Function Calling 功能，AI 自动调用订单查询工具");

        try {
            // 直接测试函数
            OrderQueryFunction.Request request = new OrderQueryFunction.Request("ORD20251019001", null);
            OrderQueryFunction.Response functionResponse = orderQueryFunction.apply(request);

            result.put("directFunctionCall", Map.of(
                    "request", "查询订单号 ORD20251019001",
                    "response", functionResponse));

            // 通过 AI 测试
            String sessionId = customerServiceAgent.createSession("TEST003", "测试用户");
            String aiResponse = customerServiceAgent.chat(sessionId, "TEST003", "查询订单号 ORD20251019001");

            result.put("userMessage", "查询订单号 ORD20251019001");
            result.put("aiResponse", aiResponse);
            result.put("status", "SUCCESS");

        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("error", e.getMessage());
            log.error("订单查询测试失败", e);
        }

        return result;
    }

    /**
     * 测试 4：Function Calling - 物流追踪
     */
    @GetMapping("/4-function-tracking")
    public Map<String, Object> testTracking() {
        log.info("=== 测试 4：Function Calling - 物流追踪 ===");

        Map<String, Object> result = new HashMap<>();
        result.put("test", "物流追踪");
        result.put("description", "测试物流追踪功能");

        try {
            // 直接测试函数
            TrackingQueryFunction.Request request = new TrackingQueryFunction.Request("ORD20251019001");
            TrackingQueryFunction.Response functionResponse = trackingQueryFunction.apply(request);

            result.put("directFunctionCall", Map.of(
                    "request", "查询订单 ORD20251019001 的物流",
                    "response", functionResponse));

            // 通过 AI 测试
            String sessionId = customerServiceAgent.createSession("TEST004", "测试用户");
            String aiResponse = customerServiceAgent.chat(sessionId, "TEST004", "查询订单 ORD20251019001 的物流信息");

            result.put("userMessage", "查询订单 ORD20251019001 的物流信息");
            result.put("aiResponse", aiResponse);
            result.put("status", "SUCCESS");

        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("error", e.getMessage());
            log.error("物流追踪测试失败", e);
        }

        return result;
    }

    /**
     * 测试 5：Function Calling - 创建工单
     */
    @GetMapping("/5-function-create-ticket")
    public Map<String, Object> testCreateTicket() {
        log.info("=== 测试 5：Function Calling - 创建工单 ===");

        Map<String, Object> result = new HashMap<>();
        result.put("test", "创建工单");
        result.put("description", "测试工单创建功能");

        try {
            // 直接测试函数
            TicketCreateFunction.Request request = new TicketCreateFunction.Request(
                    "TEST005",
                    "测试用户",
                    "测试工单",
                    "这是一个自动化测试创建的工单",
                    "MEDIUM");
            TicketCreateFunction.Response functionResponse = ticketCreateFunction.apply(request);

            result.put("directFunctionCall", Map.of(
                    "request", request,
                    "response", functionResponse));

            // 通过 AI 测试
            String sessionId = customerServiceAgent.createSession("TEST005", "测试用户");
            String aiResponse = customerServiceAgent.chat(sessionId, "TEST005",
                    "我收到的商品有质量问题，屏幕有划痕，请帮我创建一个工单");

            result.put("userMessage", "我收到的商品有质量问题，屏幕有划痕，请帮我创建一个工单");
            result.put("aiResponse", aiResponse);
            result.put("status", "SUCCESS");

        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("error", e.getMessage());
            log.error("创建工单测试失败", e);
        }

        return result;
    }

    /**
     * 测试 6：多轮对话
     */
    @GetMapping("/6-multi-turn-conversation")
    public Map<String, Object> testMultiTurnConversation() {
        log.info("=== 测试 6：多轮对话 ===");

        Map<String, Object> result = new HashMap<>();
        result.put("test", "多轮对话");
        result.put("description", "测试上下文记忆和多轮对话能力");

        try {
            String sessionId = customerServiceAgent.createSession("TEST006", "测试用户");

            // 第一轮
            String response1 = customerServiceAgent.chat(sessionId, "TEST006", "我想查询订单");

            // 第二轮（依赖上下文）
            String response2 = customerServiceAgent.chat(sessionId, "TEST006", "订单号是 ORD20251019001");

            // 第三轮（继续上下文）
            String response3 = customerServiceAgent.chat(sessionId, "TEST006", "这个订单的物流信息呢？");

            result.put("conversation", List.of(
                    Map.of("turn", 1, "user", "我想查询订单", "ai", response1),
                    Map.of("turn", 2, "user", "订单号是 ORD20251019001", "ai", response2),
                    Map.of("turn", 3, "user", "这个订单的物流信息呢？", "ai", response3)));
            result.put("status", "SUCCESS");

        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("error", e.getMessage());
            log.error("多轮对话测试失败", e);
        }

        return result;
    }

    /**
     * 测试 7：数据库查询
     */
    @GetMapping("/7-database-query")
    public Map<String, Object> testDatabaseQuery() {
        log.info("=== 测试 7：数据库查询 ===");

        Map<String, Object> result = new HashMap<>();
        result.put("test", "数据库查询");
        result.put("description", "测试数据持久化和查询功能");

        try {
            // 查询所有订单
            List<Order> orders = orderRepository.findAll();

            result.put("totalOrders", orders.size());
            result.put("orders", orders.stream()
                    .map(order -> Map.of(
                            "orderNumber", order.getOrderNumber(),
                            "customerName", order.getCustomerName(),
                            "productName", order.getProductName(),
                            "status", order.getStatus().name()))
                    .toList());
            result.put("status", "SUCCESS");

        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("error", e.getMessage());
            log.error("数据库查询测试失败", e);
        }

        return result;
    }

    /**
     * 运行所有测试
     */
    @GetMapping("/run-all")
    public Map<String, Object> runAllTests() {
        log.info("=== 运行所有测试 ===");

        Map<String, Object> result = new HashMap<>();
        result.put("title", "AI 智能客服系统 - 完整功能测试");
        result.put("timestamp", System.currentTimeMillis());

        Map<String, Object> tests = new HashMap<>();

        // 运行所有测试
        tests.put("test1_basicChat", testBasicChat());
        tests.put("test2_knowledgeBase", testKnowledgeBase());
        tests.put("test3_orderQuery", testOrderQuery());
        tests.put("test4_tracking", testTracking());
        tests.put("test5_createTicket", testCreateTicket());
        tests.put("test6_multiTurn", testMultiTurnConversation());
        tests.put("test7_database", testDatabaseQuery());

        result.put("tests", tests);

        // 统计结果
        long successCount = tests.values().stream()
                .filter(test -> test instanceof Map)
                .map(test -> (Map<?, ?>) test)
                .filter(test -> "SUCCESS".equals(test.get("status")))
                .count();

        result.put("summary", Map.of(
                "total", tests.size(),
                "success", successCount,
                "failed", tests.size() - successCount));

        return result;
    }

    /**
     * 获取系统信息
     */
    @GetMapping("/system-info")
    public Map<String, Object> getSystemInfo() {
        Map<String, Object> info = new HashMap<>();

        info.put("projectName", "AI 智能客服系统");
        info.put("version", "1.0.0");
        info.put("features", List.of(
                "RAG（检索增强生成）",
                "Function Calling（工具调用）",
                "Streaming（流式响应）",
                "Prompt Engineering（提示工程）",
                "Agent Workflow（智能工作流）",
                "会话管理",
                "多轮对话"));

        info.put("apis", Map.of(
                "chat", Map.of(
                        "provider", "DeepSeek",
                        "model", "gemini-2.5-pro",
                        "url", "https://992236.xyz"),
                "embeddings", Map.of(
                        "provider", "SiliconFlow",
                        "model", "BAAI/bge-large-zh-v1.5",
                        "url", "https://api.siliconflow.cn/v1")));

        info.put("database", Map.of(
                "type", "H2",
                "mode", "memory",
                "tables", List.of("chat_sessions", "chat_messages", "orders", "customer_tickets")));

        info.put("endpoints", Map.of(
                "web", "http://localhost:8080",
                "api", "http://localhost:8080/api/customer-service",
                "test", "http://localhost:8080/api/test",
                "h2Console", "http://localhost:8080/h2-console"));

        return info;
    }
}
