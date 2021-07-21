package com.test.thread.atom;

import com.test.thread.volatilethread.ThreadD;
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

//        testAtomicInteger();

//        testThead();
    }

    public void testAtomicInteger() {
        AtomicInteger integer = new AtomicInteger();

        logger.info("------> 1 > 获取原子变量 :[{}] <-------", integer.get());

        // Step 2 ：　设置参数
        integer.set(999);

        logger.info("------> 2 > 获取原子变量 :[{}] <-------", integer.get());


        logger.info("------> 失败比较获取 : 测试比较判断 :[{}] <-------", integer.compareAndSet(0, 998));
        logger.info("------> 3 > 获取原子变量 :[{}] <-------", integer.get());

        logger.info("------> 成功比较获取 : 测试比较判断 :[{}] <-------", integer.compareAndSet(999, 998));
        logger.info("------> 4 > 获取原子变量 :[{}] <-------", integer.get());


        // Step 3 : 获取当前的值，并设置新的值
        logger.info("------> 测试比较判断 :[{}] <-------", integer.getAndSet(888));
        logger.info("------> 5 > 获取原子变量 :[{}] <-------", integer.get());


        // Step 4 : 获取当前的值，并设置新的值
        logger.info("------> 测试比较判断 :[{}] <-------", integer.getAndIncrement());
        logger.info("------> 6 > 获取原子变量 :[{}] <-------", integer.get());

        // 以原子方式给当前值加1并获取新值
        logger.info("------> 测试比较判断 :[{}] <-------", integer.incrementAndGet());
        logger.info("------> 6-1 > 获取原子变量 :[{}] <-------", integer.get());

        // Step 5 : 获取当前的值，并设置新的值
        logger.info("------> 测试比较判断 :[{}] <-------", integer.getAndDecrement());
        logger.info("------> 7 > 获取原子变量 :[{}] <-------", integer.get());

        // 以原子方式给当前值减1并获取新值
        logger.info("------> 测试比较判断 :[{}] <-------", integer.decrementAndGet());
        logger.info("------> 7 > 获取原子变量 :[{}] <-------", integer.get());

        // Step 6 : 获取当前的值，并设置新的值
        logger.info("------> 测试比较判断 :[{}] <-------", integer.getAndAdd(99));
        logger.info("------> 8 > 获取原子变量 :[{}] <-------", integer.get());

        // 以原子方式给当前值加delta并获取新值
        logger.info("------> 测试比较判断 :[{}] <-------", integer.addAndGet(99));
        logger.info("------> 8 > 获取原子变量 :[{}] <-------", integer.get());
    }

    /**
     * 测多线程方式
     */
    public void testThead() throws Exception {
        InnerTO innerTO = new InnerTO();

        MyThread[] threadDSS = new MyThread[1000];

        for (int i = 0; i < 1000; i++) {
            threadDSS[i] = new MyThread(innerTO);
        }

        for (int i = 0; i < 1000; i++) {
            threadDSS[i].start();
        }

        logger.info("------> 原子类线程 Start 完成 :{} <-------", innerTO.getInteger().get());

        for (int i = 0; i < 1000; i++) {
            if (i % 100 == 0) {
                Thread.sleep(1);
                logger.info("------> 测试原子类 :{} <-------", innerTO.getInteger().get());
            }
        }

    }


    class InnerTO {

        AtomicInteger integer = new AtomicInteger();

        public AtomicInteger getInteger() {
            return integer;
        }

        public void setInteger(AtomicInteger integer) {
            this.integer = integer;
        }
    }


    class MyThread extends Thread {

        public InnerTO innerTO = new InnerTO();

        public MyThread(InnerTO innerTO) {
            this.innerTO = innerTO;
        }

        @Override
        public void run() {
            int i = innerTO.getInteger().getAndIncrement();
            if (i == 999) {
                logger.info("------> 线程执行完成 <-------");
            }
        }
    }


}
