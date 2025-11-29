package org.example.service;


import org.noear.solon.annotation.Component;

@Component("demoService")
public class DemoService {

    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }
}
