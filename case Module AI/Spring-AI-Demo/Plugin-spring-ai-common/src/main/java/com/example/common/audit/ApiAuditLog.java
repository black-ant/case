package com.example.common.audit;

import java.lang.annotation.*;

/**
 * API 审计日志注解
 * 用于标记需要记录审计日志的 Controller 方法
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiAuditLog {
    
    /**
     * 操作描述
     */
    String value() default "";
    
    /**
     * 是否记录请求参数
     */
    boolean logRequest() default true;
    
    /**
     * 是否记录响应结果
     */
    boolean logResponse() default true;
    
    /**
     * 是否记录异常信息
     */
    boolean logException() default true;
}
