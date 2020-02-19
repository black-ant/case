/**
 * Copyright (C) 2011 ConnId (connid-dev@googlegroups.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.tirasa.connid.bundles.ad.sync;

import static net.tirasa.connid.bundles.ad.ADConnector.OBJECTGUID;

import com.sun.jndi.ldap.ctl.DirSyncResponseControl;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;
import net.tirasa.adsddl.ntsd.controls.SDFlagsControl;
import net.tirasa.adsddl.ntsd.controls.DirSyncControl;
import net.tirasa.adsddl.ntsd.utils.GUID;
import net.tirasa.connid.bundles.ad.ADConfiguration;
import net.tirasa.connid.bundles.ad.ADConnection;
import net.tirasa.connid.bundles.ad.util.ADUtilities;
import net.tirasa.connid.bundles.ad.util.DeletedControl;
import net.tirasa.connid.bundles.ad.util.DirSyncUtils;
import net.tirasa.connid.bundles.ldap.search.LdapInternalSearch;
import org.identityconnectors.common.StringUtil;
import org.identityconnectors.common.logging.Log;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.OperationOptions;
import org.identityconnectors.framework.common.objects.SyncDelta;
import org.identityconnectors.framework.common.objects.SyncDeltaBuilder;
import org.identityconnectors.framework.common.objects.SyncDeltaType;
import org.identityconnectors.framework.common.objects.SyncResultsHandler;
import org.identityconnectors.framework.common.objects.SyncToken;
import org.identityconnectors.framework.common.objects.Uid;

/**
 * An implementation of the sync operation based on the DirSync protocol, for Active Directory.
 */
public class ADSyncStrategy {

    private static final Log LOG = Log.getLog(ADSyncStrategy.class);

    private final transient ADConnection conn;

    private transient SyncToken latestSyncToken;

    private final ADUtilities utils;

    public ADSyncStrategy(final ADConnection conn) {
        this.conn = conn;
        this.utils = new ADUtilities(conn);
    }

    private Set<SearchResult> search(
            final LdapContext ctx,
            final String filter,
            final SearchControls searchCtls,
            final boolean updateLastSyncToken) {

        final Set<SearchResult> result = new HashSet<SearchResult>();

        for (String baseContextDn : conn.getConfiguration().getBaseContextsToSynchronize()) {

            if (LOG.isOk()) {
                LOG.ok("Searching from " + baseContextDn);
            }

            try {
                final NamingEnumeration<SearchResult> answer = ctx.search(baseContextDn, filter, searchCtls);

                while (answer.hasMoreElements()) {
                    result.add(answer.nextElement());
                }

                if (updateLastSyncToken) {
                    final Control[] rspCtls = ctx.getResponseControls();

                    if (rspCtls != null) {
                        if (LOG.isOk()) {
                            LOG.ok("Response Controls: {0}", rspCtls.length);
                        }

                        for (Control rspCtl : rspCtls) {
                            if (rspCtl instanceof DirSyncResponseControl) {
                                DirSyncResponseControl dirSyncRspCtl = (DirSyncResponseControl) rspCtl;
                                latestSyncToken = new SyncToken(dirSyncRspCtl.getCookie());
                            }
                        }

                        if (LOG.isOk()) {
                            LOG.ok("Latest sync token set to {0}", latestSyncToken);
                        }
                    }
                }
            } catch (NamingException e) {
                LOG.error(e, "While searching base context {0} with filter {1} and search controls {2}",
                        baseContextDn, filter, searchCtls);
            }
        }

        return result;
    }

