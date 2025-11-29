package com.example.prompt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 对话记忆服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConversationMemoryService {
    
    private final ChatClient.Builder chatClientBuilder;
    private final Map<String, ChatMemory> memoryStore = new ConcurrentHashMap<>();
    
    /**
     * 1. 带记忆的对话
     */
    public Map<String, Object> chatWithMemory(String sessionId, String message) {
        log.info("带记忆的对话: sessionId={}, message={}", sessionId, message);
        
        ChatMemory memory = getOrCreateMemory(sessionId);
        
        ChatClient chatClient = chatClientBuilder.build();
        
        String response = chatClient.prompt()
                .user(message)
                .advisors(new MessageChatMemoryAdvisor(memory))
                .call()
                .content();
        
        return Map.of(
                "sessionId", sessionId,
                "message", message,
                "response", response,
                "messageCount", memory.get(sessionId, 100).size()
        );
    }
    
    /**
     * 2. 获取对话历史
     */
    public Map<String, Object> getConversationHistory(String sessionId) {
        log.info("获取对话历史: sessionId={}", sessionId);
        
        ChatMemory memory = memoryStore.get(sessionId);
        
        if (memory == null) {
            return Map.of(
                    "sessionId", sessionId,
                    "messages", List.of(),
                    "count", 0
            );
        }
        
        List<Message> messages = memory.get(sessionId, 100);
        
        List<Map<String, String>> formattedMessages = messages.stream()
                .map(msg -> Map.of(
                        "role", msg.getMessageType().getValue(),
                        "content", msg.getContent()
                ))
                .collect(Collectors.toList());
        
        return Map.of(
                "sessionId", sessionId,
                "messages", formattedMessages,
                "count", messages.size()
        );
    }
    
    /**
     * 3. 清除对话记忆
     */
    public Map<String, Object> clearMemory(String sessionId) {
        log.info("清除对话记忆: sessionId={}", sessionId);
        
        ChatMemory memory = memoryStore.get(sessionId);
        
        if (memory != null) {
            memory.clear(sessionId);
            memoryStore.remove(sessionId);
        }
        
        return Map.of(
                "sessionId", sessionId,
                "status", "cleared"
        );
    }
    
    /**
     * 4. 压缩对话记忆
     */
    public Map<String, Object> compressMemory(String sessionId) {
        log.info("压缩对话记忆: sessionId={}", sessionId);
        
        ChatMemory memory = memoryStore.get(sessionId);
        
        if (memory == null) {
            return Map.of(
                    "sessionId", sessionId,
                    "status", "no memory found"
            );
        }
        
        List<Message> messages = memory.get(sessionId, 100);
        
        if (messages.size() <= 10) {
            return Map.of(
                    "sessionId", sessionId,
                    "status", "no compression needed",
                    "messageCount", messages.size()
            );
        }
        
        // 压缩旧消息为摘要
        List<Message> oldMessages = messages.subList(0, messages.size() - 5);
        String messagesToCompress = oldMessages.stream()
                .map(msg -> msg.getMessageType().getValue() + ": " + msg.getContent())
                .collect(Collectors.joining("\n"));
        
        ChatClient chatClient = chatClientBuilder.build();
        
        String summary = chatClient.prompt()
                .system("请简洁地总结以下对话历史，保留关键信息")
                .user(messagesToCompress)
                .call()
                .content();
        
        // 创建新的记忆
        ChatMemory newMemory = new InMemoryChatMemory();
        
        // 添加摘要
        newMemory.add(sessionId, new SystemMessage("对话历史摘要：" + summary));
        
        // 保留最近的消息
        List<Message> recentMessages = messages.subList(messages.size() - 5, messages.size());
        recentMessages.forEach(msg -> newMemory.add(sessionId, msg));
        
        // 替换旧记忆
        memoryStore.put(sessionId, newMemory);
        
        return Map.of(
                "sessionId", sessionId,
                "status", "compressed",
                "originalCount", messages.size(),
                "compressedCount", newMemory.get(sessionId, 100).size(),
                "summary", summary
        );
    }
    
    /**
     * 5. 多会话管理
     */
    public Map<String, Object> listSessions() {
        log.info("列出所有会话");
        
        List<Map<String, Object>> sessions = memoryStore.entrySet().stream()
                .map(entry -> {
                    String sessionId = entry.getKey();
                    ChatMemory memory = entry.getValue();
                    List<Message> messages = memory.get(sessionId, 100);
                    
                    Map<String, Object> sessionInfo = new java.util.HashMap<>();
                    sessionInfo.put("sessionId", sessionId);
                    sessionInfo.put("messageCount", messages.size());
                    sessionInfo.put("lastMessage", messages.isEmpty() ? "" : 
                            messages.get(messages.size() - 1).getContent());
                    return sessionInfo;
                })
                .collect(Collectors.toList());
        
        return Map.of(
                "totalSessions", sessions.size(),
                "sessions", sessions
        );
    }
    
    /**
     * 获取或创建记忆
     */
    private ChatMemory getOrCreateMemory(String sessionId) {
        return memoryStore.computeIfAbsent(sessionId, id -> new InMemoryChatMemory());
    }
}
