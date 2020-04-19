package com.gang.study.thread.consumerproduce.demo.logic5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Classname PCFactory
 * @Description TODO
 * @Date 2020/4/19 17:49
 * @Created by zengzg
 */

public class PCFactory extends ReentrantLock {

    private static Condition consumerCondition;

    private static Condition produceCondition;

    public PCFactory() {
        synchronized (PCFactory.class) {
            if (consumerCondition == null) {
                consumerCondition = newCondition();
            }
        }
        synchronized (PCFactory.class) {
            if (produceCondition == null) {
                produceCondition = newCondition();
            }
        }

    }

    public Condition getConsumerCondition() {
        return consumerCondition;
    }

    public Condition getProduceCondition() {
        return produceCondition;
    }
}
