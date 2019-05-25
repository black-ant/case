package com.gang.adbundles.demo.config;

import net.tirasa.connid.bundles.ad.ADConfiguration;
import net.tirasa.connid.bundles.ad.ADConnector;
import net.tirasa.connid.bundles.ad.search.ADDefaultSearchStrategy;
import net.tirasa.connid.bundles.ad.util.ADUtilities;
import net.tirasa.connid.bundles.ldap.LdapConfiguration;
import net.tirasa.connid.bundles.ldap.commons.LdapConstants;
import net.tirasa.connid.bundles.ldap.commons.ObjectClassMappingConfig;
import net.tirasa.connid.bundles.ldap.search.DefaultSearchStrategy;
import org.identityconnectors.common.CollectionUtil;
import org.identityconnectors.common.StringUtil;
import org.identityconnectors.common.security.GuardedString;
import org.identityconnectors.framework.api.APIConfiguration;
import org.identityconnectors.framework.api.ConnectorFacade;
import org.identityconnectors.framework.api.ConnectorFacadeFactory;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.spi.Configuration;
import org.identityconnectors.framework.spi.ConfigurationProperty;
import org.identityconnectors.framework.spi.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.constraints.AssertFalse;
import java.io.IOException;
import java.util.*;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/5/25 20:24
 * @Version 1.0
 **/
@Service
public abstract class AbstractADContext {

    private static Logger logger = LoggerFactory.getLogger(AbstractADContext.class);

    protected static ConnectorFacade connector;

    protected static ADConfiguration conf;

    protected static String BASE_CONTEXT;

    protected static final Properties PROP = new Properties();

    private static AbstractADContext instance;

    protected static void init() {

        logger.info("--> AD Configuration 进行初始化 :{}", new Date().getTime());
        try {
            PROP.load(AbstractADContext.class.getResourceAsStream("/ad.properties"));
        } catch (IOException e) {
            logger.error("Error loading properties file : {}", e);
        }

        BASE_CONTEXT = PROP.getProperty("baseContext");

        conf = getSimpleConf(PROP);

        Assert.isNull(conf, "conf is null");
        conf.validate();

        final ConnectorFacadeFactory factory = ConnectorFacadeFactory.getInstance();

        final APIConfiguration impl = createTestConfiguration(ADConnector.class, conf);
        impl.getResultsHandlerConfiguration().setFilteredResultsHandlerInValidationMode(true);

        connector = factory.newInstance(impl);

        Assert.isNull(connector, "connector is null");
        connector.test();
    }

    public static APIConfiguration createTestConfiguration(Class<? extends Connector> clazz, Configuration config) {
        return getSpi().createTestConfiguration(clazz, config);
    }

    private static synchronized AbstractADContext getSpi() {
        if (instance == null) {
            try {
                Class<?> clazz = Class.forName("org.identityconnectors.framework.impl.test.TestHelpersImpl");
                Object object = clazz.newInstance();
                instance = (AbstractADContext) AbstractADContext.class.cast(object);
            } catch (Exception var2) {
                throw ConnectorException.wrap(var2);
            }
        }

        return instance;
    }

    protected static ADConfiguration getSimpleConf(final Properties prop) {

        final ADConfiguration configuration = new ADConfiguration();

        configuration.setUidAttribute("sAMAccountName");
        configuration.setGidAttribute("sAMAccountName");
        // --> 开启 SSL
        configuration.setSsl(true);

        configuration.setDefaultPeopleContainer(/* "CN=Users," + */ BASE_CONTEXT);
        configuration.setDefaultGroupContainer(/* "CN=Users," + */ BASE_CONTEXT);
        configuration.setDefaultOrganizationContainer(BASE_CONTEXT);
        configuration.setBaseContexts(prop.getProperty("baseContextToSynchronize"));
        configuration.setObjectClassesToSynchronize("user");

        configuration.setHost(prop.getProperty("host"));
        configuration.setPort(Integer.parseInt(prop.getProperty("port")));
        configuration.setServerNumber(1);
        configuration.setGeneralServerCount(6);
        configuration.setAccountObjectClasses("top", "person", "organizationalPerson", "user");

        configuration.setIsvip(true);
        configuration.setVipServerCount(2);

        configuration.setExchangeuser(prop.getProperty("exuser"));
        configuration.setExchangepassword(new GuardedString(prop.getProperty("excredentials").toCharArray()));
        configuration.setExchangeport(Integer.parseInt(prop.getProperty("export")));
        // 这个就是 host ,详见 ADExcgangeConnector (疲敝该项可间接避免创建 exconn 连接)
        configuration.setExchangeserip(prop.getProperty("exhost"));

        // 前缀属性RDN --> 属性中默认设置了
//		configuration.setPrefixProperties("__ACCOUNT__:CN", "__GROUP__:OU", "__ORGANIZATION__:OU");
        configuration.setBaseContextsToSynchronize(prop.getProperty("baseContextToSynchronize"));

        configuration.setUserBaseContexts(BASE_CONTEXT);

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

        configuration.setGroupSearchFilter("(&(cn=GroupTest*)" + "(| (memberOf=CN=GroupTestInFilter,CN=Users,"
                + BASE_CONTEXT + ")(cn=GroupTestInFilter)))");

        // configuration.setDefaultOrganizationContainer(prop.getProperty("defaultOrganizationContainer"));
        // assertFalse(configuration.getMemberships() == null ||
        // configuration.getMemberships().length == 0);
        configuration.setUidAttribute(prop.getProperty("UidAttribute", OBJECTGUID));
        configuration.setGidAttribute(prop.getProperty("GidAttribute", OBJECTGUID));
        configuration.setOidAttribute(prop.getProperty("OidAttribute", OBJECTGUID));
        configuration.setOrganiztionBaseContexts(prop.getProperty("organiztionBaseContexts", BASE_CONTEXT));
        configuration.setUserBaseContexts(prop.getProperty("organiztionBaseContexts", BASE_CONTEXT));
        configuration.setGroupBaseContexts(prop.getProperty("groupBaseContexts", BASE_CONTEXT));
        configuration.setOrganiztionBaseContexts(prop.getProperty("organiztionBaseContexts", BASE_CONTEXT));
        return configuration;
    }

}
