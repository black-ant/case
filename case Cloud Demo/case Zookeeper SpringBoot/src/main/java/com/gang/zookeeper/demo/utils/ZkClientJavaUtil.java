package com.gang.zookeeper.demo.utils;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Classname ZkClientJavaUtil
 * @Description Java 原生客户端
 * @Date 2021/8/9
 * @ by https://www.zybuluo.com/boothsun/note/990793
 */
public class ZkClientJavaUtil {

    private static final Logger logger = LoggerFactory.getLogger(ZkClientJavaUtil.class);
    private static ZooKeeper zk;
    //  /zfpt 必须提前创建好
    private static String zkPath = "master:2181,slave1:2181,slave2:2181/zfpt";
    static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    static {
        try {
            zk = new ZooKeeper(zkPath, 1000, new Watcher() {
                // 监控所有被触发的事件
                public void process(WatchedEvent event) {
                    logger.info("已经触发了 {} 事件！ ", event.getType());
                    connectedSemaphore.countDown();
                }
            });
        } catch (Exception e) {
            System.err.println("系统异常");
        }
    }

    public static ZooKeeper getZKConnection() {
        try {
            if (zk == null) {
                connectedSemaphore.await();
            }
            return zk;
        } catch (Exception e) {
            System.err.printf("ZK初始化失败");
        }
        return null;
    }
}