    public void sync(
            final SyncToken token,
            final SyncResultsHandler handler,
            final OperationOptions options,
            final ObjectClass oclass) {

        // -----------------------------------
        // Create basicLdapSearch control
        // -----------------------------------
        final SearchControls searchCtls = LdapInternalSearch.createDefaultSearchControls();

        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        searchCtls.setReturningAttributes(null);
        // -----------------------------------

        // -----------------------------------
        // Get Synchronization Context
        // -----------------------------------
        final LdapContext ctx;

        try {
            if (token == null
                    || token.getValue() == null
                    || !(token.getValue() instanceof byte[])
                    || ((byte[]) token.getValue()).length == 0) {

                if (LOG.isOk()) {
                    LOG.ok("Synchronization with empty token.");
                }

                ctx = conn.getSyncContext(new Control[] { new DirSyncControl() });

                if (((ADConfiguration) conn.getConfiguration()).isStartSyncFromToday()) {
                    search(ctx, "(cn=__CONNID-NORES__)", searchCtls, true);
                    return;
                }

            } else {
                if (LOG.isOk()) {
                    LOG.ok("Synchronization with token.");
                }

                ctx = conn.getSyncContext(new Control[] { new DirSyncControl((byte[]) token.getValue()) });
            }
        } catch (Exception e) {
            throw new ConnectorException("Could not set DirSync request controls", e);
        }
        // -----------------------------------

        // -----------------------------------
        // Create basicLdapSearch filter
        // -----------------------------------
        final String filter = oclass.is(ObjectClass.ACCOUNT_NAME)
                ? // get user filter
                DirSyncUtils.createDirSyncUFilter((ADConfiguration) conn.getConfiguration(), utils)
                : // get group filter
                DirSyncUtils.createDirSyncGFilter((ADConfiguration) conn.getConfiguration());

        if (LOG.isOk()) {
            LOG.ok("Search filter: " + filter);
        }
        // -----------------------------------

        final String[] attrsToGetOption = options.getAttributesToGet();
        final Set<String> attrsToGet = utils.getAttributesToGet(attrsToGetOption, oclass);

        final Set<SearchResult> changes = search(ctx, filter, searchCtls, true);

        int count = changes.size();

        if (oclass.is(ObjectClass.ACCOUNT_NAME)) {
            for (SearchResult sr : changes) {
                try {
                    handleSyncUDelta(ctx, sr, attrsToGet, count == 1 ? latestSyncToken : token, handler);
                    count--;
                } catch (NamingException e) {
                    LOG.error(e, "SyncDelta handling for '{0}' failed", sr.getName());
                }
            }
        } else {
            for (SearchResult sr : changes) {
                try {
                    handleSyncGDelta(ctx, sr, attrsToGet, count == 1 ? latestSyncToken : token, handler);
                    count--;
                } catch (NamingException e) {
                    LOG.error(e, "SyncDelta handling for '{0}' failed", sr.getName());
                }
            }
        }
    }

    public SyncToken getLatestSyncToken() {
        // -----------------------------------
        // Create basicLdapSearch control
        // -----------------------------------
        final SearchControls searchCtls = LdapInternalSearch.createDefaultSearchControls();
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchCtls.setReturningAttributes(null);
        // -----------------------------------

        final String baseContextDn = conn.getConfiguration().getBaseContextsToSynchronize()[0];
        final String filter = "(CN=__CONNID-NORES__)";

        try {
            final LdapContext ctx = conn.getSyncContext(new Control[] { new DirSyncControl() });
            ctx.search(baseContextDn, filter, searchCtls);

            final Control[] rspCtls = ctx.getResponseControls();

            if (rspCtls != null) {

                for (Control rspCtl : rspCtls) {
                    if (rspCtl instanceof DirSyncResponseControl) {
                        DirSyncResponseControl dirSyncRspCtl = (DirSyncResponseControl) rspCtl;
                        latestSyncToken = new SyncToken(dirSyncRspCtl.getCookie());
                    }
                }

                if (LOG.isOk()) {
                    LOG.ok("Latest sync token set to {0}", latestSyncToken);
                }
            }
        } catch (Exception e) {
            LOG.error(e, "While searching for {0} with filter {1} and controls {2}", baseContextDn, filter, searchCtls);
        }

        return latestSyncToken;
    }

