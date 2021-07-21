package com.gang.study.container.demo.logic;

import com.gang.study.container.demo.to.CommonTO;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Classname ArrayBlockingQueueTest
 * @Description TODO
 * @Date 2021/3/6 21:38
 * @Created by zengzg
 */
@Component
public class ArrayBlockingQueueTest implements ApplicationRunner {

    ArrayBlockingQueue queue = new ArrayBlockingQueue(99);


    @Override
    public void run(ApplicationArguments args) throws Exception {
        queue.offer(new CommonTO());
    }
}
