/**
 * Copyright (C) 2011 ConnId (connid-dev@googlegroups.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.tirasa.connid.bundles.ad;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.tirasa.connid.bundles.ad.authentication.ADAuthenticate;
import net.tirasa.connid.bundles.ad.crud.ADCreate;
import net.tirasa.connid.bundles.ad.crud.ADDelete;
import net.tirasa.connid.bundles.ad.crud.ADUpdate;
import net.tirasa.connid.bundles.ad.search.ADSearch;
import net.tirasa.connid.bundles.ad.sync.ADSyncStrategy;
import net.tirasa.connid.bundles.ldap.LdapConnector;
import net.tirasa.connid.bundles.ldap.commons.LdapConstants;
import net.tirasa.connid.bundles.ldap.search.LdapFilter;
import org.identityconnectors.common.logging.Log;
import org.identityconnectors.common.security.GuardedString;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.AttributeBuilder;
import org.identityconnectors.framework.common.objects.AttributeUtil;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.OperationOptions;
import org.identityconnectors.framework.common.objects.OperationalAttributes;
import org.identityconnectors.framework.common.objects.ResultsHandler;
import org.identityconnectors.framework.common.objects.Schema;
import org.identityconnectors.framework.common.objects.SyncResultsHandler;
import org.identityconnectors.framework.common.objects.SyncToken;
import org.identityconnectors.framework.common.objects.Uid;
import org.identityconnectors.framework.spi.Configuration;
import org.identityconnectors.framework.spi.ConnectorClass;

/**
 * All-java, agent-less Active Directory connector, extending LDAP connector.
 *
 * @see org.identityconnectors.ldap.LdapConnector
 */
@ConnectorClass(configurationClass = ADConfiguration.class, displayNameKey = "ADConnector")
public class ADConnector extends LdapConnector {

    private static final Log LOG = Log.getLog(ADConnector.class);

    public static final String OBJECTGUID = "objectGUID";

    public static final String DNPARAMNAME = "distinguishedName";

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
     * The configuration for this connector instance.
     */
    private transient ADConfiguration config;

    /**
     * The relative DirSyncSyncStrategy instance which sync-related operations are delegated to.
     */
    private transient ADSyncStrategy syncStrategy;

    /**
     * The connection to the AD server.
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
