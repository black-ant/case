package com.gang.study.ldap.demo.service;

import com.gang.study.ldap.demo.to.LDAPConfig;
import com.gang.study.ldap.demo.to.LdapAuthType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.ldap.LdapContext;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2020/8/7 22:15
 * @Created by zengzg
 */
@Component
public class StartController implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void run(ApplicationArguments args) throws Exception {

        // LDAP Simple
        //        LDAPConfig config = new LDAPConfig("192.168.2.75", "389", "devad\\administrator", "Passw0rd@2019", Boolean.FALSE);

        // LDAP None Auth
        LDAPConfig config = new LDAPConfig("192.168.2.75", "636", null, null, Boolean.TRUE);
        config.setAuthType(LdapAuthType.NONE);
        config.setBaseContxt("DC=devad,DC=com,DC=cn");

        LdapContext context = null;
        try {
            context = LDAPConnect.createLdapContext(config);
        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
        }

        logger.info("------> this is Auth Over <-------");


        NamingEnumeration<NameClassPair> name = context.list("devad\\\\administrator");
        logger.info("------> name :{} <-------", name);
    }
}
