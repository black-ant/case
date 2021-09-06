package com.gang.zookeeper.demo.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.function.Consumer;

/**
 * @Classname RegistryTemplate
 * @Description 一个标准的 Zookeeper 流程
 * @Date 2021/7/20
 * @Created by zengzg
 */
@Component
public class RegistryTemplate {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String ROOT = "/root";
    private final static String MESSAGE = "Hello World ! ";

    CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
        @Override
        public void run() {
            logger.info("------> [开始下一步业务处理] <-------");
        }
    });


    public void run() throws Exception {
        logger.info("------> [Step 1 : 构建一个 Zookeeper 连接] <-------");

        String connectAddress = "127.0.0.1:2181";
        int timeout = 4000;


        ZooKeeper zooKeeper = new ZooKeeper(connectAddress, timeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                logger.info("------> [Step 2 : 连接构建完成 , 当前连接状态 :{}] <-------", JSONObject.toJSONString(event));
                if (Event.KeeperState.SyncConnected == event.getState()) {
                    try {
                        cyclicBarrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        cyclicBarrier.await();
        cyclicBarrier.reset();
        logger.info("------> [开始业务处理] <-------");


        logger.info("------> [Step 3 : 判断节点是否存在] <-------");
        zooKeeper.exists(ROOT, new ExistsWatch());
//        cyclicBarrier.await();
//        cyclicBarrier.reset();

//        logger.info("------> [Step 4 : 创建节点 ] <-------");
//        Consumer<Integer> createRoot = (msg) -> {
//            try {
//                zooKeeper.create(ROOT, MESSAGE.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//            } catch (Exception e) {
//                logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
//            }
//        };
//        zooKeeper.exists(ROOT, new ConsumerWatch(createRoot, 0));
//        cyclicBarrier.await();
//        cyclicBarrier.reset();

        logger.info("------> [Step 6 : 创建子节点 ] <-------");

        for (int i = 0; i < 10; i++) {
            Consumer<Integer> createChild = (msg) -> {
                try {
                    logger.info("------> [当前节点不存在 , 添加节点 :{}] <-------", ROOT + "/" + msg);
                    zooKeeper.create(ROOT + "/" + msg, MESSAGE.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                } catch (Exception e) {
                    logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
                }
            };
            zooKeeper.exists(ROOT, new ConsumerWatch(createChild, i));
//            cyclicBarrier.await();
//            cyclicBarrier.reset();

            zooKeeper.create(ROOT + "/childZ" + new Random().nextInt(9999), (MESSAGE + "-Child").getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }


        logger.info("------> [Step 7 : 判断子节点是否存在] <-------");
        zooKeeper.exists(ROOT + "/childZ", new ExistsWatch());
//        cyclicBarrier.await();
//        cyclicBarrier.reset();

//        logger.info("------> [Step 8 : 删除子节点] <-------");
//        zooKeeper.delete(ROOT + "/childZ", -1);

//        logger.info("------> [Step 7 : 判断子节点是否存在] <-------");
//        zooKeeper.exists(ROOT + "/childZ", new ExistsWatch());
//        countDownLatch.await();

    }


    class ExistsWatch implements Watcher {

        @Override
        public void process(WatchedEvent watchedEvent) {
            logger.info("------> ExistsWatch :{} <-------", JSONObject.toJSONString(watchedEvent));
//            try {
//                cyclicBarrier.await();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    class ConsumerWatch implements Watcher {

        private Consumer<Integer> consumer;
        private Integer num;

        public ConsumerWatch(Consumer<Integer> consumer, Integer num) {
            this.consumer = consumer;
            this.num = num;
        }

        @Override
        public void process(WatchedEvent watchedEvent) {
            logger.info("------> ConsumerWatch :{} <-------", JSONObject.toJSONString(watchedEvent));
            if (true) {
                consumer.accept(num);
            }
//            try {
//                cyclicBarrier.await();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }
}
