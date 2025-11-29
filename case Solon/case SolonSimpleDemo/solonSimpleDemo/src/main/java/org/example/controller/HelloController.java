package org.example.controller;

import org.example.service.DemoService;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloController {

    @Inject
    DemoService demoService;
    /**
     * 这是直接返回值
     * */
    @Mapping("/")
    public String hello() {
        return "Hello world!";
    }

    /**
     * 这是返回个对象（以json形式）
     * */
    @Mapping("/json")
    public Map hello_json() {
        Map<String,Object> map = new HashMap<>(); //实体也ok
        map.put("message", demoService.sayHello("World"));

        return map;
    }

}