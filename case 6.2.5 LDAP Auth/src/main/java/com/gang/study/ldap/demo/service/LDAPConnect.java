package com.gang.study.ldap.demo.service;

import com.gang.study.ldap.demo.exception.LdapException;
import com.gang.study.ldap.demo.to.DefaultProperties;
import com.gang.study.ldap.demo.to.LDAPConfig;
import com.gang.study.ldap.demo.to.LdapAuthType;
import com.gang.study.ldap.demo.utils.ADUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * @Classname LDAPConnect
 * @Description TODO
 * @Date 2020/8/7 21:59
 * @Created by zengzg
 */
public class LDAPConnect {

    private static Logger LOG = LoggerFactory.getLogger(LDAPConnect.class);


    /**
     * 生成连接器环境信息
     *
     * @param config
     * @return
     */
    public static LdapContext createLdapContext(LDAPConfig config) {

        final java.util.Hashtable<Object, Object> env = new java.util.Hashtable<Object, Object>();

        env.put(Context.INITIAL_CONTEXT_FACTORY, DefaultProperties.LDAP_CTX_FACTORY);

        // 开启SSL / 信任密钥
        if (config.getOpenSSL()) {
            env.put(Context.SECURITY_PROTOCOL, "ssl");
            env.put(DefaultProperties.LDAP_CTX_SOCKET_FACTORY, TrustAllSocketFactory.class.getName());
        }

        // 构建LDAP 访问地址
        env.put(Context.PROVIDER_URL, getLdapUrls(config));
        env.put(Context.REFERRAL, "follow");


        env.put(DefaultProperties.LDAP_BINARY_ATTRIBUTE,
                DefaultProperties.SDDL_ATTR + " " + DefaultProperties.OBJECTGUID + " " + DefaultProperties.OBJECTSID);

        // 访问当时 账号 / 密码
        if (LdapAuthType.NONE.equals(config.getAuthType())) {
            env.put(Context.SECURITY_AUTHENTICATION, LdapAuthType.NONE.getCode());
            //            env.put(Context.PROVIDER_URL, env.get(Context.PROVIDER_URL) + "/" + config.getAccount());
            env.put(Context.PROVIDER_URL, env.get(Context.PROVIDER_URL));
        } else if (LdapAuthType.STRONG.equals(config.getAuthType())) {
            throw new LdapException("System Error , not support LdapAuthType : Strong");
        } else {
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, config.getAccount());
            env.put(Context.SECURITY_CREDENTIALS, config.getPassword());
        }

        // 超时时间
        env.put("com.sun.jndi.ldap.connect.timeout", Long.toString(config.getConnectTimeout()));
        env.put("com.sun.jndi.ldap.read.timeout", Long.toString(config.getReadTimeout()));

        LdapContext context = null;
        try {
            context = new InitialLdapContext(env, null);
        } catch (NamingException e) {
            LOG.error("------> Init Context Error ,Please Check You Config <-------");
        } catch (Exception e) {
            LOG.error("------> Init Context Error ,Please Check You Config  :{}<-------",
                    e.getClass() + e.getMessage());
            throw new LdapException("System Error , Obvious error in Config");
        }
        if (context == null) {
            throw new LdapException("Create LdapContext failed, Obvious error in Config");
        }
        return context;
    }


    /**
     * 获取LDAP 连接地址
     *
     * @param config
     * @return
     */
    private static String getLdapUrls(LDAPConfig config) {
        final StringBuilder builder = new StringBuilder();
        if (LdapAuthType.NONE.equals(config.getAuthType())) {
            if (StringUtils.isEmpty(config.getBaseContxt())) {
                throw new LdapException("LDAP No BaseContext");
            }
            builder.append("ldap://").append(config.getHost()).append(':').append(config.getPort());
        } else {
            builder.append("ldap://").append(config.getHost()).append(':').append(config.getPort());
        }
        return builder.toString();
    }
}
