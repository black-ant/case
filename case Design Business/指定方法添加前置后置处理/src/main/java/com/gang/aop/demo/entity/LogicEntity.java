package com.gang.aop.demo.entity;

/**
 * @Classname LogicEntity
 * @Description TODO
 * @Date 2020/10/28 17:10
 * @Created by zengzg
 */
public class LogicEntity {

    private String type;

    private String info;

    public LogicEntity(String type, String info) {
        this.type = type;
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "LogicEntity{" +
                "type='" + type + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
