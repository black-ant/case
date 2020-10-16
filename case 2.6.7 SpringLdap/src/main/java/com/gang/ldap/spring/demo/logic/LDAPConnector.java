package com.gang.ldap.spring.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.SortControl;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Properties;

/**
 * @Classname LdapJndiAuthLogic
 * @Description TODO
 * @Date 2020/10/16 15:39
 * @Created by zengzg
 */
public class LDAPConnector {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Logger for this class and subclasses
     */
    private static LDAPConnector instance;
    private String url;
    private String baseDN;
    private String bindDN;
    private String bindPassword;
    private final Hashtable<String, String> env = new Hashtable<String, String>();
    private final Control[] sortConnCtls = new SortControl[1];

    {
        try {
            sortConnCtls[0] = new SortControl("sAMAccountName", Control.CRITICAL);
        } catch (IOException ex) {
        }
    }

    private LDAPConnector() {
        try {
            url = "ldap://10.19.34.218:1389/";
            baseDN = "ou=People,o=hisense.com,o=isp";
            bindDN = "cn=Directory Manager";
            bindPassword = "welcome1";
            // set up environment for creating initial context
            env.put(Context.PROVIDER_URL, url + baseDN);
            env.put(Context.SECURITY_PRINCIPAL, bindDN);
            env.put(Context.SECURITY_CREDENTIALS, bindPassword);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put("java.naming.batchsize", "50");
            env.put("com.sun.jndi.ldap.connect.timeout", "3000");
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put("com.sun.jndi.ldap.connect.pool", "true");
            // the following pool parameters doesn't work
            // must setup as java init parameters
            env.put("com.sun.jndi.ldap.connect.pool.maxsize", "3");
            env.put("com.sun.jndi.ldap.connect.pool.prefsize", "1");
            env.put("com.sun.jndi.ldap.connect.pool.timeout", "300000");
            env.put("com.sun.jndi.ldap.connect.pool.initsize", "1");
            env.put("com.sun.jndi.ldap.connect.pool.authentication", "simple");

        } catch (Exception e) {
            // ignore error
            e.printStackTrace();
        }
    }

    public static LDAPConnector getInstance() {
        if (instance == null)
            instance = new LDAPConnector();
        return instance;
    }

    public boolean validateUser(String username, String password) {
        boolean passed = false;
        LdapContext dirContext = null;
        try {
            // create initial context
            dirContext = new InitialLdapContext(env, sortConnCtls);
            dirContext.setRequestControls(sortConnCtls);
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String filter = "(uid=" + username + ")";
            NamingEnumeration<?> answer = dirContext.search("", filter, controls);
            String userDN = null;
            while (answer.hasMore()) {
                userDN = ((NameClassPair) answer.nextElement()).getName();
            }
            // set up environment for creating initial context
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.PROVIDER_URL, url + baseDN);
            env.put(Context.SECURITY_PRINCIPAL, userDN + "," + baseDN);
            env.put(Context.SECURITY_CREDENTIALS, password);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put("com.sun.jndi.ldap.connect.timeout", "1000");
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

            // create initial context
            DirContext context = new InitialDirContext(env);
            passed = true;
            context.close();
        } catch (NamingException e) {
            // ignore error
            // e.printStackTrace();
        } finally {
            if (dirContext != null) {
                try {
                    dirContext.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }

        }
        return passed;
    }


    public boolean login(String username, String password) {
        Properties env = new Properties();
        String account = "(uid=" + username + ")";
        String ldapURL = "ldap://10.19.34.218:1389";
        env.put(Context.PROVIDER_URL, ldapURL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, username);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        //批量处理
        env.put(Context.BATCHSIZE, "50");
        // 连接超时设置
        env.put("com.sun.jndi.ldap.connect.timeout", "3000");
        // LDAP连接池
        env.put("com.sun.jndi.ldap.connect.pool", "true");
        // LDAP连接池最大数
        env.put("com.sun.jndi.ldap.connect.pool.maxsize", "3");
        // LDAP连接池优先数
        env.put("com.sun.jndi.ldap.connect.pool.prefsize", "1");
        // LDAP连接池超时
        env.put("com.sun.jndi.ldap.connect.pool.timeout", "300000");
        // LDAP连接池初始化数
        env.put("com.sun.jndi.ldap.connect.pool.initsize", "1");
        // LDAP连接池的认证方式
        env.put("com.sun.jndi.ldap.connect.pool.authentication", "simple");
        try {
            InitialLdapContext dc = new InitialLdapContext(env, null);
            logger.info("域用户" + username + " 登录" + account + "成功！");
            return true;
        } catch (Exception e) {
            logger.info("域用户" + username + " 登录" + account + "失败！");
            return false;
        }
    }
}
