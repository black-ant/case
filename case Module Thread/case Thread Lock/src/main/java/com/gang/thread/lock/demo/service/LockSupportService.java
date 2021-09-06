package com.gang.thread.lock.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.LockSupport;

/**
 * @Classname LockSupportService
 * @Description TODO
 * @Date 2021/8/30
 * @Created by zengzg
 */
public class LockSupportService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static Object object = new Object();

    public void main(String[] args) {
        LockSupportService lockSupportService = new LockSupportService();
        try {
            lockSupportService.testGetBlocker();
            lockSupportService.testPark();
            lockSupportService.testParkAndUnpark();
            lockSupportService.testParkNanos();
            lockSupportService.testParkUntil();
            lockSupportService.testParkUntilL();
        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
        }
    }

    public static class TestThread extends Thread {
        public TestThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            LockSupport.park(object);
            System.out.println("当前线程: " + getName());
            if (Thread.currentThread().isInterrupted()) {
                System.out.println(getName() + "被中断了");
            }
            System.out.println(getName() + "继续执行");
        }
    }

    /**
     * park和unPark 和wait和notify功能一样，但是不能交叉使用
     * park和unPark没有顺序
     * void
     *
     * @Param
     * @author zhqwm
     * @date 2020/6/21 19:18
     */
    public void testParkAndUnpark() throws Exception {
        TestThread t1 = new TestThread("t1");
        TestThread t2 = new TestThread("t2");
        t1.start();
        Thread.sleep(1000L);
        t2.start();
        Thread.sleep(1000L);
        t1.interrupt();
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }

    public static class TestThread1 extends Thread {
        public TestThread1(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (object) {
                System.out.println("当前线程: " + getName());
                long start = System.nanoTime();
                LockSupport.parkNanos(100000000);
                System.out.println("差值:" + (System.nanoTime() - start));
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(getName() + "被中断了");
                }
                System.out.println(getName() + "继续执行");
            }
        }
    }

    /**
     * park和unPark 和wait和notify功能一样，但是不能交叉使用
     * park和unPark没有顺序
     * void
     *
     * @Param
     * @author zhqwm
     * @date 2020/6/21 19:18
     */
    public void testParkNanos() throws Exception {
        TestThread1 t1 = new TestThread1("t1");
        t1.start();
        Thread.sleep(200L);
        LockSupport.unpark(t1);
    }

    public static class TestThread2 extends Thread {
        public TestThread2(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (object) {
                System.out.println("当前线程: " + getName());
                long start = System.nanoTime();
                LockSupport.parkUntil(object, 1000);
                System.out.println("blocker:" + LockSupport.getBlocker(this));
                System.out.println("差值:" + (System.nanoTime() - start));
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(getName() + "被中断了");
                }
                System.out.println(getName() + "继续执行");
            }
        }
    }

    /**
     * 在指定的时限前禁用当前线程
     * void
     *
     * @Param
     * @author zhqwm
     * @date 2020/6/21 19:18
     */
    public void testParkUntil() throws Exception {
        TestThread2 t1 = new TestThread2("t1");
        t1.start();
//                LockSupport.unpark(t1);
    }

    /**
     * 在指定的时限前禁用当前线程
     * void
     *
     * @Param
     * @author zhqwm
     * @date 2020/6/21 19:18
     */
    public void testGetBlocker() throws Exception {
        TestThread2 t1 = new TestThread2("t1");
        t1.start();
        LockSupport.unpark(t1);
    }

    public static class TestThread3 extends Thread {
        public TestThread3(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (object) {
                System.out.println("当前线程: " + getName());
                long start = System.nanoTime();
                LockSupport.park();
                System.out.println("blocker:" + LockSupport.getBlocker(this));
                System.out.println("差值:" + (System.nanoTime() - start));
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(getName() + "被中断了");
                }
                System.out.println(getName() + "继续执行");
            }
        }
    }

    /**
     * park和unPark 和wait和notify功能一样，但是不能交叉使用
     * park和unPark没有顺序
     * void
     *
     * @Param
     * @author zhqwm
     * @date 2020/6/21 19:18
     */
    public void testPark() throws Exception {
        TestThread3 t1 = new TestThread3("t1");
        t1.start();
        LockSupport.unpark(t1);
    }

    public static class TestThread4 extends Thread {
        public TestThread4(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (object) {
                System.out.println("当前线程: " + getName());
                long start = System.nanoTime();
                LockSupport.parkNanos(10000);
                System.out.println("差值:" + (System.nanoTime() - start));
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(getName() + "被中断了");
                }
                System.out.println(getName() + "继续执行");
            }
        }
    }

    /**
     * park和unPark 和wait和notify功能一样，但是不能交叉使用
     * park和unPark没有顺序
     * void
     *
     * @Param
     * @author zhqwm
     * @date 2020/6/21 19:18
     */
    public void testParkNanosLong() throws Exception {
        TestThread4 t1 = new TestThread4("t1");
        t1.start();
    }

    public static class TestThread5 extends Thread {
        public TestThread5(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (object) {
                System.out.println("当前线程: " + getName());
                long start = System.nanoTime();
                LockSupport.parkUntil(10000);
                System.out.println("差值:" + (System.nanoTime() - start));
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(getName() + "被中断了");
                }
                System.out.println(getName() + "继续执行");
            }
        }
    }

    /**
     * 在指定的时限前禁用当前线程
     * void
     *
     * @Param
     * @author zhqwm
     * @date 2020/6/21 19:18
     */
    public void testParkUntilL() throws Exception {
        TestThread5 t1 = new TestThread5("t1");
        t1.start();
    }

}
