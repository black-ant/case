package com.test.thread.lock;

import org.springframework.stereotype.Component;

/**
 * @Classname LockCommonThread
 * @Description TODO
 * @Date 2021/3/22
 * @Created by zengzg
 */
public class LockCommonThread extends Thread {

    private LockTO lockTO;

    public LockCommonThread(LockTO lockTO) {
        this.lockTO = lockTO;
    }

    @Override
    public void run() {
        lockTO.test();
    }

}
