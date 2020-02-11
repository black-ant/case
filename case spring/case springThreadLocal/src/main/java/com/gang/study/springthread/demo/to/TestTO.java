package com.gang.study.springthread.demo.to;

import lombok.Data;

/**
 * @Classname TestTO
 * @Description TODO
 * @Date 2020/2/11 11:34
 * @Created by zengzg
 */
@Data
public class TestTO {

    public ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

    private String name;

    public TestTO(String name) {
        this.name = name;
    }

    public Integer getThreadValue() {
        return threadLocal.get();
    }

    public void setThreadValue(int nameValue) {
        threadLocal.set(nameValue);
    }
}
