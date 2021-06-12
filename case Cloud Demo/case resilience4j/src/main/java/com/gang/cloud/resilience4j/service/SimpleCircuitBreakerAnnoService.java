package com.gang.cloud.resilience4j.service;

import com.gang.cloud.resilience4j.client.RemoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Classname SimpleCircuitBreakerAnnoService
 * @Description 通过注解的方式添加断路器
 * @Date 2021/6/11
 * @Created by zengzg
 */
@Service
public class SimpleCircuitBreakerAnnoService {

    @Autowired
    private RemoteClient remoteClient;

    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "order")
    public String get() {
        return remoteClient.test();
    }
}
