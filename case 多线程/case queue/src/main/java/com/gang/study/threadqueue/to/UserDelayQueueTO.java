package com.gang.study.threadqueue.to;

import java.util.Random;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Classname UserDelayQueueTO
 * @Description TODO
 * @Date 2020/7/23 18:02
 * @Created by zengzg
 */
public class UserDelayQueueTO implements Delayed {

    /* 触发时间*/
    private long time;
    private Integer userid;
    private String username;

    public UserDelayQueueTO() {
        this.time = System.currentTimeMillis() + 1000 * new Random().nextInt(9);
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return time - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        UserDelayQueueTO item = (UserDelayQueueTO) o;
        long diff = this.time - item.time;
        if (diff <= 0) {// 改成>=会造成问题
            return -1;
        } else {
            return 1;
        }
    }
}
