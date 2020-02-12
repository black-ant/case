package com.gang.study.container.demo.logic;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Classname LinkedBlockingQueue
 * @Description TODO
 * @Date 2020/2/12 21:22
 * @Created by zengzg
 */
public class LinkedBlockingQueueTest {

    // 生产者线程数量
    private final static int providerThreadAmount = 5;

    // 记录每一个生产者线程是否处理完毕的标记
    private static boolean[] providerDoneFlag = new boolean[providerThreadAmount];

    // 整个所有的生产者线程全部结束的标记
    private static boolean done = false;

    // 一个线程安全的队列，用于生产者和消费者异步地信息交互
    private static LinkedBlockingQueue<String> linkedBlockingQeque = new LinkedBlockingQueue<String>();

    static class ProviderThread extends Thread {
        private Thread thread;
        private String threadName;
        private int threadNo;

        public ProviderThread(String threadName2, int threadNo) {
            this.threadName = threadName2;
            this.threadNo = threadNo;
        }

        public void start() {
            if (thread == null) {
                thread = new Thread(this, threadName);
            }

            thread.start();
            System.out.println(
                    (new Date().getTime()) + " " + threadName + " starting... " + Thread.currentThread().getName());
        }

        @Override
        public void run() {
            int rows = 0;
            for (int i = 0; i < 100; i++) {
                String string = String.format("%s-%d-%s", threadName, i, Thread.currentThread().getName());
                // offer不会去阻塞线程，put会
                //linkedBlockingQeque.offer(string);
                try {
                    linkedBlockingQeque.put(string);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                rows++;
                /*
                 * try { Thread.sleep((new Random()).nextInt(5) * 1000); } catch
                 * (InterruptedException e) { e.printStackTrace(); }
                 */
            }

            // 本线程处理完毕的标记
            LinkedBlockingQueueTest.providerDoneFlag[threadNo] = true;
            System.out.println((new Date().getTime()) + " " + threadName + " end. total rows is " + rows + "\t"
                    + Thread.currentThread().getName());
        }
    }

    static class ConsumerThread implements Runnable {
        private Thread thread;
        private String threadName;

        public ConsumerThread(String threadName2) {
            this.threadName = threadName2;
        }

        public void start() {
            if (thread == null) {
                thread = new Thread(this, threadName);
            }

            thread.start();
            System.out.println(
                    (new Date().getTime()) + " " + threadName + " starting... " + Thread.currentThread().getName());
        }

        @Override
        public void run() {
            int rows = 0;
            // 生产者线程没有结束，或者消息队列中有元素的时候，去队列中取数据
            while (LinkedBlockingQueueTest.getDone() == false || linkedBlockingQeque.isEmpty() == false) {
                //在甘肃电信的实际应用中发现，当数据的处理量达到千万级的时候，带参数的poll会将主机的几百个G的内存耗尽，jvm会提示申请内存失败，并将进程退出。网上说，这是这个方法的一个bug。
                //String string = linkedBlockingQeque.poll(3, TimeUnit.SECONDS);
                String string = linkedBlockingQeque.poll();
                if (string == null) {
                    continue;
                }

                rows++;

                System.out
                        .println((new Date().getTime()) + " " + threadName + " get msg from linkedBlockingQeque is "
                                + string + "\t" + Thread.currentThread().getName());
                /*
                 * try { Thread.sleep((new Random()).nextInt(5) * 1000); } catch
                 * (InterruptedException e) { e.printStackTrace(); }
                 */

            }
            System.out.println((new Date().getTime()) + " " + threadName + " end total rows is " + rows + "\t"
                    + Thread.currentThread().getName());
        }
    }

    public static synchronized void setDone(boolean flag) {
        LinkedBlockingQueueTest.done = flag;
    }

    public static synchronized boolean getDone() {
        return LinkedBlockingQueueTest.done;
    }

    public static void main(String[] args) {
        System.out.println((new Date().getTime()) + " " + "process begin at " + Thread.currentThread().getName());
        System.out.println(
                (new Date().getTime()) + " " + "linkedBlockingDeque.hashCode() is " + linkedBlockingQeque.hashCode());

        // 启动若干生产者线程
        for (int i = 0; i < providerThreadAmount; i++) {
            String threadName = String.format("%s-%d", "ProviderThread", i);
            ProviderThread providerThread = new ProviderThread(threadName, i);
            providerThread.start();
        }

        // 启动若干个消费者线程
        for (int i = 0; i < 10; i++) {
            String threadName = String.format("%s-%d", "ConsumerThread", i);
            ConsumerThread consumerThread = new ConsumerThread(threadName);
            consumerThread.start();
        }

        // 循环检测生产者线程是否处理完毕
        do {
            for (boolean b : providerDoneFlag) {
                if (b == false) {
                    /*
                     * try { Thread.sleep(3 * 1000); System.out.println((new Date().getTime()) +
                     * " "+"sleep 3 seconds. linkedBlockingQeque.size() is "+linkedBlockingQeque.
                     * size() + "\t" + Thread.currentThread().getName()); } catch
                     * (InterruptedException e) { e.printStackTrace(); }
                     */

                    // 只要有一个生产者线程没有结束，则整个生产者线程检测认为没有结束
                    break;
                }

                LinkedBlockingQueueTest.setDone(true);
            }

            // 生产者线程全部结束的时候，跳出检测
            if (LinkedBlockingQueueTest.getDone() == true) {
                break;
            }
        } while (true);

        System.out.println((new Date().getTime()) + " process done successfully\t" + Thread.currentThread().getName());
    }
}

/**
 *
 *
 *
 */
