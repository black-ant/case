package com.test.thread.notify;

import com.test.thread.to.CommonTO;
import com.test.thread.to.LockBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname WaitThread
 * @Description TODO
 * @Date 2021/3/12 10:51
 * @Created by zengzg
 */
public class WaitThread extends Thread {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private LockBean lockBean;

    public WaitThread(LockBean lockBean) {
        this.lockBean = lockBean;
    }

    @Override
    public void run() {
        logger.info("------> WaitThread run [{}] <-------", lockBean.getMsg());

        synchronized (lockBean) {
            try {
                lockBean.wait();
            } catch (InterruptedException e) {
                logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            }
            logger.info("------> WaitThread run over [{}] <-------", lockBean.getMsg());
        }
    }
}
