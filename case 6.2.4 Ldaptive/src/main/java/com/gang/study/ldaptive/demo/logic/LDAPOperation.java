package com.gang.study.ldaptive.demo.logic;

/**
 * @Classname ILdapAPI
 * @Description TODO
 * @Date 2020/7/13 15:10
 * @Created by zengzg
 */
public interface LDAPOperation {

    String connect();

    String create();

    String update();

    String delete();

    void get();

    void search();
}
