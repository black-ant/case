package com.example.common.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 审计日志自动配置
 * 只在有 AOP 和 Jackson 的环境下生效
 */
@Configuration
@EnableAspectJAutoProxy
@ConditionalOnClass({ObjectMapper.class, org.aspectj.lang.annotation.Aspect.class})
public class AuditAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper auditObjectMapper() {
        return new ObjectMapper();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public ApiAuditAspect apiAuditAspect(ObjectMapper objectMapper) {
        return new ApiAuditAspect(objectMapper);
    }
}
