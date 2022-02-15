package org.apache.dubbo.demo.consumer.controller;

import org.apache.dubbo.demo.DemoService;
import org.apache.dubbo.demo.GreetingService;
import org.apache.dubbo.demo.consumer.comp.DemoServiceComponent;
import org.apache.dubbo.demo.consumer.comp.GrettingServiceComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2021/10/7
 * @Created by zengzg
 */
@RequestMapping("/start")
@RestController
public class StartController {

    @Autowired
    private DemoService demoService;

    @Autowired
    private GreetingService greetingService;

    @GetMapping
    public String test() {
        String hello = demoService.sayHello("world");
        System.out.println("result :" + hello);

        String hello2 = greetingService.hello();
        System.out.println("result :" + hello2);

        return "success";
    }
}
