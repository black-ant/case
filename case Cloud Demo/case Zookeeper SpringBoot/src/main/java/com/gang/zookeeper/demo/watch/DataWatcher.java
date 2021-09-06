package com.gang.zookeeper.demo.watch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @Classname DataWatcher
 * @Description TODO
 * @Date 2021/9/6
 * @Created by zengzg
 */
public class DataWatcher implements Watcher {

    @Override
    public void process(WatchedEvent event) {

        System.out.println("==========DataWatcher start==============");
        System.out.println("DataWatcher state: " + event.getState().name());
        System.out.println("DataWatcher type: " + event.getType().name());
        System.out.println("DataWatcher path: " + event.getPath());
        System.out.println("==========DataWatcher end==============");
    }
}

