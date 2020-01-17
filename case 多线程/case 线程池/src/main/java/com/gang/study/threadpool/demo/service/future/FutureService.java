package com.gang.study.threadpool.demo.service.future;

import java.util.concurrent.Callable;

public class FutureService implements Callable<String> {

    private String para;

    public FutureService(String para) {
        this.para = para;
    }

    @Override
    public String call() throws Exception {
        //真实的业务逻辑
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(para);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }

        return sb.toString();
    }

}
