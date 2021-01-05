package com.gang.simplecase.demo.service.to;

/**
 * @Classname User
 * @Description TODO
 * @Date 2020/9/15 11:09
 * @Created by zengzg
 */

public class User {

    private String username;

    private Address address;

    private String age;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", address=" + address +
                ", age='" + age + '\'' +
                '}';
    }
}
