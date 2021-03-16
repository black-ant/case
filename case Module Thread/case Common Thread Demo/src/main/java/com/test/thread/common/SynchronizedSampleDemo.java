package com.test.thread.common;

import com.test.thread.to.CommonTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname SynchoizedSampleDemo
 * @Description TODO
 * @Date 2021/2/28 19:13
 * @Created by zengzg
 */
@Component
public class SynchronizedSampleDemo implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        showThread();

//        showThreadClass();

    }

    /**
     * 线程冲突案例 : 实例变量非线程安全
     */
    public void showThread() {
        CommonTO commonTO = new CommonTO();

        ThreadA threadA = new ThreadA(commonTO);
        threadA.start();
        try {
            // 保证 A 先 run
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ThreadB threadB = new ThreadB(commonTO);
        threadB.start();
    }

    public void showThreadClass() {

        // 走 同步代码块 , Class 锁 , 因为锁 ,2-3 步有阻塞操作
        //2021-02-28 22:15:24.350  INFO 2940 --- [       Thread-6] : check is 0 <-------
        //2021-02-28 22:15:25.350  INFO 2940 --- [       Thread-9] : check is 1 <-------
        //2021-02-28 22:15:29.351  INFO 2940 --- [       Thread-6] : check is Over synchronized 0 :100
        //2021-02-28 22:15:29.351  INFO 2940 --- [       Thread-9] : check is Over synchronized 1 :200

        // 走同步代码块 this 锁 , 注意时间 , 这里明显23步没有阻塞
        //2021-02-28 22:12:42.518  INFO 17356 --- [       Thread-6]check is 0 <-------
        //2021-02-28 22:12:43.522  INFO 17356 --- [       Thread-9]check is 1 <-------
        //2021-02-28 22:12:43.522  INFO 17356 --- [       Thread-9]check is Over synchronized 1 :200
        //2021-02-28 22:12:47.520  INFO 17356 --- [       Thread-6]check is Over synchronized 0 :100


        CommonTO commonTO = new CommonTO();
        ThreadA threadA = new ThreadA(commonTO);
        threadA.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CommonTO commonTO2 = new CommonTO();
        ThreadB threadB = new ThreadB(commonTO2);
        threadB.start();
    }
}
