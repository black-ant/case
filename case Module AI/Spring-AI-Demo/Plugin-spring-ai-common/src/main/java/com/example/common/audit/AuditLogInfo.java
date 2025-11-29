package com.example.common.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 审计日志信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogInfo {
    
    /**
     * 请求ID
     */
    private String requestId;
    
    /**
     * 操作描述
     */
    private String operation;
    
    /**
     * 请求方法
     */
    private String method;
    
    /**
     * 请求路径
     */
    private String path;
    
    /**
     * 请求参数
     */
    private Object requestBody;
    
    /**
     * 响应结果
     */
    private Object responseBody;
    
    /**
     * 执行时长（毫秒）
     */
    private Long duration;
    
    /**
     * 是否成功
     */
    private Boolean success;
    
    /**
     * 异常信息
     */
    private String errorMessage;
    
    /**
     * 请求时间
     */
    private Long timestamp;
}
