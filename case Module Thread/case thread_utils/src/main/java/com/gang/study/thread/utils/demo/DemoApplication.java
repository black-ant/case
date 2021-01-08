package com.gang.study.thread.utils.demo;

import com.gang.study.thread.utils.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

//    @Autowired
//    private JUCCyclicBarrierUtils jucCyclicBarrierUtils;

//    @Autowired
//    private JUCCountDownLatchUtils jucCountDownLatchUtils;

//    @Autowired
//    private ThreadCallable threadCallable;

//    @Autowired
//    private ThreadParamShard threadParamShard;

    @Autowired
    private ThreadExceptionCatch threadExceptionCatch;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
