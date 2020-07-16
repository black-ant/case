package com.gang.study.ldaptive.demo.logic;

import com.alibaba.fastjson.JSONObject;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.LdapEntry;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname LDAPJDK11Service
 * @Description TODO
 * @Date 2020/7/13 15:11
 * @Created by zengzg
 */
@Component
public class LDAPJDK11Service implements LDAPOperation {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * @return
     */
    @Override
    public String connect() {
        try {
            logger.info("------> do connect <-------");
            ConnectionFactory factory = new DefaultConnectionFactory("ldap://192.168.7.15:389");
            Connection connection = factory.getConnection();
            connection.open();
            logger.info("------> this is connect :{} <-------", connection.isOpen());
            connection.close();
        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
        }
        return "get Conenct";
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
