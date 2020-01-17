package com.test.thread.mythread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 10169
 * @Description 停止线程
 * @Date 2019/4/25 15:46
 * @Version 1.0
 **/
public class ThreadStop extends Thread {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
        Long time = System.currentTimeMillis();
        logger.info("线程 {} 开始运行，唯一ID ：{} ,开始时间：{}", Thread.currentThread().getName(), this.getId(), time);
        for (int i = 0; i < 100000; i++) {
            Boolean continueThread = false;
            if (this.isInterrupted()) {
                logger.info("线程已被停止 : 当前 i {}", i);
                continueThread = true;
            }
            if (continueThread) {
                logger.info("线程已被停止,但是任然运行 : 当前 i {}", i);
            } else if (i % 1000 == 0) {
                logger.info("线程正常运行: 当前 i {}", i);
            } else if (i == 5005) {
                logger.info("尝试停止线程: 当前 i {}", i);
                // interrupt 并不会立即中断线程 ，只是告诉线程该停止了 ，为其打上标志
                Thread.currentThread().interrupt();
                logger.info("线程是否停止: {}", this.isInterrupted());
            }
        }

        logger.info("打印该语句说明线程停止后还是会输出后面的:isInterrupted {}-- isalive{}", this.isInterrupted(), this.isAlive());
    }
}
