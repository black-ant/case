package com.gang.study.ldap.ldap;

import com.gang.study.ldap.ldap.service.OrgOp;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LdapApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrgOp orgOp;

    @Test
    void contextLoads() {
        orgOp.testConnect();
        logger.info("------> LDAP Connect OK <-------");
        orgOp.createOrg();
    }

}
