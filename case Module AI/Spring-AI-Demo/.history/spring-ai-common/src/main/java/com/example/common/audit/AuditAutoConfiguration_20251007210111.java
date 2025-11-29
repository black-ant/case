package com.example.common.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 审计日志自动配置
 */
@Configuration
@EnableAspectJAutoProxy
public class AuditAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    
    @Bean
    public ApiAuditAspect apiAuditAspect(ObjectMapper objectMapper) {
        return new ApiAuditAspect(objectMapper);
    }
}
