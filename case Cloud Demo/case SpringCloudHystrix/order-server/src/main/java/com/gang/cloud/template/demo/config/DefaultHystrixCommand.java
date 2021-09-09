package com.gang.cloud.template.demo.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname HystrixCommand
 * @Description TODO
 * @Date 2021/9/8
 * @Created by zengzg
 */
public class DefaultHystrixCommand extends HystrixCommand {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String name;

    public DefaultHystrixCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    // 如果继承的是HystrixObservableCommand，要重写Observable construct()
    @Override
    protected String run() {
        return "Hello " + name;
    }
}
