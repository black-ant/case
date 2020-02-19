package com.gang.study.adtest.demo.output;

import com.gang.study.adtest.demo.to.ADConfigInfoTO;
import com.gang.study.adtest.demo.to.AbstractBaseBean;
import com.gang.study.adtest.demo.utils.ADSyncUtils;
import lombok.Data;
import net.tirasa.connid.bundles.ad.ADConfiguration;
import net.tirasa.connid.bundles.ad.ADConnection;
import net.tirasa.connid.bundles.ad.ADConnector;
import net.tirasa.connid.bundles.ad.crud.ADCreate;
import net.tirasa.connid.bundles.ad.crud.ADDelete;
import net.tirasa.connid.bundles.ad.crud.ADUpdate;
import net.tirasa.connid.bundles.ad.search.ADSearch;
import net.tirasa.connid.bundles.ad.util.ADUtilities;
import org.identityconnectors.common.security.GuardedString;
import org.identityconnectors.framework.common.objects.ConnectorObject;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.ResultsHandler;
import org.identityconnectors.framework.common.objects.Uid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname ADBaseConfig
 * @Description TODO
 * @Date 2019/12/5 17:00
 * @Created by zengzg
 */
@Component
@Data
public class ADBaseInfo {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private ADConnection adConnection;
    private ADConfiguration adConfiguration;
    private ADConnector adConnector;
    private ADUtilities utils;

    private ADSearch adSearch;
    private ADCreate adCreate;
    private ADUpdate adUpdate;
    private ADDelete adDelete;

    private final static Integer MAX_POOL_CONNECT = 5;
    private static Map<Integer, ADBaseInfo> connectMap = new HashMap<>();
    private static Map<Integer, ADBaseInfo> connectMapNew = new HashMap<>();

    public ADBaseInfo init(ADConfigInfoTO prop) {

        ADConfiguration configItem = getConfiguration(prop);

        // TODO : NEED
        refushConnectPool(configItem.toString().hashCode());

        if (!connectMap.isEmpty() && connectMap.containsKey(configItem.toString().hashCode())) {
            return connectMap.get(adConfiguration.toString().hashCode());
        }

        adConnector = new ADConnector();
        adConfiguration = configItem;
        adConnection = new ADConnection(adConfiguration);
        this.utils = new ADUtilities((ADConnection) this.adConnection);
        connectMap.put(configItem.toString().hashCode(), this);
        return this;
    }

    /**
     * 刷新连接池
     *
     * @param mapKey
     */
    public void refushConnectPool(Integer mapKey) {

        if (!connectMapNew.containsKey(mapKey)) {
            connectMapNew.put(mapKey, connectMap.get(mapKey));
        }

        if (connectMapNew.size() >= MAX_POOL_CONNECT) {
            connectMap = connectMapNew;
            connectMapNew = new HashMap<>();
        }
    }


    protected ADConfiguration getConfiguration(final ADConfigInfoTO prop) {

        final ADConfiguration configuration = new ADConfiguration();

        // --> connect Info
        configuration.setHost(prop.getHost());
        configuration.setPort(Integer.parseInt(prop.getPort()));
        configuration.setPrincipal(prop.getAccount());
        configuration.setCredentials(new GuardedString(prop.getPassword().toCharArray()));

        // -->  SSL Info
        configuration.setTrustAllCerts(true);
        configuration.setSsl(prop.getIsSSL());

        // --> base info
        configuration.setBaseContexts(prop.getBaseContext());
        configuration.setBaseContextsToSynchronize(prop.getBaseContext());

        configuration.setUidAttribute("sAMAccountName");
        configuration.setGidAttribute("sAMAccountName");
        configuration.setOidAttribute("objectGUID");

        //        configuration.setDefaultPeopleContainer( "CN=Users," +  );
        //        configuration.setDefaultGroupContainer("CN=Users," +  );

        configuration.setObjectClassesToSynchronize("user");
        configuration.setDefaultIdAttribute("cn");


        configuration.setAccountObjectClasses("top", "person", "organizationalPerson", "user");


        configuration.setGroupBaseContexts(configuration.getDefaultGroupContainer());


        configuration.setRetrieveDeletedUser(false);


        configuration.setMembershipsInOr(true);

        configuration.setUserSearchScope("subtree");
        configuration.setGroupSearchScope("subtree");


        configuration.setUidAttribute(prop.getUidAttribute());
        configuration.setGidAttribute(prop.getGidAttribute());
        configuration.setOidAttribute(prop.getOidAttribute());

        //    configuration.setExchangeuser(prop.getProperty("exuser"));
        //    configuration.setExchangepassword(new GuardedString(prop.getProperty("excredentials").toCharArray()));
        //    configuration.setExchangeport(Integer.parseInt(prop.getProperty("export")));
        // 这个就是 host ,详见 ADExcgangeConnector (疲敝该项可间接避免创建 exconn 连接)
        //    configuration.setExchangeserip(prop.getProperty("exhost"));


        // 前缀属性RDN --> 属性中默认设置了
        //    configuration.setPrefixProperties("__ACCOUNT__:CN", "__GROUP__:OU", "__ORGANIZATION__:OU");
        // configuration.setMemberships(prop.getProperty("memberships").split(";"));
        //        configuration.setGroupSearchFilter("(&(cn=GroupTest*)" + "(| (memberOf=CN=GroupTestInFilter,CN=Users,"
        //                + BASE_CONTEXT + ")(cn=GroupTestInFilter)))");

        // configuration.setDefaultOrganizationContainer(prop.getProperty("defaultOrganizationContainer"));
        // assertFalse(configuration.getMemberships() == null ||
        // configuration.getMemberships().length == 0);
        //        configuration.setOrganiztionBaseContexts(prop.getProperty("organiztionBaseContexts", BASE_CONTEXT));
        //        configuration.setUserBaseContexts(prop.getProperty("organiztionBaseContexts", BASE_CONTEXT));
        //        configuration.setGroupBaseContexts(prop.getProperty("groupBaseContexts", BASE_CONTEXT));
        //        configuration.setOrganiztionBaseContexts(prop.getProperty("organiztionBaseContexts", BASE_CONTEXT));
        return configuration;
    }


    static class BaseSearchResult implements ResultsHandler {

        private static Logger LOG = LoggerFactory.getLogger(BaseSearchResult.class);

        private ObjectClass objectClass;

        public BaseSearchResult(ObjectClass objectClass) {
            this.objectClass = objectClass;
        }

        @Override
        public boolean handle(ConnectorObject connectorObject) {
            LOG.info("------> connectorObject :{} <-------", connectorObject.getAttributes());
            //            ADSearchUtils.getBaseTOList(objectClass, connectorObject);
            return Boolean.TRUE;
        }
    }

    public ADSearch getAdSearch() {
        return adSearch;
    }


    public ADCreate getAdCreate(ObjectClass objectClass, AbstractBaseBean adObjectTO) {
        return new ADCreate(adConnection, objectClass, ADSyncUtils.getAttributeInfo(adObjectTO), null);
    }


    public ADUpdate getAdUpdate(ObjectClass objectClass, String adObjectTOKey) {
        return new ADUpdate(adConnection, objectClass, new Uid(adObjectTOKey));
    }


    public ADDelete getAdDelete(ObjectClass objectClass, String adObjectTOKey) {
        return new ADDelete(adConnection, objectClass, new Uid(adObjectTOKey));
    }

}

