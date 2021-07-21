package com.test.thread.to;

/**
 * @Classname LockBean
 * @Description TODO
 * @Date 2021/3/12 15:26
 * @Created by zengzg
 */
public class LockBean {

    private String msg = "no msg";

    public LockBean(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
