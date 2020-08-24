package com.gang.study.mapstruct.demo.to;

import java.util.Date;

/**
 * @Classname PersonDO
 * @Description TODO
 * @Date 2020/8/22 15:41
 * @Created by zengzg
 */

public class PersonDO {

    private Integer id;
    private String name;
    private int age;
    private Date birthday;
    private String gender;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

