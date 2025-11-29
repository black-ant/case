package com.example.common.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * API 审计日志切面
 * 自动拦截所有 Controller 方法，记录请求和响应
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ApiAuditAspect {
    
    private final ObjectMapper objectMapper;
    
    /**
     * 切入点：带有 @ApiAuditLog 注解的类或方法
     */
    @Pointcut("@within(com.example.common.audit.ApiAuditLog) || " +
              "@annotation(com.example.common.audit.ApiAuditLog)")
    public void auditLogAnnotated() {}
    
    /**
     * 环绕通知：记录审计日志
     */
    @Around("auditLogAnnotated()")
    public Object auditLog(ProceedingJoinPoint joinPoint) throws Throwable {
        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();
        
        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        
        // 获取方法信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        // 获取注解配置
        ApiAuditLog auditLog = method.getAnnotation(ApiAuditLog.class);
        if (auditLog == null) {
            auditLog = joinPoint.getTarget().getClass().getAnnotation(ApiAuditLog.class);
        }
        
        boolean logRequest = auditLog == null || auditLog.logRequest();
        boolean logResponse = auditLog == null || auditLog.logResponse();
        boolean logException = auditLog == null || auditLog.logException();
        String operation = auditLog != null ? auditLog.value() : "";
        
        // 记录请求信息
        if (logRequest) {
            logRequestInfo(requestId, request, className, methodName, operation, joinPoint.getArgs());
        }
        
        try {
            // 执行目标方法
            Object result = joinPoint.proceed();
            
            // 处理响应式返回类型
            if (result instanceof Mono) {
                return handleMonoResponse((Mono<?>) result, requestId, startTime, logResponse);
            } else if (result instanceof Flux) {
                return handleFluxResponse((Flux<?>) result, requestId, startTime, logResponse);
            } else {
                // 同步响应
                long duration = System.currentTimeMillis() - startTime;
                if (logResponse) {
                    logResponseInfo(requestId, result, duration, true, null);
                }
                return result;
            }
            
        } catch (Throwable throwable) {
            long duration = System.currentTimeMillis() - startTime;
            if (logException) {
                logResponseInfo(requestId, null, duration, false, throwable.getMessage());
            }
            throw throwable;
        }
    }
    
    /**
     * 记录请求信息
     */
    private void logRequestInfo(String requestId, HttpServletRequest request, 
                                String className, String methodName, String operation,
                                Object[] args) {
        try {
            String httpMethod = request != null ? request.getMethod() : "UNKNOWN";
            String path = request != null ? request.getRequestURI() : "UNKNOWN";
            
            log.info("╔═══════════════════════════════════════════════════════════════");
            log.info("║ [AUDIT] Request ID: {}", requestId);
            log.info("║ [AUDIT] Operation: {} {}", httpMethod, path);
            log.info("║ [AUDIT] Controller: {}.{}", className, methodName);
            if (operation != null && !operation.isEmpty()) {
                log.info("║ [AUDIT] Description: {}", operation);
            }
            
            // 记录请求参数
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    if (arg != null && !isIgnoredType(arg)) {
                        String argJson = toJsonString(arg);
                        log.info("║ [AUDIT] Request Body [{}]: {}", i, argJson);
                    }
                }
            }
            log.info("╚═══════════════════════════════════════════════════════════════");
            
        } catch (Exception e) {
            log.warn("Failed to log request info: {}", e.getMessage());
        }
    }
    
    /**
     * 记录响应信息
     */
    private void logResponseInfo(String requestId, Object response, long duration, 
                                 boolean success, String errorMessage) {
        try {
            log.info("╔═══════════════════════════════════════════════════════════════");
            log.info("║ [AUDIT] Request ID: {}", requestId);
            log.info("║ [AUDIT] Duration: {} ms", duration);
            log.info("║ [AUDIT] Status: {}", success ? "SUCCESS" : "FAILED");
            
            if (success && response != null) {
                String responseJson = toJsonString(response);
                // 如果响应太长，截断显示
                if (responseJson.length() > 2000) {
                    responseJson = responseJson.substring(0, 2000) + "... (truncated)";
                }
                log.info("║ [AUDIT] Response Body: {}", responseJson);
            }
            
            if (!success && errorMessage != null) {
                log.error("║ [AUDIT] Error: {}", errorMessage);
            }
            
            log.info("╚═══════════════════════════════════════════════════════════════");
            
        } catch (Exception e) {
            log.warn("Failed to log response info: {}", e.getMessage());
        }
    }
    
    /**
     * 处理 Mono 响应
     */
    private Mono<?> handleMonoResponse(Mono<?> mono, String requestId, long startTime, boolean logResponse) {
        return mono
                .doOnSuccess(result -> {
                    if (logResponse) {
                        long duration = System.currentTimeMillis() - startTime;
                        logResponseInfo(requestId, result, duration, true, null);
                    }
                })
                .doOnError(error -> {
                    long duration = System.currentTimeMillis() - startTime;
                    logResponseInfo(requestId, null, duration, false, error.getMessage());
                });
    }
    
    /**
     * 处理 Flux 响应（流式）
     */
    private Flux<?> handleFluxResponse(Flux<?> flux, String requestId, long startTime, boolean logResponse) {
        return flux
                .doOnSubscribe(subscription -> {
                    if (logResponse) {
                        log.info("[AUDIT] Request ID: {} - Stream started", requestId);
                    }
                })
                .doOnComplete(() -> {
                    if (logResponse) {
                        long duration = System.currentTimeMillis() - startTime;
                        log.info("[AUDIT] Request ID: {} - Stream completed in {} ms", requestId, duration);
                    }
                })
                .doOnError(error -> {
                    long duration = System.currentTimeMillis() - startTime;
                    log.error("[AUDIT] Request ID: {} - Stream error after {} ms: {}", 
                             requestId, duration, error.getMessage());
                });
    }
    
    /**
     * 转换为 JSON 字符串
     */
    private String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return obj.toString();
        }
    }
    
    /**
     * 判断是否为需要忽略的类型
     */
    private boolean isIgnoredType(Object obj) {
        return obj instanceof HttpServletRequest ||
               obj instanceof jakarta.servlet.http.HttpServletResponse ||
               obj instanceof org.springframework.ui.Model ||
               obj instanceof org.springframework.validation.BindingResult;
    }
}
