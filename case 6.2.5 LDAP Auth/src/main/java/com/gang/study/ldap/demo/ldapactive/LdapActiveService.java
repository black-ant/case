package com.gang.study.ldap.demo.ldapactive;

/**
 * @Classname LdapActiveService
 * @Description TODO
 * @Date 2020/8/8 22:54
 * @Created by zengzg
 */
public class LdapActiveService {

    public void doSearch() {

        ConnectionConfig connConfig = ConnectionConfig.builder()
                .url("ldap://directory.ldaptive.org")
                .useStartTLS(true)
                .build();

        SearchDnResolver dnResolver = SearchDnResolver.builder()
                .factory(new DefaultConnectionFactory(connConfig))
                .dn("ou=people,dc=ldaptive,dc=org")
                .filter("uid={user}")
                .build();
    }
}
