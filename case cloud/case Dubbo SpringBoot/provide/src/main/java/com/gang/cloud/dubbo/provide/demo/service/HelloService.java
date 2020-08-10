package com.gang.cloud.dubbo.provide.demo.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * @Classname HelloService
 * @Description TODO
 * @Date 2020/8/10 22:50
 * @Created by zengzg
 */
@Service(interfaceClass = IHelloService.class)
@Component
public class HelloService implements IHelloService {
}
