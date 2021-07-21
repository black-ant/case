package com.gang.study.thread.demo.service;

import java.util.concurrent.ForkJoinTask;

/**
 * @Classname FileForkJoinService
 * @Description TODO
 * @Date 2021/2/23 11:49
 * @Created by zengzg
 */
public class FileForkJoinService extends ForkJoinTask<Integer> {

    @Override
    public Integer getRawResult() {
        return null;
    }

    @Override
    protected void setRawResult(Integer value) {

    }

    @Override
    protected boolean exec() {
        return false;
    }
}
