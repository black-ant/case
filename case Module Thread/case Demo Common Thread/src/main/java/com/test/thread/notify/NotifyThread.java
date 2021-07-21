package com.test.thread.notify;

import com.test.thread.to.CommonTO;
import com.test.thread.to.LockBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname NotifyThread
 * @Description TODO
 * @Date 2021/3/12 10:51
 * @Created by zengzg
 */
public class NotifyThread extends Thread {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private LockBean lockBean;

    public NotifyThread(LockBean lockBean) {
        this.lockBean = lockBean;
    }

    @Override
    public void run() {
        logger.info("------> NotifyThread run [{}] <-------", lockBean.getMsg());

        synchronized (lockBean) {
            try {
                yield();
                lockBean.notify();
            } catch (Exception e) {
                logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            }

            logger.info("------> NotifyThread run over [{}] <-------", lockBean.getMsg());
        }

    }
}