    @SuppressWarnings("unchecked")
    private void handleSyncUDelta(
            final LdapContext ctx,
            final SearchResult result,
            final Collection<String> attrsToGet,
            final SyncToken token,
            final SyncResultsHandler handler)
            throws NamingException {

        if (ctx == null || result == null) {
            throw new ConnectorException("Invalid context or search result.");
        }

        ctx.setRequestControls(new Control[] { new DeletedControl(), new SDFlagsControl(0x00000004) });

        // Just used to retrieve object classes and to pass to getSyncDelta
        Attributes profile = result.getAttributes();

        if (LOG.isOk()) {
            LOG.ok("Object profile: {0}", profile);
        }

        final String guid = GUID.getGuidAsString((byte[]) profile.get("objectGUID").get());

        boolean isDeleted = false;

        try {

            javax.naming.directory.Attribute attributeIsDeleted = profile.get("isDeleted");

            isDeleted = attributeIsDeleted != null
                    && attributeIsDeleted.get() != null
                    && Boolean.parseBoolean(attributeIsDeleted.get().toString());

        } catch (NoSuchElementException e) {
            if (LOG.isOk()) {
                LOG.ok("Cannot find the isDeleted element for user.");
            }
        } catch (Throwable t) {
            LOG.error(t, "Error retrieving isDeleted attribute");
        }

        // We need for this beacause DirSync can return an uncomplete profile.
        profile = ctx.getAttributes("<GUID=" + guid + ">");

        final Attribute objectClasses = profile.get("objectClass");

        final ADConfiguration conf = (ADConfiguration) conn.getConfiguration();

        final javax.naming.directory.Attribute member11;
        final javax.naming.directory.Attribute member00;

        if (objectClasses.contains("group")) {
            // basicLdapSearch for users in adn users out

            if (LOG.isOk()) {
                LOG.ok("Modified group {0}", result.getNameInNamespace());
            }

            member11 = result.getAttributes().get("member;range=1-1");
            member00 = result.getAttributes().get("member;range=0-0");

            ctx.setRequestControls(null);

            if (member11 != null) {
                if (LOG.isOk()) {
                    LOG.ok("Found users 'IN' ...");
                }

                handleInOutEntries(
                        ctx,
                        ObjectClass.ACCOUNT,
                        (NamingEnumeration<String>) member11.getAll(),
                        DirSyncUtils.getUserFilter(conf),
                        handler,
                        token,
                        conf,
                        attrsToGet);
            }

            if (member00 != null && conf.isRetrieveDeletedUser()) {
                // users to be removed
                if (LOG.isOk()) {
                    LOG.ok("Found users 'OUT' ...");
                }

                handleInOutEntries(
                        ctx,
                        ObjectClass.ACCOUNT,
                        (NamingEnumeration<String>) member00.getAll(),
                        DirSyncUtils.getUserFilter(conf),
                        handler,
                        token,
                        conf,
                        attrsToGet);
            }
        } else if (objectClasses.contains("user")) {
            if (LOG.isOk()) {
                LOG.ok("Created/Updated/Deleted user {0}",
                        result.getNameInNamespace());
            }

            if (isDeleted) {

                if (LOG.isOk()) {
                    LOG.ok("Deleted user {0}", result.getNameInNamespace());
                }

                if (conf.isRetrieveDeletedUser()) {
                    handler.handle(getSyncDelta(
                            ObjectClass.ACCOUNT,
                            result.getNameInNamespace(),
                            SyncDeltaType.DELETE,
                            token,
                            profile,
                            attrsToGet));
                }

            } else {
                // user to be created/updated
                if (LOG.isOk()) {
                    LOG.ok("Created/Updated user {0}", result.getNameInNamespace());
                }

                handleEntry(
                        ctx,
                        ObjectClass.ACCOUNT,
                        result.getNameInNamespace(),
                        DirSyncUtils.getUserFilter(conf),
                        handler,
                        token,
                        conf,
                        attrsToGet);
            }
        } else {
            LOG.warn("Invalid object type {0}", objectClasses);
        }
    }

    @SuppressWarnings("unchecked")
    private void handleSyncGDelta(
            final LdapContext ctx,
            final SearchResult sr,
            final Collection<String> attrsToGet,
            final SyncToken token,
            final SyncResultsHandler handler)
            throws NamingException {

        if (ctx == null || sr == null) {
            throw new ConnectorException("Invalid context or search result.");
        }

        ctx.setRequestControls(new Control[] { new DeletedControl() });

        // Just used to retrieve object classes and to pass to getSyncDelta
        Attributes profile = sr.getAttributes();

        if (LOG.isOk()) {
            LOG.ok("Object profile: {0}", profile);
        }

        String guid = GUID.getGuidAsString((byte[]) profile.get(OBJECTGUID).get());

        boolean isDeleted = false;

        try {

            javax.naming.directory.Attribute attributeIsDeleted = profile.get("isDeleted");

            isDeleted = attributeIsDeleted != null
                    && attributeIsDeleted.get() != null
                    && Boolean.parseBoolean(
                            attributeIsDeleted.get().toString());

        } catch (NoSuchElementException e) {
            if (LOG.isOk()) {
                LOG.ok("Cannot find the isDeleted element for group.");
            }
        } catch (Throwable t) {
            LOG.error(t, "Error retrieving isDeleted attribute");
        }

        // We need for this beacause DirSync can return an uncomplete profile.
        profile = ctx.getAttributes("<GUID=" + guid + ">");

        final Attribute objectClasses = profile.get("objectClass");

        if (objectClasses.contains("group")) {
            final ADConfiguration conf = (ADConfiguration) conn.getConfiguration();

            if (LOG.isOk()) {
                LOG.ok("Created/Updated/Deleted group {0}", sr.getNameInNamespace());
            }

            if (isDeleted) {

                if (LOG.isOk()) {
                    LOG.ok("Deleted group {0}", sr.getNameInNamespace());
                }

                if (conf.isRetrieveDeletedGroup()) {
                    handler.handle(getSyncDelta(
                            ObjectClass.GROUP,
                            sr.getNameInNamespace(),
                            SyncDeltaType.DELETE,
                            token,
                            profile,
                            attrsToGet));
                }

            } else {
                // user to be created/updated
                if (LOG.isOk()) {
                    LOG.ok("Created/Updated group {0}", sr.getNameInNamespace());
                }

                String userDN = sr.getNameInNamespace();

                handleEntry(
                        ctx, ObjectClass.GROUP, userDN, conf.getGroupSearchFilter(), handler, token, conf, attrsToGet);

                final javax.naming.directory.Attribute member11 = sr.getAttributes().get("member;range=1-1");
                final javax.naming.directory.Attribute member00 = sr.getAttributes().get("member;range=0-0");

                ctx.setRequestControls(null);

                if (member11 != null) {
                    if (LOG.isOk()) {
                        LOG.ok("Found entry 'IN' ...");
                    }

                    handleInOutEntries(
                            ctx,
                            ObjectClass.GROUP,
                            (NamingEnumeration<String>) member11.getAll(),
                            "(&(objectclass=group)" + conf.getGroupSearchFilter() + ")",
                            handler,
                            token,
                            conf,
                            attrsToGet);
                }

                if (member00 != null) {
                    // users to be removed
                    if (LOG.isOk()) {
                        LOG.ok("Found entry 'OUT' ...");
                    }

                    handleInOutEntries(
                            ctx,
                            ObjectClass.GROUP,
                            (NamingEnumeration<String>) member00.getAll(),
                            "(&(objectclass=group)" + conf.getGroupSearchFilter() + ")",
                            handler,
                            token,
                            conf,
                            attrsToGet);
                }
            }
        } else {
            LOG.warn("Invalid object type {0}", objectClasses);
        }
    }

