package com.gang.zookeeper.demo.watch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname ZkWatchService
 * @Description TODO
 * @Date 2021/9/6
 * @Created by zengzg
 */
@Component
public class ZkWatchService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ZooKeeper zkClient;

    public void run() {
        testWatch();
    }

    public void testWatch() {
        String path = "/testWatch";
        String data = "before";


        // 节点数据变化的通知
        try {
            // Step 1 : 创建节点
            logger.info("------> 创建节点 <-------");
//            Stat stat = zkClient.exists(path, new ExistsWatch());
            Stat stat = zkClient.exists(path, false);
            if (stat == null) {
                zkClient.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            // Step 2 : 获取 Data ,同时添加监听器
            logger.info("------> 添加监听节点 <-------");
            watchDataChange(path);

            // Step 3 : 设置数据
            data = "after";
            logger.info("------> 修改数据 <-------");
            zkClient.setData(path, data.getBytes(), -1);

            // Step 4 : 测试一次性
            data = "after-2";
            logger.info("------> 再次修改数据 <-------");
            zkClient.setData(path, data.getBytes(), -1);

        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
        }
    }


    public void watchDataChange(String path) throws Exception {
        DataWatcher dataWatcher = new DataWatcher();
        zkClient.getData(path, dataWatcher, null);
    }
}
