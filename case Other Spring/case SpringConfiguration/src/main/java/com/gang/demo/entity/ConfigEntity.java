package com.gang.demo.entity;

/**
 * @Classname ConfigEntity
 * @Description TODO
 * @Date 2020/4/28 22:41
 * @Created by zengzg
 */
public class ConfigEntity {

    private String username;
    private String userage;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserage() {
        return userage;
    }

    public void setUserage(String userage) {
        this.userage = userage;
    }

    @Override
    public String toString() {
        return "ConfigEntity{" +
                "username='" + username + '\'' +
                ", userage='" + userage + '\'' +
                '}';
    }
}