    private SyncDelta getSyncDelta(
            final ObjectClass oclass,
            final String entryDN,
            final SyncDeltaType syncDeltaType,
            final SyncToken token,
            final Attributes profile,
            final Collection<String> attrsToGet)
            throws NamingException {

        final SyncDeltaBuilder sdb = new SyncDeltaBuilder();

        // Set token
        sdb.setToken(token);

        // Set Delta Type
        sdb.setDeltaType(syncDeltaType);

        javax.naming.directory.Attribute uidAttribute;

        Uid uid = null;

        if (StringUtil.isNotBlank(conn.getSchemaMapping().getLdapUidAttribute(oclass))) {
            uidAttribute = profile.get(conn.getSchemaMapping().getLdapUidAttribute(oclass));

            if (uidAttribute != null) {
                uid = new Uid(uidAttribute.get().toString());
            }
        }

        if (uid == null) {
            throw new ConnectorException("UID attribute not found");
        }

        // Set UID
        sdb.setUid(uid);

        // Set Connector Object
        sdb.setObject(utils.createConnectorObject(entryDN, profile, attrsToGet, oclass));

        return sdb.build();
    }

    private void handleInOutEntries(
            final LdapContext ctx,
            final ObjectClass oclass,
            final NamingEnumeration<String> dns,
            final String filter,
            final SyncResultsHandler handler,
            final SyncToken token,
            final ADConfiguration conf,
            final Collection<String> attrsToGet)
            throws NamingException {

        while (dns.hasMoreElements()) {
            // for each new user "in" we must verify custom ldap filter
            handleEntry(ctx, oclass, dns.next(), filter, handler, token, conf, attrsToGet);
        }
    }

    private void handleEntry(
            final LdapContext ctx,
            final ObjectClass oclass,
            final String dn,
            final String filter,
            final SyncResultsHandler handler,
            final SyncToken token,
            final ADConfiguration conf,
            final Collection<String> attrsToGet)
            throws NamingException {

        final Attributes profile = ctx.getAttributes(dn);

        final Attribute objectClasses = profile.get("objectClass");

        if (oclass.is(ObjectClass.ACCOUNT_NAME) && !objectClasses.contains("user")
                || oclass.is(ObjectClass.GROUP_NAME) && !objectClasses.contains("group")) {
            LOG.warn("Invalid type: skip object {0}", dn);
            return;
        }

        final SyncDeltaType deltaType;

        if (DirSyncUtils.verifyFilter(ctx, dn, filter)) {
            if (LOG.isOk()) {
                LOG.ok("Entry {0} - update", dn);
            }

            deltaType = SyncDeltaType.CREATE_OR_UPDATE;

        } else {
            // new memberOf could invalidate the custom filter
            if (LOG.isOk()) {
                LOG.ok("Entry {0} - delete", dn);
            }

            deltaType = SyncDeltaType.DELETE;
        }

        if (deltaType != SyncDeltaType.DELETE
                || (oclass.is(ObjectClass.GROUP_NAME) && conf.isRetrieveDeletedGroup())
                || (oclass.is(ObjectClass.ACCOUNT_NAME) && conf.isRetrieveDeletedUser())) {

            handler.handle(getSyncDelta(
                    oclass,
                    dn,
                    deltaType,
                    token,
                    profile,
                    attrsToGet));
        }
    }
}
