package com.gang.study.io.demo.streamobject;

import java.io.Serializable;

/**
 * @Classname ObjectStreamTO
 * @Description TODO
 * @Date 2022/8/23
 * @Created by zengzg
 */
public class ObjectStreamTO implements Serializable {

    private String username;

    private Integer age;

    private InnerTO innerTO;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public InnerTO getInnerTO() {
        return innerTO;
    }

    public void setInnerTO(InnerTO innerTO) {
        this.innerTO = innerTO;
    }

    static class InnerTO implements Serializable{

        private String channel;

        private String userNo;

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getUserNo() {
            return userNo;
        }

        public void setUserNo(String userNo) {
            this.userNo = userNo;
        }
    }
}
