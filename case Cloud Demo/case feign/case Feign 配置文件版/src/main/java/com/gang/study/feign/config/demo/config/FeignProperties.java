package com.gang.study.feign.config.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname ParaFeignClientProperties
 * @Description TODO
 * @Date 2020/4/22 23:11
 * @Created by zengzg
 */
@ConfigurationProperties(
        prefix = "com.gang.feign",
        ignoreUnknownFields = true,
        ignoreInvalidFields = true
)
@Data
@Component
public class FeignProperties implements Serializable {

    public static final String PREFIX = "com.gang.feign";
    private boolean authorizationRequestInterceptor = true;
    private boolean enable = true;
    private Map<String, FeignClientBean> config = new HashMap();

    public Map<String, FeignProperties.FeignClientBean> getConfig() {
        return this.config;
    }

    public void setConfig(Map<String, FeignProperties.FeignClientBean> config) {
        this.config = config;
    }

    @Data
    public static class FeignClientBean implements Serializable {

        private String contextPath;
        private String serverId;
        private boolean decode404 = false;
        private Class<?> fallback;
        private Class<?> fallbackFactory;
        private boolean primary;
        private String url;
        private List<Class<?>> beanInjections;


    }
}
