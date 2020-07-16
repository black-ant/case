package com.gang.study.ldaptive.demo.logic;

import com.alibaba.fastjson.JSONObject;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.LdapEntry;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Classname StartLogic
 * @Description TODO
 * @Date 2020/7/13 14:46
 * @Created by zengzg
 */
@Component
public class StartLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationContext context;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        runJDK11();
    }

    public void runJDK8() {
        LDAPOperation ldapOperation = context.getBean("LDAPJDK8Service", LDAPOperation.class);
    }

    public void runJDK11() throws Exception {
        LDAPOperation ldapOperation = context.getBean("LDAPJDK11Service", LDAPOperation.class);
        ldapOperation.connect();
    }

}
