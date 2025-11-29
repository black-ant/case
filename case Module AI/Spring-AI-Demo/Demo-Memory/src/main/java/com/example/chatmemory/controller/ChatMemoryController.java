package com.example.chatmemory.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.time.Duration;
import java.lang.reflect.Method;

@RestController
@RequestMapping("/chat-memory")
public class ChatMemoryController {

    private final ChatMemory chatMemory;
    private final ChatClient chatClient;

    public ChatMemoryController(ChatMemory chatMemory, ChatClient chatClient) {
        this.chatMemory = chatMemory;
        this.chatClient = chatClient;
    }

    // Exactly like your snippet: get history, append user, call, append assistant
    @PostMapping(value = "/chat", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String chat(@RequestParam String sessionId, @RequestBody String msg) {
        List<Message> history = new ArrayList<>(getAllHistory(chatMemory, sessionId));

        history.add(new UserMessage(msg));

        // Gateway returns text/event-stream; switch to streaming API and aggregate tokens
        String reply = chatClient
                .prompt(new Prompt(history))
                .stream()
                .content()
                .collectList()
                .map(list -> String.join("", list))
                .block(Duration.ofSeconds(60));
        chatMemory.add(sessionId, new AssistantMessage(reply));
        return reply;
    }

    // Helper to inspect current history
    @GetMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> history(@RequestParam String sessionId) {
        List<Message> history = getAllHistory(chatMemory, sessionId);
        List<Map<String, Object>> items = new ArrayList<>();
        for (Message m : history) {
            Map<String, Object> it = new HashMap<>();
            it.put("type", m.getMessageType().name());
            it.put("content", m.getContent());
            items.add(it);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("sessionId", sessionId);
        res.put("history", items);
        return res;
    }

    // Adapter to support both ChatMemory#get(String,int) and legacy ChatMemory#get(String)
    @SuppressWarnings("unchecked")
    private List<Message> getAllHistory(ChatMemory memory, String sessionId) {
        try {
            Method m2 = memory.getClass().getMethod("get", String.class, int.class);
            Object res = m2.invoke(memory, sessionId, Integer.MAX_VALUE);
            return (List<Message>) Optional.ofNullable(res).orElseGet(ArrayList::new);
        } catch (NoSuchMethodException ignored) {
            try {
                Method m1 = memory.getClass().getMethod("get", String.class);
                Object res = m1.invoke(memory, sessionId);
                return (List<Message>) Optional.ofNullable(res).orElseGet(ArrayList::new);
            } catch (Exception e) {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
