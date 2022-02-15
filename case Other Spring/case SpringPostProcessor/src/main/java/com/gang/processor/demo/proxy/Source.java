package com.gang.processor.demo.proxy;

import org.springframework.stereotype.Service;

/**
 * @Classname Source
 * @Description TODO
 * @Date 2021/10/5
 * @Created by zengzg
 */
@Service
public class Source implements Sourceable {

    @Override
    public void method() {
        System.out.println("the original method!");
    }
}
