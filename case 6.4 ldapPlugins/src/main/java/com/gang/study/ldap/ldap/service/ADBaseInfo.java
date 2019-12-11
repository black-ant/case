package com.gang.study.ldap.ldap.service;

import com.gang.study.ldap.ldap.to.ADConfigInfoTO;
import com.gang.study.ldap.ldap.to.AbstractBaseBean;
import com.gang.study.ldap.ldap.utils.ADSyncUtils;
import lombok.Data;
import net.tirasa.connid.bundles.ldap.LdapConfiguration;
import net.tirasa.connid.bundles.ldap.LdapConnection;
import net.tirasa.connid.bundles.ldap.LdapConnector;
import net.tirasa.connid.bundles.ldap.modify.LdapCreate;
import net.tirasa.connid.bundles.ldap.modify.LdapDelete;
import net.tirasa.connid.bundles.ldap.modify.LdapUpdate;
import net.tirasa.connid.bundles.ldap.search.LdapSearch;
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


    private LdapConnection adConnection;
    private LdapConfiguration adConfiguration;
    private LdapConnector adConnector;
    //    private LdapUtilities utils;

    private LdapSearch adSearch;
    private LdapCreate adCreate;
    private LdapUpdate adUpdate;
    private LdapDelete adDelete;

    private final static Integer MAX_POOL_CONNECT = 5;
    private static Map<Integer, ADBaseInfo> connectMap = new HashMap<>();
    private static Map<Integer, ADBaseInfo> connectMapNew = new HashMap<>();

    public ADBaseInfo init(ADConfigInfoTO prop) {

        LdapConfiguration configItem = getConfiguration(prop);

        // TODO : NEED
        refushConnectPool(configItem.toString().hashCode());

        if (!connectMap.isEmpty() && connectMap.containsKey(configItem.toString().hashCode())) {
            return connectMap.get(adConfiguration.toString().hashCode());
        }

        adConnector = new LdapConnector();
        adConfiguration = configItem;
        adConnection = new LdapConnection(adConfiguration);
        //        this.utils = new ADUtilities((LdapConnection) this.adConnection);
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


    protected LdapConfiguration getConfiguration(final ADConfigInfoTO prop) {

        final LdapConfiguration configuration = new LdapConfiguration();

        // --> connect Info
        configuration.setHost(prop.getHost());
        configuration.setPort(Integer.parseInt(prop.getPort()));
        configuration.setPrincipal(prop.getAccount());
        configuration.setCredentials(new GuardedString(prop.getPassword().toCharArray()));

        // -->  SSL Info
        configuration.setSsl(prop.getIsSSL());

        // --> base info
        configuration.setBaseContexts(prop.getBaseContext());
        configuration.setBaseContextsToSynchronize(prop.getBaseContext());

        configuration.setUidAttribute("sAMAccountName");
        configuration.setGidAttribute("sAMAccountName");

        configuration.setObjectClassesToSynchronize("user");


        configuration.setAccountObjectClasses("top", "person", "organizationalPerson", "user");

        configuration.setUidAttribute(prop.getUidAttribute());
        configuration.setGidAttribute(prop.getGidAttribute());
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

    public LdapSearch getAdSearch() {
        return adSearch;
    }


    public LdapCreate getAdCreate(ObjectClass objectClass, AbstractBaseBean adObjectTO) {
        return new LdapCreate(adConnection, objectClass, ADSyncUtils.getAttributeInfo(adObjectTO), null);
    }


    public LdapUpdate getAdUpdate(ObjectClass objectClass, String adObjectTOKey) {
        return new LdapUpdate(adConnection, objectClass, new Uid(adObjectTOKey));
    }


    public LdapDelete getAdDelete(ObjectClass objectClass, String adObjectTOKey) {
        return new LdapDelete(adConnection, objectClass, new Uid(adObjectTOKey));
    }

}

