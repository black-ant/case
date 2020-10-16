package com.gang.ldap.spring.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.LdapProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.pool.factory.PoolingContextSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname LDAPConfig
 * @Description TODO
 * @Date 2020/10/15 17:51
 * @Created by zengzg
 */

@Configuration
@EnableConfigurationProperties(LdapProperties.class)
public class LdapConfig {
    @Autowired
    private LdapProperties properties;

    @Bean
    public LdapContextSource ldapContextSource() {
        LdapContextSource source = new LdapContextSource();
        source.setUserDn("cn=Directory Manager");
        source.setPassword("welcome1");
        source.setBase("ou=People,o=hisense.com,o=isp");
        source.setUrls(new String[]{"ldap://10.19.34.218:1389"});

        Map<String, String> env = new HashMap<>();
//        source.setPooled(Boolean.TRUE);
//                env.put("com.sun.jndi.ldap.connect.pool", "true");
//                env.put("com.sun.jndi.ldap.connect.pool.maxsize", "2000");
//                env.put("com.sun.jndi.ldap.connect.pool.prefsize", "200");
//                env.put("com.sun.jndi.ldap.connect.pool.timeout", "300000");
//                env.put("com.sun.jndi.ldap.connect.pool.initsize", "200");
        source.setBaseEnvironmentProperties(Collections.unmodifiableMap(env));
        return source;
    }

    @Bean
    public PoolingContextSource getPoolingContextSource() {
        PoolingContextSource contextSource = new PoolingContextSource();
        contextSource.setContextSource(ldapContextSource());
        contextSource.setMaxActive(1000);
        contextSource.setMaxTotal(2000);
        contextSource.setMaxIdle(500);
        contextSource.setMinIdle(200);
        contextSource.setMaxWait(20);
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(getPoolingContextSource());
    }
}
