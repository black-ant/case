package com.gang.cloud.template.demo.config;

import feign.Client;
import feign.Feign;
import feign.okhttp.OkHttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.clientconfig.OkHttpFeignConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname OkHttpConfig
 * @Description TODO
 * @Date 2021/6/10
 * @Created by zengzg
 */
@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureAfter(value = {FeignAutoConfiguration.class, OkHttpFeignConfiguration.class})
public class FeignOkHttpConfig {

    @Bean
    @ConditionalOnMissingBean(Client.class)
    public Client feignClient(okhttp3.OkHttpClient client) {
        return new OkHttpClient(client);
    }
}
