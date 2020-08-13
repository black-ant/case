package com.gang.study.ldap.demo.service;

import com.gang.study.ldap.demo.ldapactive.LdapAuthService;
import com.gang.study.ldap.demo.ldapactive.LdapNoneAuthService;
import com.gang.study.ldap.demo.to.LDAPConfig;
import com.gang.study.ldap.demo.to.LdapAuthType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2020/8/7 22:15
 * @Created by zengzg
 */
@Component
public class StartController implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //    @Autowired
    //    private LdapActiveService ldapActiveService;

    @Autowired
    private LdapAuthService ldapAuthService;

    @Autowired
    private LdapNoneAuthService ldapNoneAuthService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        //        test();
        //        test2();
        //        ldapAuthService.auth();
        doAuthNone();
        //        ldapNoneAuthService.run();

    }

    public void doAuthNone() throws Exception {
        // Set up environment for creating initial context
        Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://192.168.2.75:389/");
        env.put(Context.SECURITY_PRINCIPAL, "DC=devad,DC=com,DC=cn");
        env.put(Context.REFERRAL, "follow");
        env.put(Context.SECURITY_AUTHENTICATION, "none");

        // Create initial context
        DirContext ctx = new InitialDirContext(env);
        logger.info("------> ctx :{} <-------", ctx);
        ctx.bind("CN=Administrator,CN=Users,DC=devad,DC=com,DC=cn", ctx);

        NamingEnumeration<NameClassPair> list = ctx.list("Sync382");
        logger.info("------> list :{} <-------", list);
    }

    public void test2() {
        //        ldapActiveService.doSearch();
        //        ldapActiveService.doSearchCas();
    }


    public void test() throws Exception {
        // LDAP Simple
        //        LDAPConfig config = new LDAPConfig("192.168.2.11", "389", "devad\\administrator", "11", Boolean.FALSE);

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


        NamingEnumeration<NameClassPair> name = context.list("administrator");
        logger.info("------> name :{} <-------", name);
    }
}
