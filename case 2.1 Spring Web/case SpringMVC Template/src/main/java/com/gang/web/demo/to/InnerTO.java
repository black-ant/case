package com.gang.web.demo.to;

import java.util.List;
import java.util.Map;

/**
 * @Classname InnerTO
 * @Description TODO
 * @Date 2021/5/11
 * @Created by zengzg
 */
public class InnerTO {

    private String name;

    private Integer age;

    private Map<String, String> map;

    private List<String> list;

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

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "InnerTO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", map=" + map +
                ", list=" + list +
                '}';
    }
}
