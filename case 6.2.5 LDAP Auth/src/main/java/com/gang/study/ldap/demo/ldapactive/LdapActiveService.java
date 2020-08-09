package com.gang.study.ldap.demo.ldapactive;

import com.gang.study.ldap.demo.utils.CollectionUtils;
import com.gang.study.ldap.demo.utils.LdapUtils;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.Response;
import org.ldaptive.SearchExecutor;
import org.ldaptive.SearchFilter;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResult;
import org.ldaptive.auth.SearchDnResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname LdapActiveService
 * @Description TODO
 * @Date 2020/8/8 22:54
 * @Created by zengzg
 */
@Component
public class LdapActiveService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SearchExecutor userSearchExecutor = new SearchExecutor();

    public void doSearchCas() {

        ConnectionFactory connection = new DefaultConnectionFactory("ldap://192.168.2.75:389");


        SearchFilter searchFilter = new SearchFilter();
        searchFilter.setFilter("(uid=administrator)");
        final SearchFilter filter = LdapUtils.newLdaptiveSearchFilter(searchFilter.getFilter(), LdapUtils.LDAP_SEARCH_FILTER_DEFAULT_PARAM_NAME, CollectionUtils.wrap("devad"));

        try {
            final Response<SearchResult> response = this.userSearchExecutor.search(connection, filter);
            logger.info("------> response :{} <-------", response);
        } catch (LdapException e) {
            e.printStackTrace();
        }
    }

    public void doSearch() {

        logger.info("------> this is in doSearch <-------");

        Connection connection = new DefaultConnectionFactory("ldap://192.168.2.75:389").getConnection();
        try {
            connection.open();
        } catch (LdapException e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            e.printStackTrace();
        }

        SearchOperation searchOperation = new SearchOperation(connection);

        SearchRequest request = new SearchRequest();
        request.setBaseDn("DC=devad,DC=com,DC=cn");

        SearchFilter searchFilter = new SearchFilter();
        searchFilter.setFilter("(uid=devad\\\\administrator)");
        request.setSearchFilter(searchFilter);

        try {
            Response response = searchOperation.execute(request);
            logger.info("------> this is response :{} <-------", response);
        } catch (LdapException e) {
            e.printStackTrace();
        }

    }

    public void doSearch1() {
        SearchDnResolver dnResolver = new SearchDnResolver();
        dnResolver.setConnectionFactory(new DefaultConnectionFactory(builderConfig()));
        dnResolver.setBaseDn("DC=devad,DC=com,DC=cn");
        dnResolver.setUserFilter("uid={user}");
    }

    public ConnectionConfig builderConfig() {
        ConnectionConfig connConfig = new ConnectionConfig();
        connConfig.setLdapUrl("ldap://directory.ldaptive.org");
        connConfig.setUseStartTLS(true);
        return connConfig;
    }


}
