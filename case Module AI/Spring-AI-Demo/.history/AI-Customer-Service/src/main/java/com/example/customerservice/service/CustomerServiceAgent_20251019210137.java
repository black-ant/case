package com.example.customerservice.service;

import com.example.customerservice.entity.ChatMessage;
import com.example.customerservice.entity.ChatSession;
import com.example.customerservice.function.*;
import com.example.customerservice.repository.ChatMessageRepository;
import com.example.customerservice.repository.ChatSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * AI 客服代理 - 核心服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceAgent {
    
    private final ChatClient.Builder chatClientBuilder;
    private final KnowledgeBaseService knowledgeBaseService;
    private final ChatMessageRepository messageRepository;
    private final ChatSessionRepository sessionRepository;
    
    private final OrderQueryFunction orderQueryFunction;
    private final TrackingQueryFunction trackingQueryFunction;
    private final TicketCreateFunction ticketCreateFunction;
    private final TicketQueryFunction ticketQueryFunction;
    
    private static final String SYSTEM_PROMPT = """
        你是一位专业、友好的AI客服助手。你的职责是：
        
        1. 热情、耐心地回答客户问题
        2. 使用礼貌、专业的语言
        3. 优先使用知识库中的信息回答问题
        4. 当需要查询订单、物流或创建工单时，使用相应的工具
        5. 如果无法解决问题，引导客户创建工单或联系人工客服
        6. 保持回答简洁明了，避免冗长
        
        注意事项：
        - 始终保持礼貌和专业
        - 对客户的问题表示理解和关心
        - 提供具体、可操作的解决方案
        - 如果不确定，诚实告知并提供替代方案
        """;
    
    /**
     * 创建新会话
     */
    public String createSession(String customerId, String customerName) {
        String sessionId = UUID.randomUUID().toString();
        
        ChatSession session = new ChatSession();
        session.setSessionId(sessionId);
        session.setCustomerId(customerId);
        session.setCustomerName(customerName);
        session.setStartTime(LocalDateTime.now());
        sessionRepository.save(session);
        
        log.info("创建新会话: sessionId={}, customerId={}", sessionId, customerId);
        return sessionId;
    }
    
    /**
     * 处理客户消息（非流式）
     */
    public String chat(String sessionId, String customerId, String userMessage) {
        // 保存用户消息
        saveMessage(sessionId, ChatMessage.MessageRole.USER, userMessage, null);
        
        // 获取历史消息
        List<Message> messages = buildMessageHistory(sessionId, userMessage);
        
        // 调用 AI
        ChatClient chatClient = chatClientBuilder
            .defaultFunctions(
                "orderQueryFunction",
                "trackingQueryFunction",
                "ticketCreateFunction",
                "ticketQueryFunction"
            )
            .build();
        
        ChatResponse response = chatClient.prompt()
            .messages(messages)
            .call()
            .chatResponse();
        
        String assistantMessage = response.getResult().getOutput().getContent();
        
        // 保存助手消息
        saveMessage(sessionId, ChatMessage.MessageRole.ASSISTANT, assistantMessage, null);
        
        // 更新会话
        updateSession(sessionId);
        
        return assistantMessage;
    }
    
    /**
     * 处理客户消息（流式）
     */
    public Flux<String> chatStream(String sessionId, String customerId, String userMessage) {
        // 保存用户消息
        saveMessage(sessionId, ChatMessage.MessageRole.USER, userMessage, null);
        
        // 获取历史消息
        List<Message> messages = buildMessageHistory(sessionId, userMessage);
        
        // 调用 AI（流式）
        ChatClient chatClient = chatClientBuilder
            .defaultFunctions(
                orderQueryFunction,
                trackingQueryFunction,
                ticketCreateFunction,
                ticketQueryFunction
            )
            .build();
        
        StringBuilder fullResponse = new StringBuilder();
        
        return chatClient.prompt()
            .messages(messages)
            .stream()
            .content()
            .doOnNext(fullResponse::append)
            .doOnComplete(() -> {
                // 流式响应完成后保存完整消息
                saveMessage(sessionId, ChatMessage.MessageRole.ASSISTANT, fullResponse.toString(), null);
                updateSession(sessionId);
            });
    }
    
    /**
     * 构建消息历史
     */
    private List<Message> buildMessageHistory(String sessionId, String currentMessage) {
        List<Message> messages = new ArrayList<>();
        
        // 系统提示词
        messages.add(new SystemMessage(SYSTEM_PROMPT));
        
        // 添加知识库上下文
        String knowledgeContext = knowledgeBaseService.getKnowledgeContext(currentMessage);
        if (!knowledgeContext.isEmpty()) {
            messages.add(new SystemMessage("以下是可能相关的知识库信息，请参考使用：\n" + knowledgeContext));
        }
        
        // 添加历史消息（最近10条）
        List<ChatMessage> history = messageRepository.findBySessionIdOrderByTimestampAsc(sessionId);
        int startIndex = Math.max(0, history.size() - 10);
        for (int i = startIndex; i < history.size(); i++) {
            ChatMessage msg = history.get(i);
            if (msg.getRole() == ChatMessage.MessageRole.USER) {
                messages.add(new UserMessage(msg.getContent()));
            } else if (msg.getRole() == ChatMessage.MessageRole.ASSISTANT) {
                messages.add(new AssistantMessage(msg.getContent()));
            }
        }
        
        // 添加当前消息
        messages.add(new UserMessage(currentMessage));
        
        return messages;
    }
    
    /**
     * 保存消息
     */
    private void saveMessage(String sessionId, ChatMessage.MessageRole role, String content, String functionCalled) {
        ChatMessage message = new ChatMessage();
        message.setSessionId(sessionId);
        message.setRole(role);
        message.setContent(content);
        message.setFunctionCalled(functionCalled);
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);
    }
    
    /**
     * 更新会话
     */
    private void updateSession(String sessionId) {
        sessionRepository.findBySessionId(sessionId).ifPresent(session -> {
            session.setMessageCount(session.getMessageCount() + 2); // user + assistant
            sessionRepository.save(session);
        });
    }
    
    /**
     * 结束会话
     */
    public void endSession(String sessionId) {
        sessionRepository.findBySessionId(sessionId).ifPresent(session -> {
            session.setEndTime(LocalDateTime.now());
            session.setStatus(ChatSession.SessionStatus.ENDED);
            sessionRepository.save(session);
            log.info("会话结束: sessionId={}", sessionId);
        });
    }
}
