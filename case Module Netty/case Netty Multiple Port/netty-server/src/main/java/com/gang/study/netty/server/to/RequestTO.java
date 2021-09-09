package com.gang.study.netty.server.to;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname RequestTO
 * @Description TODO
 * @Date 2021/9/7
 * @Created by zengzg
 */
public class RequestTO {

    private String username;

    private Integer age;

    private Map<String, String> map = new HashMap<>();

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

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
