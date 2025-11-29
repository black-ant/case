package com.example.functioncalling.controller;

import com.example.functioncalling.service.FunctionCallingService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Function Calling 演示控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/function")
@RequiredArgsConstructor
public class FunctionCallingController {
    
    private final FunctionCallingService functionCallingService;
    
    @Data
    public static class FunctionRequest {
        private String message;
        private String functionName;
        private String context;
    }
    
    /**
     * 1. 单个函数调用
     */
    @PostMapping("/single")
    public String singleFunction(@RequestBody FunctionRequest request) {
        return functionCallingService.callSingleFunction(
                request.getMessage(),
                request.getFunctionName()
        );
    }
    
    /**
     * 2. 多函数调用（AI 自动选择）
     */
    @PostMapping("/multiple")
    public String multipleFunctions(@RequestBody FunctionRequest request) {
        return functionCallingService.callMultipleFunctions(request.getMessage());
    }
    
    /**
     * 3. 自动函数调用
     */
    @PostMapping("/auto")
    public String autoFunctions(@RequestBody FunctionRequest request) {
        return functionCallingService.callWithAutoFunctions(request.getMessage());
    }
    
    /**
     * 4. 强制函数调用
     */
    @PostMapping("/force")
    public String forceFunction(@RequestBody FunctionRequest request) {
        return functionCallingService.forceFunction(
                request.getMessage(),
                request.getFunctionName()
        );
    }
    
    /**
     * 5. 链式函数调用
     */
    @PostMapping("/chain")
    public String chainFunctions(@RequestBody FunctionRequest request) {
        return functionCallingService.chainFunctionCalls(request.getMessage());
    }
    
    /**
     * 6. 带上下文的函数调用
     */
    @PostMapping("/context")
    public String contextFunction(@RequestBody FunctionRequest request) {
        return functionCallingService.callWithContext(
                request.getMessage(),
                request.getContext()
        );
    }
    
    /**
     * 健康检查
     */
    @GetMapping("/health")
    public String health() {
        return "Function Calling Demo is running!";
    }
}
