package com.gang.study.mapstruct.demo.to;

import java.util.Date;

/**
 * @Classname PersonDTO
 * @Description TODO
 * @Date 2020/8/22 15:46
 * @Created by zengzg
 */

public class PersonDTO {

    private String userName;
    private Integer age;
    private Date birthday;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }
}
