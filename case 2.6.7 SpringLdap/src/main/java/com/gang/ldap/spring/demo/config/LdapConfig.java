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
    @Autowired
    private Environment environment;

    @Bean
    public LdapContextSource ldapContextSource() {
        //        baseConfig.openLdapSystem("127.0.0.1", "6389", "cn=root,dc=test,dc=com", "123456")
        //                .closeLdapSSL()
        //                .ldapVersion(LDAPVersion.OPENLDAP)
        //                .setBaseContxt("cn=root,dc=test,dc=com");

        LdapContextSource source = new LdapContextSource();
        source.setUserDn("cn=root,dc=test,dc=com");
        source.setPassword("123456");
        source.setBase("cn=root,dc=test,dc=com");
        source.setUrls(new String[]{"ldap://192.168.158.149:389"});
        source.setBaseEnvironmentProperties(Collections.unmodifiableMap(properties.getBaseEnvironment()));
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
