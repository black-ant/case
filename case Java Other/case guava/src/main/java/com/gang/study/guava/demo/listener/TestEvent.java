package com.gang.study.guava.demo.listener;

/**
 * @Classname TestEvent
 * @Description TODO
 * @Date 2020/8/4 17:33
 * @Created by zengzg
 */
public class TestEvent {

    private final int message;

    public TestEvent(int message) {
        this.message = message;
        System.out.println("event message:" + message);
    }

    public int getMessage() {
        return message;
    }
}
