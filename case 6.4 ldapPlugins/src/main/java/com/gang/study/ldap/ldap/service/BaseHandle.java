package com.gang.study.ldap.ldap.service;

import net.tirasa.connid.bundles.ldap.LdapConfiguration;
import org.identityconnectors.common.security.GuardedString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * @Classname BaseHandle
 * @Description TODO
 * @Date 2019/12/2 18:43
 * @Created by zengzg
 */
@Component
public class BaseHandle {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public LdapConfiguration getConfiguration() {
        Properties PROP = new Properties();
        try {
            PROP.load(BaseHandle.class.getResourceAsStream("/ad.properties"));
        } catch (IOException e) {
            logger.info("------> this is in getConfiguration <-------");
        }
        LdapConfiguration ldapConfiguration = getSimpleConf(PROP);

        return ldapConfiguration;
    }

    protected static LdapConfiguration getSimpleConf(final Properties prop) {

        final LdapConfiguration configuration = new LdapConfiguration();

        configuration.setUidAttribute("sAMAccountName");
        configuration.setGidAttribute("sAMAccountName");
        // --> 开启 SSL
        //		configuration.setSsl(true);
        configuration.setSsl(false);

        configuration.setBaseContexts(prop.getProperty("baseContextToSynchronize"));
        configuration.setObjectClassesToSynchronize("user");

        configuration.setHost(prop.getProperty("host"));
        configuration.setPort(Integer.parseInt(prop.getProperty("port")));
        configuration.setAccountObjectClasses("top", "person", "organizationalPerson", "user");


        configuration.setBaseContextsToSynchronize(prop.getProperty("baseContextToSynchronize"));

        configuration.setPrincipal(prop.getProperty("principal"));

        configuration.setCredentials(new GuardedString(prop.getProperty("credentials").toCharArray()));

        // configuration.setMemberships(prop.getProperty("memberships").split(";"));


        // configuration.setDefaultOrganizationContainer(prop.getProperty("defaultOrganizationContainer"));
        // assertFalse(configuration.getMemberships() == null ||
        // configuration.getMemberships().length == 0);
        configuration.setUidAttribute(prop.getProperty("UidAttribute", "objectUID"));
        configuration.setGidAttribute(prop.getProperty("GidAttribute", "objectUID"));
        return configuration;
    }
}
