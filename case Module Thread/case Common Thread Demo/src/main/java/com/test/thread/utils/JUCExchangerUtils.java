package com.test.thread.utils;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * @Classname JUCExchangerUtils
 * @Description TODO
 * @Date 2021/3/16
 * @Created by zengzg
 */
@Component
public class JUCExchangerUtils implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        doExchange();
    }

    public void doExchange() {
        List<String> buffer1 = new ArrayList<String>();
        List<String> buffer2 = new ArrayList<String>();

        Exchanger<List<String>> exchanger = new Exchanger<List<String>>();

        Thread producerThread = new Thread(new Producer(buffer1, exchanger));
        Thread consumerThread = new Thread(new Consumer(buffer2, exchanger));

        producerThread.start();
        consumerThread.start();

    }

    static class Producer implements Runnable {

        //生产者、消费者交换的数据结构
        private List<String> buffer;

        //步生产者和消费者的交换对象
        private Exchanger<List<String>> exchanger;

        Producer(List<String> buffer, Exchanger<List<String>> exchanger) {
            this.buffer = buffer;
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            for (int i = 1; i < 5; i++) {
                System.out.println("生产者第" + i + "次提供");
                for (int j = 1; j <= 3; j++) {
                    System.out.println("生产者装入" + i + "--" + j);
                    buffer.add("buffer：" + i + "--" + j);
                }

                System.out.println("生产者装满，等待与消费者交换...");
                try {
                    exchanger.exchange(buffer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer implements Runnable {
        private List<String> buffer;

        private final Exchanger<List<String>> exchanger;

        public Consumer(List<String> buffer, Exchanger<List<String>> exchanger) {
            this.buffer = buffer;
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            for (int i = 1; i < 5; i++) {
                //调用exchange()与消费者进行数据交换
                try {
                    buffer = exchanger.exchange(buffer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("消费者第" + i + "次提取");
                for (int j = 1; j <= 3; j++) {
                    System.out.println("消费者 : " + buffer.get(0));
                    buffer.remove(0);
                }
            }
        }
    }
}
