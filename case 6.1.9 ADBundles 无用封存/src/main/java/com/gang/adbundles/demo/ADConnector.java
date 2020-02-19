package com.gang.adbundles.demo;

import com.gang.adbundles.demo.authentication.ADAuthenticate;
import com.gang.adbundles.demo.config.ADConfiguration;
import com.gang.adbundles.demo.crud.ADCreate;
import com.gang.adbundles.demo.crud.ADDelete;
import com.gang.adbundles.demo.crud.ADUpdate;
import com.gang.adbundles.demo.search.ADSearch;
import com.gang.adbundles.demo.sync.ADSyncStrategy;
import net.tirasa.connid.bundles.ldap.LdapConfiguration;
import net.tirasa.connid.bundles.ldap.LdapConnector;
import net.tirasa.connid.bundles.ldap.commons.LdapConstants;
import net.tirasa.connid.bundles.ldap.search.LdapFilter;
import org.identityconnectors.common.security.GuardedString;
import org.identityconnectors.framework.common.objects.*;
import org.identityconnectors.framework.spi.Configuration;
import org.identityconnectors.framework.spi.ConnectorClass;


import java.util.*;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/5/25 19:40
 * @Version 1.0
 **/

@ConnectorClass(configurationClass = ADConfiguration.class, displayNameKey = "ADConnector")
public class ADConnector extends LdapConnector {

    public static final String OBJECTGUID = "objectGUID";

    public static final String OBJECTSID = "objectSID";

    public static final String PRIMARYGROUPID = "primaryGroupID";

    public static final String MEMBEROF = "memberOf";

    public static final String UACCONTROL_ATTR = "userAccountControl";

    public static final String SDDL_ATTR = "ntSecurityDescriptor";

    // These attributes have to be removed for ADDS 2012
    public final static List<String> ADDS2012_ATTRIBUTES_TO_BE_REMOVED = Arrays.asList(
            "msds-memberOfTransitive", "msDS-parentdistname", "msds-memberTransitive");

    //some useful constants from lmaccess.h
    public static final int UF_ACCOUNTDISABLE = 0x0002;

    public static final int UF_PASSWD_NOTREQD = 0x0020;

    public static final int UF_PASSWD_CANT_CHANGE = 0x0040;

    public static final int UF_NORMAL_ACCOUNT = 0x0200;

    public static final int UF_DONT_EXPIRE_PASSWD = 0x10000;

    public static final int UF_PASSWORD_EXPIRED = 0x800000;

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

    @Override
    public Configuration getConfiguration() {
        return config;
    }

    @Override
    public void init(final Configuration cfg) {

        config = (ADConfiguration) cfg;

        // TODO: easier and more efficient if conn was protected in superclass
        conn = new ADConnection(config);

        syncStrategy = new ADSyncStrategy(conn);
        super.init(cfg);
    }

    @Override
    public void dispose() {
        conn.close();
        super.dispose();
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
    public SyncToken getLatestSyncToken(final ObjectClass oclass) {
        return syncStrategy.getLatestSyncToken();
    }

    @Override
    public void sync(final ObjectClass oclass, final SyncToken token,
                     final SyncResultsHandler handler, final OperationOptions options) {

        syncStrategy.sync(token, handler, options, oclass);
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
