package com.gang.study.ldaptive.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname LDAPJDK8Service
 * @Description TODO
 * @Date 2020/7/13 15:11
 * @Created by zengzg
 */
@Component
public class LDAPJDK8Service implements LDAPOperation {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String connect() {
        return null;
    }

    @Override
    public String create() {
        return null;
    }

    @Override
    public String update() {
        return null;
    }

    @Override
    public String delete() {
        return null;
    }

    @Override
    public void get() {

    }

    @Override
    public void search() {

    }
}
