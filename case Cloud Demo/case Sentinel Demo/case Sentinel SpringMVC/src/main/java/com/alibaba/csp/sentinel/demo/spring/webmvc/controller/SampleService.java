package com.alibaba.csp.sentinel.demo.spring.webmvc.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Component;

@Component
public class SampleService {

    @SentinelResource(value="annotationSource")
    public void test(){
        System.out.println("OK.业务成功执行");
    }


}
