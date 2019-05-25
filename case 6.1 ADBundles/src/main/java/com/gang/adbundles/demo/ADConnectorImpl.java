package com.gang.adbundles.demo;

import com.gang.adbundles.demo.config.ADConfigurationImpl;
import net.tirasa.adsddl.ntsd.controls.SDFlagsControl;
import net.tirasa.connid.bundles.ad.ADConfiguration;
import net.tirasa.connid.bundles.ad.ADConnection;
import net.tirasa.connid.bundles.ad.ADConnector;
import net.tirasa.connid.bundles.ad.authentication.ADAuthenticate;
import net.tirasa.connid.bundles.ad.crud.ADCreate;
import net.tirasa.connid.bundles.ad.crud.ADDelete;
import net.tirasa.connid.bundles.ad.crud.ADUpdate;
import net.tirasa.connid.bundles.ad.search.ADSearch;
import net.tirasa.connid.bundles.ad.sync.ADSyncStrategy;
import net.tirasa.connid.bundles.ldap.commons.LdapConstants;
import net.tirasa.connid.bundles.ldap.search.LdapFilter;
import org.identityconnectors.common.security.GuardedString;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.common.objects.*;
import org.identityconnectors.framework.spi.ConnectorClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/5/25 19:40
 * @Version 1.0
 **/

@ConnectorClass(configurationClass = ADConfiguration.class, displayNameKey = "ADConnector")
@Service
@ApplicationScope
public class ADConnectorImpl extends ADConnector {

    /**
     * 配置类
     */
    private transient ADConfiguration config;

    /**
     * 异步处理类
     */
    private transient ADSyncStrategy syncStrategy;

    /**
     * 连接器类
     */
    private transient ADConnection conn;

    public void start(){
        ADConfigurationImpl.init();
    }

    @Override
    public void executeQuery(
            final ObjectClass oclass,
            final LdapFilter query,
            final ResultsHandler handler,
            final OperationOptions options) {
        new ADSearch(conn, oclass, query, handler, options).executeADQuery(handler);
    }

    @Override
    public Uid create(
            final ObjectClass oclass,
            final Set<Attribute> attrs,
            final OperationOptions options) {

        if (ADConfiguration.class.cast(conn.getConfiguration()).isPwdUpdateOnly()) {
            throw new IllegalStateException("Create operation not permitted");
        }

        final Set<Attribute> attributes = new HashSet<Attribute>(attrs);

        if (oclass.is(ObjectClass.ACCOUNT_NAME)) {
            final Attribute ldapGroups = AttributeUtil.find(LdapConstants.LDAP_GROUPS_NAME, attributes);

            final Set<String> ldapGroupsToBeAdded = new HashSet<String>();

            if (ldapGroups != null) {
                attributes.remove(ldapGroups);
                ldapGroupsToBeAdded.addAll(ldapGroups.getValue() == null
                        ? Collections.<String>emptyList()
                        : Arrays.asList(ldapGroups.getValue().toArray(new String[ldapGroups.getValue().size()])));
            }

            ldapGroupsToBeAdded.addAll(config.getMemberships() == null
                    ? Collections.<String>emptyList() : Arrays.asList(config.getMemberships()));

            // add groups
            attributes.add(AttributeBuilder.build("ldapGroups", ldapGroupsToBeAdded));
        }

        return new ADCreate(conn, oclass, attributes, options).create();
    }

    @Override
    public Uid update(
            final ObjectClass oclass,
            final Uid uid,
            final Set<Attribute> attrs,
            final OperationOptions options) {

        final Set<Attribute> attributes = new HashSet<Attribute>();

        if (ADConfiguration.class.cast(conn.getConfiguration()).isPwdUpdateOnly()) {
            final Attribute pwd = AttributeUtil.find(OperationalAttributes.PASSWORD_NAME, attrs);
            if (pwd != null) {
                attributes.add(pwd);
            }
        } else {
            attributes.addAll(attrs);
            final Attribute ldapGroups = AttributeUtil.find(LdapConstants.LDAP_GROUPS_NAME, attributes);

            if (ldapGroups != null && oclass.is(ObjectClass.ACCOUNT_NAME)) {
                attributes.remove(ldapGroups);

                final Set<String> ldapGroupsToBeAdded = new HashSet<String>(
                        ldapGroups.getValue() == null
                                ? Collections.<String>emptyList()
                                : Arrays.asList(ldapGroups.getValue().toArray(
                                new String[ldapGroups.getValue().size()])));

                ldapGroupsToBeAdded.addAll(config.getMemberships() == null
                        ? Collections.<String>emptyList() : Arrays.asList(config.getMemberships()));

                // add groups
                attributes.add(AttributeBuilder.build("ldapGroups", ldapGroupsToBeAdded));
            }
        }

        return new ADUpdate(conn, oclass, uid).update(attributes);
    }

    @Override
    public void delete(
            final ObjectClass oclass,
            final Uid uid,
            final OperationOptions options) {

        if (ADConfiguration.class.cast(conn.getConfiguration()).isPwdUpdateOnly()) {
            throw new IllegalStateException("Delete operation not permitted");
        }

        new ADDelete(conn, oclass, uid).delete();
    }

    @Override
    public Schema schema() {
        return conn.getADSchema().getSchema();
    }

    @Override
    public Uid authenticate(
            final ObjectClass objectClass,
            final String username,
            final GuardedString password,
            final OperationOptions options) {

        return new ADAuthenticate(conn, objectClass, username, options).authenticate(password);
    }

    @Override
    public Uid resolveUsername(
            final ObjectClass objectClass,
            final String username,
            final OperationOptions options) {

        return new ADAuthenticate(conn, objectClass, username, options).resolveUsername();
    }

    @Override
    public void test() {
        conn.test();
    }

    @Override
    public void checkAlive() {
        conn.checkAlive();
    }

}
