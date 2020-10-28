package com.gang.aop.demo.proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Classname ProxyConfig
 * @Description TODO
 * @Date 2020/10/28 17:35
 * @Created by zengzg
 */
@Configuration
public class ProxyConfig {

    @Bean
    public ExInterface getExInterface() {
        MySelfService selfService = new MySelfService();
        JDKProxy jdkProxy = new JDKProxy();
        //创建代理对象
        ExInterface proxy = jdkProxy.createProxy(selfService, AfterAop.class);
        return proxy;
    }
}
