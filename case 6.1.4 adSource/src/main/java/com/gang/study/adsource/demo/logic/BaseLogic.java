package com.gang.study.adsource.demo.logic;

import com.gang.study.adsource.demo.common.TrustAllSocketFactory;
import com.gang.study.adsource.demo.to.ADConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.net.ssl.SSLSocketFactory;
import java.util.Hashtable;

/**
 * @Classname BaseLogic
 * @Description TODO
 * @Date 2020/2/19 10:16
 * @Created by zengzg
 */
@Component
public class BaseLogic {

    private static Logger LOG = LoggerFactory.getLogger(BaseLogic.class);

    private static final String LDAP_CTX_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    private static final String LDAP_CTX_SOCKET_FACTORY = "java.naming.ldap.factory.socket";
    private static final String LDAP_BINARY_ATTRIBUTE = "java.naming.ldap.attributes.binary";
    public static final String OBJECTGUID = "objectGUID";
    public static final String DNPARAMNAME = "distinguishedName";
    public static final String OBJECTSID = "objectSID";
    public static final String PRIMARYGROUPID = "primaryGroupID";
    public static final String MEMBEROF = "memberOf";
    public static final String UACCONTROL_ATTR = "userAccountControl";
    public static final String SDDL_ATTR = "ntSecurityDescriptor";

    private ADConfig config;

    //    public static void main(String[] args) {
    //        LOG.info("------> this is in main <-------");
    //        BaseLogic logic = new BaseLogic();
    //        logic.init();
    //        LdapContext ctx = logic.createLdapContext();
    //        LOG.info("------> this is contenxt :{} <-------", ctx);
    //        logic.checkAlive(ctx);
    //
    //    }

    public void init() {
        config = new ADConfig();
        config.setHost("127.0.0.1");
        config.setPort("46361");
    }


    public LdapContext createLdapContext() {

        final java.util.Hashtable<Object, Object> env = new java.util.Hashtable<Object, Object>();

        env.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_CTX_FACTORY);

        // 构建LDAP 访问地址
        env.put(Context.PROVIDER_URL, getLdapUrls());
        env.put(Context.REFERRAL, "follow");

        // 开启SSL / 信任密钥
        env.put(Context.SECURITY_PROTOCOL, "ssl");
        env.put(LDAP_CTX_SOCKET_FACTORY, TrustAllSocketFactory.class.getName());

        env.put(LDAP_BINARY_ATTRIBUTE,
                SDDL_ATTR + " " + OBJECTGUID + " " + OBJECTSID);
        // 访问当时 账号
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "administrator@wdhacpoc");
        //        GuardedString credentials =

        // 密码
        env.put(Context.SECURITY_CREDENTIALS, "19950824");
        LdapContext context = null;
        try {
            context = new InitialLdapContext(env, null);
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return context;
    }


    public Boolean checkAlive(LdapContext ctx) {
        try {
            final Attributes attrs = ctx.getAttributes("", new String[]{"subschemaSubentry"});
            attrs.get("subschemaSubentry");
        } catch (NamingException e) {
            LOG.error("E----> error :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }
        return Boolean.TRUE;
    }

    private String getLdapUrls() {
        final StringBuilder builder = new StringBuilder();

        builder.append("ldap://").
                append(config.getHost()).append(':').append(config.getPort());

        return builder.toString();
    }
}
