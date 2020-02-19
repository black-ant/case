package com.gang.study.adplugin.common;

import net.tirasa.connid.bundles.ad.ADConfiguration;
import net.tirasa.connid.bundles.ad.ADConnection;
import net.tirasa.connid.bundles.ad.ADConnector;
import net.tirasa.connid.bundles.ldap.LdapConfiguration;
import net.tirasa.connid.bundles.ldap.LdapConnection;
import net.tirasa.connid.bundles.ldap.LdapConnector;
import org.identityconnectors.common.security.GuardedString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * @Classname BaseHandle
 * @Description TODO
 * @Date 2019/12/3 17:30
 * @Created by zengzg
 */
@Component
public class ADConfig implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private ADConnection adConnection;
    private ADConfiguration adConfiguration;
    private ADConnector adConnector;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        adConnector = new ADConnector();
        adConfiguration = getConfiguration();
        adConnection = new ADConnection(adConfiguration);
    }

    public ADConfiguration getConfiguration() {
        Properties PROP = new Properties();
        try {
            PROP.load(ADConfig.class.getResourceAsStream("/ad.properties"));
        } catch (IOException e) {
            logger.info("------> this is in getConfiguration <-------");
        }
        ADConfiguration ldapConfiguration = getConfiguration(PROP);

        return ldapConfiguration;
    }

    protected ADConfiguration getConfiguration(final Properties prop) {

        final ADConfiguration configuration = new ADConfiguration();

        configuration.setUidAttribute("sAMAccountName");
        configuration.setGidAttribute("sAMAccountName");
        // --> 开启 SSL
        //		configuration.setSsl(true);
        configuration.setSsl(false);

        //        configuration.setDefaultPeopleContainer( "CN=Users," +  );
        //        configuration.setDefaultGroupContainer("CN=Users," +  );
        configuration.setBaseContexts(prop.getProperty("baseContextToSynchronize"));
        configuration.setObjectClassesToSynchronize("user");
        configuration.setDefaultIdAttribute("cn");

        configuration.setHost(prop.getProperty("host"));
        configuration.setPort(Integer.parseInt(prop.getProperty("port")));
        configuration.setAccountObjectClasses("top", "person", "organizationalPerson", "user");


        //		configuration.setExchangeuser(prop.getProperty("exuser"));
        //		configuration.setExchangepassword(new GuardedString(prop.getProperty("excredentials").toCharArray()));
        //		configuration.setExchangeport(Integer.parseInt(prop.getProperty("export")));
        // 这个就是 host ,详见 ADExcgangeConnector (疲敝该项可间接避免创建 exconn 连接)
        //		configuration.setExchangeserip(prop.getProperty("exhost"));


        // 前缀属性RDN --> 属性中默认设置了
        //		configuration.setPrefixProperties("__ACCOUNT__:CN", "__GROUP__:OU", "__ORGANIZATION__:OU");
        configuration.setBaseContextsToSynchronize(prop.getProperty("baseContextToSynchronize"));

        String server4 = "000000";
        String[] serverArrays = {server4};

        // set default group container as Fgroup search context
        configuration.setGroupBaseContexts(configuration.getDefaultGroupContainer());

        configuration.setPrincipal(prop.getProperty("principal"));

        configuration.setCredentials(new GuardedString(prop.getProperty("credentials").toCharArray()));

        // configuration.setMemberships(prop.getProperty("memberships").split(";"));

        configuration.setRetrieveDeletedUser(false);
        // --> 信任所有证书
        configuration.setTrustAllCerts(true);

        configuration.setMembershipsInOr(true);

        configuration.setUserSearchScope("subtree");
        configuration.setGroupSearchScope("subtree");

        //        configuration.setGroupSearchFilter("(&(cn=GroupTest*)" + "(| (memberOf=CN=GroupTestInFilter,CN=Users,"
        //                + BASE_CONTEXT + ")(cn=GroupTestInFilter)))");

        // configuration.setDefaultOrganizationContainer(prop.getProperty("defaultOrganizationContainer"));
        // assertFalse(configuration.getMemberships() == null ||
        // configuration.getMemberships().length == 0);
        configuration.setUidAttribute(prop.getProperty("UidAttribute", "objectGUID"));
        configuration.setGidAttribute(prop.getProperty("GidAttribute", "objectGUID"));
        configuration.setOidAttribute(prop.getProperty("OidAttribute", "objectGUID"));
        //        configuration.setOrganiztionBaseContexts(prop.getProperty("organiztionBaseContexts", BASE_CONTEXT));
        //        configuration.setUserBaseContexts(prop.getProperty("organiztionBaseContexts", BASE_CONTEXT));
        //        configuration.setGroupBaseContexts(prop.getProperty("groupBaseContexts", BASE_CONTEXT));
        //        configuration.setOrganiztionBaseContexts(prop.getProperty("organiztionBaseContexts", BASE_CONTEXT));
        return configuration;
    }

    public ADConnection getAdConnection() {
        return adConnection;
    }

    public void setAdConnection(ADConnection adConnection) {
        this.adConnection = adConnection;
    }

    public ADConfiguration getAdConfiguration() {
        return adConfiguration;
    }

    public void setAdConfiguration(ADConfiguration adConfiguration) {
        this.adConfiguration = adConfiguration;
    }

    public ADConnector getAdConnector() {
        return adConnector;
    }

    public void setAdConnector(ADConnector adConnector) {
        this.adConnector = adConnector;
    }
}
