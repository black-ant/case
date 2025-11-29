package com.example.functioncalling.controller;

import com.example.functioncalling.service.DynamicFunctionService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态函数注册控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/dynamic")
@RequiredArgsConstructor
public class DynamicFunctionController {
    
    private final DynamicFunctionService dynamicFunctionService;
    
    @Data
    public static class RegisterFunctionRequest {
        private String functionName;
        private String description;
        private String functionType;  // simple, greeting, math, etc.
    }
    
    @Data
    public static class ChatRequest {
        private String message;
        private String[] functionNames;
    }
    
    /**
     * 1. 注册预定义的示例函数
     */
    @PostMapping("/register/example")
    public Map<String, Object> registerExampleFunction(@RequestBody RegisterFunctionRequest request) {
        log.info("注册示例函数: {}", request.getFunctionName());
        
        switch (request.getFunctionType().toLowerCase()) {
            case "greeting":
                // 注册问候函数
                dynamicFunctionService.registerSimpleFunction(
                        request.getFunctionName(),
                        request.getDescription(),
                        params -> {
                            String name = (String) params.getOrDefault("name", "朋友");
                            return String.format("你好，%s！很高兴见到你！", name);
                        }
                );
                break;
                
            case "math":
                // 注册数学函数
                dynamicFunctionService.registerSimpleFunction(
                        request.getFunctionName(),
                        request.getDescription(),
                        params -> {
                            double x = ((Number) params.get("x")).doubleValue();
                            double y = ((Number) params.get("y")).doubleValue();
                            return x * x + y * y;
                        }
                );
                break;
                
            case "time":
                // 注册时间函数
                dynamicFunctionService.registerSimpleFunction(
                        request.getFunctionName(),
                        request.getDescription(),
                        params -> {
                            String timezone = (String) params.getOrDefault("timezone", "Asia/Shanghai");
                            return String.format("当前时间（%s）：%s", 
                                    timezone, 
                                    java.time.LocalDateTime.now());
                        }
                );
                break;
                
            case "random":
                // 注册随机数函数
                dynamicFunctionService.registerSimpleFunction(
                        request.getFunctionName(),
                        request.getDescription(),
                        params -> {
                            int min = ((Number) params.getOrDefault("min", 1)).intValue();
                            int max = ((Number) params.getOrDefault("max", 100)).intValue();
                            return min + (int)(Math.random() * (max - min + 1));
                        }
                );
                break;
                
            case "reverse":
                // 注册字符串反转函数
                dynamicFunctionService.registerSimpleFunction(
                        request.getFunctionName(),
                        request.getDescription(),
                        params -> {
                            String text = (String) params.get("text");
                            return new StringBuilder(text).reverse().toString();
                        }
                );
                break;
                
            default:
                return Map.of(
                        "success", false,
                        "message", "不支持的函数类型: " + request.getFunctionType()
                );
        }
        
        return Map.of(
                "success", true,
                "message", "函数注册成功",
                "functionName", request.getFunctionName(),
                "functionType", request.getFunctionType()
        );
    }
    
    /**
     * 2. 注册自定义 Lambda 函数
     */
    @PostMapping("/register/lambda")
    public Map<String, Object> registerLambdaFunction(@RequestBody RegisterFunctionRequest request) {
        log.info("注册 Lambda 函数: {}", request.getFunctionName());
        
        // 示例：注册一个简单的 Lambda 函数
        dynamicFunctionService.registerSimpleFunction(
                request.getFunctionName(),
                request.getDescription(),
                params -> {
                    // 这里可以执行任意逻辑
                    return "Lambda 函数执行结果: " + params.toString();
                }
        );
        
        return Map.of(
                "success", true,
                "message", "Lambda 函数注册成功",
                "functionName", request.getFunctionName()
        );
    }
    
    /**
     * 3. 注销函数
     */
    @DeleteMapping("/unregister/{functionName}")
    public Map<String, Object> unregisterFunction(@PathVariable String functionName) {
        log.info("注销函数: {}", functionName);
        
        boolean success = dynamicFunctionService.unregisterFunction(functionName);
        
        return Map.of(
                "success", success,
                "message", success ? "函数注销成功" : "函数不存在",
                "functionName", functionName
        );
    }
    
