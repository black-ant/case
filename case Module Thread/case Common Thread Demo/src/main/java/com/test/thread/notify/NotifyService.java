package com.test.thread.notify;

import com.test.thread.to.CommonTO;
import com.test.thread.to.LockBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname NotifyService
 * @Description TODO
 * @Date 2021/3/12 10:38
 * @Created by zengzg
 */
@Component
public class NotifyService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        testNotify();

    }

    public void testNotify() {
        logger.info("------> [测试 Notify Wait] <-------");

        LockBean lockBean = new LockBean("NotifyService");

        WaitThread thread = new WaitThread(lockBean);
        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
        }

        NotifyThread notifyThread = new NotifyThread(lockBean);
        notifyThread.start();

    }
}
