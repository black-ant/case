package com.gang.study.rmi.demo.invoke.config;

import com.gang.study.rmi.demo.invoke.api.CapitalAccountService;
import com.gang.study.rmi.demo.invoke.api.CapitalAccountServiceClient;
import com.gang.study.rmi.demo.invoke.server.CapitalAccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerServiceExporter;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.remoting.support.SimpleHttpServerFactoryBean;

/**
 * @Classname RestInvoke
 * @Description TODO
 * @Date 2021/3/17
 * @Created by zengzg
 */
@Configuration
public class RestInvokeConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CapitalAccountServiceImpl capitalAccountService;

    /**
     * 构建服务提供者 Server
     *
     * @return
     */
    @Bean
    public RmiServiceExporter exporter() {
        logger.info("------> [注册远程接口 - CapitalAccountService ] <-------");
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("CapitalAccountService");
        exporter.setServiceInterface(CapitalAccountService.class);
        exporter.setService(capitalAccountService);
        exporter.setRegistryPort(8088);
//        exporter.setRegistryHost("localhost");
        return exporter;
    }

    /**
     * 构建服务消费者 Client
     *
     * @return
     * @ https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#remoting-rmi
     */
    @Bean
    public RmiProxyFactoryBean repositorySky() {
        RmiProxyFactoryBean httpInvokerProxyFactoryBean = new RmiProxyFactoryBean();
        httpInvokerProxyFactoryBean.setServiceUrl("rmi://127.0.0.1:8088/CapitalAccountService");
        httpInvokerProxyFactoryBean.setServiceInterface(CapitalAccountServiceClient.class);
        return httpInvokerProxyFactoryBean;
    }


}