    /**
     * 4. 列出所有已注册的函数
     */
    @GetMapping("/list")
    public Map<String, Object> listFunctions() {
        Map<String, String> functions = dynamicFunctionService.listRegisteredFunctions();
        
        return Map.of(
                "success", true,
                "count", functions.size(),
                "functions", functions
        );
    }
    
    /**
     * 5. 检查函数是否存在
     */
    @GetMapping("/check/{functionName}")
    public Map<String, Object> checkFunction(@PathVariable String functionName) {
        boolean exists = dynamicFunctionService.isFunctionRegistered(functionName);
        
        return Map.of(
                "success", true,
                "functionName", functionName,
                "exists", exists
        );
    }
    
    /**
     * 6. 使用指定的动态函数进行对话
     */
    @PostMapping("/chat")
    public Map<String, Object> chatWithDynamicFunctions(@RequestBody ChatRequest request) {
        log.info("使用动态函数对话: {}", request.getMessage());
        
        String response = dynamicFunctionService.chatWithDynamicFunctions(
                request.getMessage(),
                request.getFunctionNames()
        );
        
        return Map.of(
                "success", true,
                "message", request.getMessage(),
                "response", response,
                "usedFunctions", request.getFunctionNames()
        );
    }
    
    /**
     * 7. 使用所有动态函数进行对话
     */
    @PostMapping("/chat/all")
    public Map<String, Object> chatWithAllFunctions(@RequestBody ChatRequest request) {
        log.info("使用所有动态函数对话: {}", request.getMessage());
        
        String response = dynamicFunctionService.chatWithAllDynamicFunctions(request.getMessage());
        
        return Map.of(
                "success", true,
                "message", request.getMessage(),
                "response", response,
                "availableFunctions", dynamicFunctionService.listRegisteredFunctions().keySet()
        );
    }
    
    /**
     * 8. 清空所有动态函数
     */
    @DeleteMapping("/clear")
    public Map<String, Object> clearAllFunctions() {
        int count = dynamicFunctionService.getFunctionCount();
        dynamicFunctionService.clearAllFunctions();
        
        return Map.of(
                "success", true,
                "message", "所有动态函数已清空",
                "clearedCount", count
        );
    }
    
    /**
     * 9. 批量注册示例函数
     */
    @PostMapping("/register/batch")
    public Map<String, Object> registerBatchFunctions() {
        log.info("批量注册示例函数");
        
        Map<String, String> registered = new HashMap<>();
        
        // 注册问候函数
        dynamicFunctionService.registerSimpleFunction(
                "greetUser",
                "向用户问候，需要参数 name（用户名）",
                params -> String.format("你好，%s！", params.get("name"))
        );
        registered.put("greetUser", "问候函数");
        
        // 注册当前时间函数
        dynamicFunctionService.registerSimpleFunction(
                "getCurrentTime",
                "获取当前时间",
                params -> java.time.LocalDateTime.now().toString()
        );
        registered.put("getCurrentTime", "时间函数");
        
        // 注册随机数函数
        dynamicFunctionService.registerSimpleFunction(
                "getRandomNumber",
                "生成随机数，参数 min（最小值）和 max（最大值）",
                params -> {
                    int min = ((Number) params.getOrDefault("min", 1)).intValue();
                    int max = ((Number) params.getOrDefault("max", 100)).intValue();
                    return min + (int)(Math.random() * (max - min + 1));
                }
        );
        registered.put("getRandomNumber", "随机数函数");
        
        // 注册字符串长度函数
        dynamicFunctionService.registerSimpleFunction(
                "getStringLength",
                "获取字符串长度，参数 text（文本内容）",
                params -> ((String) params.get("text")).length()
        );
        registered.put("getStringLength", "字符串长度函数");
        
        // 注册大写转换函数
        dynamicFunctionService.registerSimpleFunction(
                "toUpperCase",
                "将文本转换为大写，参数 text（文本内容）",
                params -> ((String) params.get("text")).toUpperCase()
        );
        registered.put("toUpperCase", "大写转换函数");
        
        return Map.of(
                "success", true,
                "message", "批量注册完成",
                "count", registered.size(),
                "functions", registered
        );
    }
}
