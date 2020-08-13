package com.gang.study.ldapactivejdk11.demo.service;

import org.ldaptive.ConnectionConfig;
import org.ldaptive.Credential;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.LdapEntry;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchOperationHandle;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.auth.AuthenticationRequest;
import org.ldaptive.auth.AuthenticationResponse;
import org.ldaptive.auth.Authenticator;
import org.ldaptive.auth.SearchDnResolver;
import org.ldaptive.auth.SimpleBindAuthenticationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2020/8/12 16:47
 * @Created by zengzg
 */
@Component
public class StartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in run <-------");
        search();
        //        auth();
    }

    public void search() throws Exception {
        SearchOperation search = new SearchOperation(
                new DefaultConnectionFactory("ldap://192.168.1.1:389"), "OU=武汉研发,DC=d1,DC=com,DC=cn");
        SearchOperationHandle handle = search.send(SearchRequest.builder()
                .dn("OU=武汉研发,DC=d1,DC=com,DC=cn")
                .filter("(cn=Sync382)")
                .returnAttributes("uid")
                .build());

        SearchResponse response = handle.execute();

        logger.info("------> this is response :{} <-------", response);
        LdapEntry entry = response.getEntry();
        logger.info("------> this is entry :{} <-------", response.getDiagnosticMessage());
    }

    public void auth() throws Exception {
        ConnectionConfig connConfig = ConnectionConfig.builder()
                .url("ldap://127.0.0.1:389")
                .useStartTLS(false)
                .build();

        SearchDnResolver dnResolver = SearchDnResolver.builder()
                .factory(new DefaultConnectionFactory(connConfig))
                .dn("DC=devad,DC=com,DC=cn")
                .filter("uid={administrator}")
                .build();

        SimpleBindAuthenticationHandler authHandler = new SimpleBindAuthenticationHandler(new DefaultConnectionFactory(connConfig));
        Authenticator auth = new Authenticator(dnResolver, authHandler);
        AuthenticationResponse response = auth.authenticate(new AuthenticationRequest("administrator", new Credential("123456")));
        if (response.isSuccess()) {
            logger.info("------> auth success <-------");
            // authentication succeeded
        } else {
            logger.info("------> auth failure <-------");
            // authentication failed
        }
    }
}
