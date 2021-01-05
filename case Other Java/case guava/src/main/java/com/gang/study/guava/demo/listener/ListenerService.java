package com.gang.study.guava.demo.listener;

import com.google.common.eventbus.Subscribe;

/**
 * @Classname ListenerService
 * @Description TODO
 * @Date 2020/8/4 17:31
 * @Created by zengzg
 */
public class ListenerService {

    public int lastMessage = 0;

    @Subscribe
    public void listen(TestEvent event) {
        lastMessage = event.getMessage();
        System.out.println("Message:" + lastMessage);
    }

    public int getLastMessage() {
        return lastMessage;
    }
}
