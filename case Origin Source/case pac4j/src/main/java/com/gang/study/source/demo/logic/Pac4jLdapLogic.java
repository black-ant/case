package com.gang.study.source.demo.logic;

import org.ldaptive.ConnectionConfig;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.auth.Authenticator;
import org.ldaptive.auth.FormatDnResolver;
import org.ldaptive.auth.PooledBindAuthenticationHandler;
import org.ldaptive.pool.BlockingConnectionPool;
import org.ldaptive.pool.IdlePruneStrategy;
import org.ldaptive.pool.PoolConfig;
import org.ldaptive.pool.PooledConnectionFactory;
import org.ldaptive.pool.SearchValidator;
import org.pac4j.ldap.profile.service.LdapProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @Classname Pac4jLdapLogic
 * @Description TODO
 * @Date 2020/7/8 16:09
 * @Created by zengzg
 */
@Component
public class Pac4jLdapLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------>  <-------");
        run("sysadmin");
    }

    public void run(String userDn) {
        // ldaptive:
        FormatDnResolver dnResolver = new FormatDnResolver();
        dnResolver.setFormat("CN=%s," + "OU=wuhan,DC=paraview,DC=in");


        DefaultConnectionFactory connectionFactory = new DefaultConnectionFactory();
        connectionFactory.setConnectionConfig(getConnectConfig());

        PoolConfig poolConfig = new PoolConfig();
        poolConfig.setMinPoolSize(1);
        poolConfig.setMaxPoolSize(2);
        poolConfig.setValidateOnCheckOut(true);
        poolConfig.setValidateOnCheckIn(true);
        poolConfig.setValidatePeriodically(false);

        SearchValidator searchValidator = new SearchValidator();
        IdlePruneStrategy pruneStrategy = new IdlePruneStrategy();
        BlockingConnectionPool connectionPool = new BlockingConnectionPool();

        connectionPool.setPoolConfig(poolConfig);
        connectionPool.setBlockWaitTime(Duration.ofSeconds(1000));
        connectionPool.setValidator(searchValidator);
        connectionPool.setPruneStrategy(pruneStrategy);
        connectionPool.setConnectionFactory(connectionFactory);
        connectionPool.initialize();

        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();

        pooledConnectionFactory.setConnectionPool(connectionPool);

        PooledBindAuthenticationHandler handler = new PooledBindAuthenticationHandler();

        handler.setConnectionFactory(pooledConnectionFactory);

        Authenticator ldaptiveAuthenticator = new Authenticator();
        ldaptiveAuthenticator.setDnResolver(dnResolver);
        ldaptiveAuthenticator.setAuthenticationHandler(handler);
        // pac4j:
        LdapProfileService ldapProfileService = new LdapProfileService(connectionFactory, ldaptiveAuthenticator, userDn);
        logger.info("------> this is service : {} <-------", ldapProfileService);

        ldapProfileService.init();
        ldapProfileService.
    }

    /**
     * 获取 ConnectionConfig
     *
     * @return
     */
    public ConnectionConfig getConnectConfig() {
        ConnectionConfig connectionConfig = new ConnectionConfig();
        connectionConfig.setConnectTimeout(Duration.ofSeconds(500));
        connectionConfig.setResponseTimeout(Duration.ofSeconds(1500));
        connectionConfig.setLdapUrl("ldap://192.168.7.15:" + "389");
        return connectionConfig;
    }

    //    public ConnectionFactory getDefaultConnectionFactory() {
    //        baseConfig.openLdapSystem("192.168.7.15", "389", "paraview\\administrator", "Parav1ew@2020")
    //                .closeLdapSSL()
    //                .ldapVersion(LDAPVersion.WINAD2012)
    //                .setBaseContxt("OU=wuhan,DC=paraview,DC=in");
    //    }
}
