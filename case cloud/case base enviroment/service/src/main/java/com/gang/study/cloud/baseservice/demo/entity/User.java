package com.gang.study.cloud.baseservice.demo.entity;

/**
 * @Classname User
 * @Description TODO
 * @Date 2020/4/22 17:28
 * @Created by zengzg
 */
public class User {

    private String key;

    private String name;

    private Integer age;

    private String info;

    public User(String key, String name, Integer age, String info) {
        this.key = key;
        this.name = name;
        this.age = age;
        this.info = info;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
