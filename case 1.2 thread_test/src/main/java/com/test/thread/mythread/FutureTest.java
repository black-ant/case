package com.test.thread.mythread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureTest {

    private static List<Future<String>> futureList = new ArrayList<Future<String>>();

    private static Logger logger = LoggerFactory.getLogger(FutureTest.class);


    public static void main(String[] args) {
        FutureTest t = new FutureTest();

//        t.generate(3);
//        t.doOtherThings();
//        t.getResult(futureList);

        logger.info("thread start");
        testThread();
        logger.info("thread end");
    }

    public static void testThread() {
        new Thread() {
            @Override
            public void run() {
                logger.info("thread in");
                try {
                    sleep(5000);
                    logger.info("thread countinue");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
        logger.info("thread ok");
    }

    /**
     * 生成指定数量的线程，都放入future数组
     *
     * @param threadNum
     */
    public void generate(int threadNum) {
        ExecutorService service = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < threadNum; i++) {
            Callable<String> job = getJob(i);
            Future<String> f = service.submit(job);
            futureList.add(f);
        }
        //关闭线程池,不影响线程的执行
        service.shutdown();
    }

    /**
     * other things
     */
    public void doOtherThings() {
        try {
            for (int i = 0; i < 3; i++) {
                System.out.println("Do thing No:" + i);
                Thread.sleep(new Random().nextInt(5000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从future中获取线程结果，打印结果
     *
     * @param fList
     */
    public void getResult(List<Future<String>> fList) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(getCollectJob(fList));
        service.shutdown();
    }

    /**
     * 生成指定序号的线程对象
     *
     * @param i
     * @return
     */
    public Callable<String> getJob(final int i) {
        final int time = new Random().nextInt(10);
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000 * time);
                return "thread-" + i;
            }
        };
    }

    /**
     * 生成结果收集线程对象
     *
     * @param fList
     * @return
     */
    public Runnable getCollectJob(final List<Future<String>> fList) {
        return new Runnable() {
            @Override
            public void run() {
                for (Future<String> future : fList) {
                    try {
                        while (true) {
                            if (future.isDone() && !future.isCancelled()) {
                                System.out.println("Future:" + future + ", Result:" + future.get());
                                break;
                            } else {
                                System.out.println("休眠100ms...");
                                Thread.sleep(100);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}