package com.gang.study.ldap.ldap;

import com.gang.study.ldap.ldap.service.OrgOp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LdapApplicationTests {

    @Autowired
    private OrgOp orgOp;

    @Test
    void contextLoads() {
        orgOp.createOrg();
    }

}
