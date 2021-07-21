package com.gang.study.container.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Classname ConcurrentLinkedQueueTest
 * @Description TODO
 * @Date 2021/2/19 11:28
 * @Created by zengzg
 */
@Service
public class ConcurrentLinkedQueueTest implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ConcurrentLinkedQueue hashMap = new ConcurrentLinkedQueue();
        hashMap.add("One");
        hashMap.add("Two");
        hashMap.add("Three");
        hashMap.add("Four");


        logger.info("------> {} <-------", hashMap.poll());
        logger.info("------> {} <-------", hashMap.poll());
        logger.info("------> {} <-------", hashMap.poll());
        logger.info("------> {} <-------", hashMap.poll());

        logger.info("------> 弹出空对象 <-------");
        logger.info("------> {} <-------", hashMap.poll());
        logger.info("------> {} <-------", hashMap.poll());

    }

//    public void offer() {
//        for (Node<E> t = tail, p = t; ; ) {
//            Node<E> q = p.next;
//            if (q == null) {
//                if (p.casNext(null, newNode)) {
//                    if (p != t)
//                        casTail(t, newNode);
//                    return true;
//                }
//            } else if (p == q)
//                p = (t != (t = tail)) ? t : head;
//            else
//                p = (p != t && t != (t = tail)) ? t : q;
//        }
//    }

//    public void pop() {
//        for (; ; ) {
//            for (Node<E> h = head, p = h, q; ; ) {
//                E item = p.item;
//
//                if (item != null && p.casItem(item, null)) {
//                    if (p != h)
//                        updateHead(h, ((q = p.next) != null) ? q : p);
//                    return item;
//                } else if ((q = p.next) == null) {
//                    updateHead(h, p);
//                    return null;
//                } else if (p == q)
//                    continue restartFromHead;
//                else
//                    p = q;
//            }
//        }
//    }
}
