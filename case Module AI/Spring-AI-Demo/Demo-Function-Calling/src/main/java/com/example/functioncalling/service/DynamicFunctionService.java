package com.example.functioncalling.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 动态函数注册服务
 * 支持运行时动态添加、删除和管理函数
 */
@Slf4j
@Service
public class DynamicFunctionService {
    
    private final ChatClient.Builder chatClientBuilder;
    
    // 存储动态注册的函数
    private final Map<String, FunctionCallback> dynamicFunctions = new ConcurrentHashMap<>();
    
    public DynamicFunctionService(ChatClient.Builder chatClientBuilder) {
        this.chatClientBuilder = chatClientBuilder;
    }
    
    /**
     * 1. 动态注册简单函数
     */
    public void registerSimpleFunction(String functionName, String description, 
                                       Function<Map<String, Object>, Object> function) {
        log.info("动态注册函数: {}", functionName);
        
        FunctionCallback callback = FunctionCallbackWrapper.builder(function)
                .withName(functionName)
                .withDescription(description)
                .build();
        
        dynamicFunctions.put(functionName, callback);
    }
    
    /**
     * 2. 动态注册带类型的函数
     */
    public <I, O> void registerTypedFunction(String functionName, String description,
                                             Class<I> inputType, Function<I, O> function) {
        log.info("动态注册类型化函数: {} (输入类型: {})", functionName, inputType.getSimpleName());
        
        FunctionCallback callback = FunctionCallbackWrapper.builder(function)
                .withName(functionName)
                .withDescription(description)
                .withInputType(inputType)
                .build();
        
        dynamicFunctions.put(functionName, callback);
    }
    
    /**
     * 3. 注销函数
     */
    public boolean unregisterFunction(String functionName) {
        log.info("注销函数: {}", functionName);
        return dynamicFunctions.remove(functionName) != null;
    }
    
    /**
     * 4. 获取所有已注册的函数名称
     */
    public Map<String, String> listRegisteredFunctions() {
        Map<String, String> functions = new ConcurrentHashMap<>();
        dynamicFunctions.forEach((name, callback) -> {
            functions.put(name, callback.getDescription());
        });
        return functions;
    }
    
    /**
     * 5. 检查函数是否已注册
     */
    public boolean isFunctionRegistered(String functionName) {
        return dynamicFunctions.containsKey(functionName);
    }
    
    /**
     * 6. 使用动态注册的函数进行对话
     */
    public String chatWithDynamicFunctions(String message, String... functionNames) {
        log.info("使用动态函数对话: message={}, functions={}", message, functionNames);
        
        ChatClient chatClient = chatClientBuilder.build();
        
        return chatClient.prompt()
                .user(message)
                .functions(functionNames)
                .call()
                .content();
    }
    
    /**
     * 7. 使用所有已注册的动态函数
     */
    public String chatWithAllDynamicFunctions(String message) {
        log.info("使用所有动态函数对话: message={}", message);
        
        String[] functionNames = dynamicFunctions.keySet().toArray(new String[0]);
        
        if (functionNames.length == 0) {
            return "没有可用的动态函数";
        }
        
        ChatClient chatClient = chatClientBuilder.build();
        
        return chatClient.prompt()
                .user(message)
                .functions(functionNames)
                .call()
                .content();
    }
    
    /**
     * 8. 清空所有动态函数
     */
    public void clearAllFunctions() {
        log.info("清空所有动态函数，当前数量: {}", dynamicFunctions.size());
        dynamicFunctions.clear();
    }
    
    /**
     * 9. 获取函数数量
     */
    public int getFunctionCount() {
        return dynamicFunctions.size();
    }
}
