package com.test.thread.atom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Classname AtomClassTest
 * @Description TODO
 * @Date 2021/3/16
 * @Created by zengzg
 */
@Component
public class AtomClassTest implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {

        testAtomicInteger();
    }

    public void testAtomicInteger(){
        AtomicInteger integer = new AtomicInteger();
        integer.get();
    }
}
