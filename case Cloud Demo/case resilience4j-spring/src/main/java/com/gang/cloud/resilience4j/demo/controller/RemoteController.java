package com.gang.cloud.resilience4j.demo.controller;

import com.gang.cloud.resilience4j.demo.service.CircuitBreakerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;

/**
 * @Classname RemoteController
 * @Description TODO
 * @Date 2021/6/11
 * @Created by zengzg
 */
@RestController
@RequestMapping("remote")
public class RemoteController {


    @Autowired
    private CircuitBreakerServiceImpl circuitBreakerService;

    @GetMapping("get")
    public String get() {
        for (int i = 0; i < 10; i++) {
            // circuitService.circuitBreakerRetryAOP();
            try {
                circuitBreakerService.circuitBreakerRetryAOP();
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "finish";
    }

}
