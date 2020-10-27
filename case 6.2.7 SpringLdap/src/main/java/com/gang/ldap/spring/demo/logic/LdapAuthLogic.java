package com.gang.ldap.spring.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.ldap.core.AuthenticationSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Component;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * @Classname LdapAuthLogic
 * @Description TODO
 * @Date 2020/10/15 17:51
 * @Created by zengzg
 */
@Component
public class LdapAuthLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LdapTemplate ldapTemplate;

    public boolean doLdapAuthSpring() {
        Boolean isOK = Boolean.FALSE;
        try {

            ldapTemplate.authenticate(query().where("uid").is("yangm"), "qqqqqq!1");
            isOK = Boolean.TRUE;
        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
        }
        return isOK;
    }


}
