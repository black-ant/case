package com.gang.zookeeper.demo.watch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname ExistsWatch
 * @Description TODO
 * @Date 2021/9/6
 * @Created by zengzg
 */
public class ExistsWatch implements Watcher {

    private static final Logger logger = LoggerFactory.getLogger(CustomWatcher.class);

    @Override
    public void process(WatchedEvent event) {
        logger.info("ExistsWatch 监听事件的状态: {}", event.getState());
        logger.info("ExistsWatch 监听事件的路径: {}", event.getPath());
        logger.info("ExistsWatch 监听事件的类型: {}", event.getType());
    }
}

