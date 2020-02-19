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
package net.tirasa.connid.bundles.ad.search;

import static java.util.Collections.singletonList;
import static org.identityconnectors.common.StringUtil.isBlank;

import com.sun.jndi.ldap.ctl.VirtualListViewControl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.naming.InvalidNameException;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.PagedResultsControl;
import net.tirasa.adsddl.ntsd.utils.GUID;
import net.tirasa.adsddl.ntsd.utils.Hex;
import net.tirasa.connid.bundles.ad.ADConfiguration;
import net.tirasa.connid.bundles.ad.ADConnection;
import net.tirasa.connid.bundles.ad.ADConnector;
import net.tirasa.connid.bundles.ad.util.ADUtilities;
import net.tirasa.connid.bundles.ldap.LdapConnection;
import net.tirasa.connid.bundles.ldap.commons.LdapConstants;
import net.tirasa.connid.bundles.ldap.search.LdapFilter;
import net.tirasa.connid.bundles.ldap.search.LdapInternalSearch;
import net.tirasa.connid.bundles.ldap.search.LdapSearchResultsHandler;
import net.tirasa.connid.bundles.ldap.search.LdapSearchStrategy;
import net.tirasa.connid.bundles.ldap.search.LdapSearches;
import org.identityconnectors.common.logging.Log;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.OperationOptions;
import org.identityconnectors.framework.common.objects.QualifiedUid;
import org.identityconnectors.framework.common.objects.ResultsHandler;
import org.identityconnectors.framework.spi.SearchResultsHandler;

public class ADSearch {

    private final LdapConnection conn;

    private final ResultsHandler handler;

    private final ObjectClass oclass;

    private final LdapFilter filter;

    private final OperationOptions options;

    private final String[] baseDNs;

    private final ADUtilities utils;

    private static final Log LOG = Log.getLog(ADSearch.class);

    public ADSearch(
            final LdapConnection conn,
            final ObjectClass oclass,
            final LdapFilter filter,
            final ResultsHandler handler,
            final OperationOptions options,
            final String[] baseDNs) {

        this.conn = conn;
        this.oclass = oclass;
        this.filter = filter;
        this.handler = handler;
        this.options = options;
        this.baseDNs = baseDNs;

        this.utils = new ADUtilities((ADConnection) this.conn);
    }

    public ADSearch(
            final LdapConnection conn,
            final ObjectClass oclass,
            final LdapFilter filter,
            final ResultsHandler handler,
            final OperationOptions options) {

        this(conn, oclass, filter, handler, options,
                oclass.is(ObjectClass.ACCOUNT_NAME)
                ? ((ADConfiguration) conn.getConfiguration()).getUserBaseContexts()
                : oclass.is(ObjectClass.GROUP_NAME)
                ? ((ADConfiguration) conn.getConfiguration()).getGroupBaseContexts()
                : ((ADConfiguration) conn.getConfiguration()).getBaseContextsToSynchronize());
    }

    public final void executeADQuery(final ResultsHandler handler) {
        final String[] attrsToGetOption = options.getAttributesToGet();
        final Set<String> attrsToGet = utils.getAttributesToGet(attrsToGetOption, oclass);

        final LdapInternalSearch search = getInternalSearch(attrsToGet);

        search.execute(new LdapSearchResultsHandler() {

            @Override
            public boolean handle(final String baseDN, final SearchResult result)
                    throws NamingException {
                return handler.handle(utils.createConnectorObject(
                        result.getNameInNamespace(),
                        result,
                        attrsToGet,
                        oclass));
            }
        });
    }

    private LdapInternalSearch getInternalSearch(final Set<String> attrsToGet) {
        // This is a bit tricky. If the LdapFilter has an entry DN,
        // we only need to look at that entry and check whether it matches
        // the native filter. Moreover, when looking at the entry DN
        // we must not throw exceptions if the entry DN does not exist or is
        // not valid -- just as no exceptions are thrown when the native
        // filter doesn't return any values.
        //
        // In the simple case when the LdapFilter has no entryDN, we
        // will just basicLdapSearch over our base DNs looking for entries
        // matching the native filter.

        LdapSearchStrategy strategy;
        List<String> dns;
        int searchScope;

        final String filterEntryDN = filter == null ? null : filter.getEntryDN();

        if (filterEntryDN == null) {
            strategy = getSearchStrategy();
            dns = getBaseDNs();
            searchScope = getLdapSearchScope();
        } else {
            // Would be good to check that filterEntryDN is under the configured 
            // base contexts. However, the adapter is likely to pass entries
            // outside the base contexts, so not checking in order to be on the
            // safe side.
            strategy = conn.getConfiguration().newDefaultSearchStrategy(true);

            try {
                dns = buildBaseContextFilter(filterEntryDN);
            } catch (InvalidNameException e) {
                LOG.error(e, "Error building entry DN filter starting from '{0}'", filterEntryDN);
                dns = getBaseDNs();
            }

            searchScope = SearchControls.OBJECT_SCOPE;
        }

        final SearchControls controls = LdapInternalSearch.createDefaultSearchControls();
        final Set<String> ldapAttrsToGet = utils.getLdapAttributesToGet(attrsToGet, oclass);

        controls.setReturningAttributes(ldapAttrsToGet.toArray(new String[ldapAttrsToGet.size()]));
        controls.setSearchScope(searchScope);

        final String optionsFilter = LdapConstants.getSearchFilter(options);

        if (LOG.isOk()) {
            LOG.ok("Options filter: {0} " + optionsFilter);
        }

        final String searchFilter = oclass.equals(ObjectClass.ACCOUNT)
                ? conn.getConfiguration().getAccountSearchFilter()
                : ((ADConfiguration) conn.getConfiguration()).getGroupSearchFilter();

        if (LOG.isOk()) {
            LOG.ok("Search filter: {0} " + searchFilter);
        }

        final String nativeFilter = filter != null ? filter.getNativeFilter() : null;

        if (LOG.isOk()) {
            LOG.ok("Native filter: {0} " + nativeFilter);
        }

        final String membershipSearchFilter = oclass.equals(ObjectClass.ACCOUNT)
                ? utils.getMembershipSearchFilter(((ADConfiguration) conn.getConfiguration()))
                : null;

        if (LOG.isOk()) {
            LOG.ok("Membership filter: {0} " + membershipSearchFilter);
        }

        return new LdapInternalSearch(
                conn,
                getSearchFilter(optionsFilter, nativeFilter, searchFilter, membershipSearchFilter),
                dns,
                strategy,
                controls);
    }

    /**
     * Accept DNs filter provided by CN only or prefix only or full DN.
     *
     * @param filterEntryDN provided entry DN filter.
     * @return base context filter.
     */
    private List<String> buildBaseContextFilter(final String filterEntryDN) throws InvalidNameException {
        final List<String> res = new ArrayList<String>();

        LdapName prefix;

        try {
            prefix = new LdapName(filterEntryDN);
        } catch (InvalidNameException ine) {
            LOG.info(ine, "'{0}' is not am entry DN. Let's try derive it", filterEntryDN);
            prefix = new LdapName(String.format("CN=%s", filterEntryDN));
        }

        for (String dn : getBaseDNs()) {
            final LdapName suffix = new LdapName(dn);

            if (prefix.startsWith(suffix)) {
                return Collections.singletonList(prefix.toString());
            }

            res.add(suffix.addAll(prefix).toString());
        }

        return res;
    }

    private String getSearchFilter(final String... optionalFilters) {
        final StringBuilder builder = new StringBuilder();
        final String ocFilter = getObjectClassFilter();
        int nonBlank = isBlank(ocFilter) ? 0 : 1;
        for (String optionalFilter : optionalFilters) {
            nonBlank += (isBlank(optionalFilter) ? 0 : 1);
        }
        if (nonBlank > 1) {
            builder.append("(&");
        }
        appendFilter(ocFilter, builder);
        for (String optionalFilter : optionalFilters) {
            appendFilter(optionalFilter, builder);
        }
        if (nonBlank > 1) {
            builder.append(')');
        }

        // replace any substring like as objectGUID=ba36c308-792a-45a9-b374-7f330e9742ab with the correct query
        final String res = builder.toString();

        final String resToLowerCase = res.toLowerCase(); // required to be case-insensitive
        final String toBeFound = ADConnector.OBJECTGUID.toLowerCase();

        final StringBuilder bld = new StringBuilder();

        int from;
        int to = 0;

        do {
            from = resToLowerCase.indexOf(toBeFound, to);
            if (from >= 0) {
                from += 11;
                bld.append(res.substring(to, from));
                to += from + 36;
                bld.append(Hex.getEscaped(GUID.getGuidAsByteArray(res.substring(from, to))));
            } else {
                bld.append(res.substring(to, res.length()));
            }
        } while (from >= 0 && to < res.length());

        return bld.toString();
    }

    private LdapSearchStrategy getSearchStrategy() {
        final LdapSearchStrategy result;

        if (options.getPageSize() != null) {
            if (conn.getConfiguration().isUseVlvControls() && conn.supportsControl(VirtualListViewControl.OID)) {
                String vlvSortAttr = conn.getConfiguration().getVlvSortAttribute();
                result = new ADVlvIndexSearchStrategy(vlvSortAttr, options.getPageSize());
            } else if (conn.supportsControl(PagedResultsControl.OID)) {
                result = new ADPagedSearchStrategy(
                        options.getPageSize(),
                        options.getPagedResultsCookie(),
                        options.getPagedResultsOffset(),
                        handler instanceof SearchResultsHandler ? (SearchResultsHandler) handler : null,
                        options.getSortKeys()
                );
            } else {
                result = conn.getConfiguration().newDefaultSearchStrategy(true);
            }
        } else {
            result = conn.getConfiguration().newDefaultSearchStrategy(true);
        }
        return result;
    }

    private static void appendFilter(String filter, StringBuilder toBuilder) {
        if (!isBlank(filter)) {
            final String trimmedUserFilter = filter.trim();
            final boolean enclose = filter.charAt(0) != '(';
            if (enclose) {
                toBuilder.append('(');
            }
            toBuilder.append(trimmedUserFilter);
            if (enclose) {
                toBuilder.append(')');
            }
        }
    }

    private List<String> getBaseDNs() {
        List<String> result;
        QualifiedUid container = options.getContainer();
        if (container != null) {
            result = singletonList(LdapSearches.findEntryDN(
                    conn, container.getObjectClass(), container.getUid()));
        } else {
            result = Arrays.asList(baseDNs);
        }
        assert result != null;
        return result;
    }

    private String getObjectClassFilter() {
        StringBuilder builder = new StringBuilder();
        List<String> ldapClasses = conn.getSchemaMapping().getLdapClasses(oclass);
        boolean and = ldapClasses.size() > 1;
        if (and) {
            builder.append("(&");
        }
        for (String ldapClass : ldapClasses) {
            builder.append("(objectClass=");
            builder.append(ldapClass);
            builder.append(')');
        }
        if (and) {
            builder.append(')');
        }
        return builder.toString();
    }

    private int getLdapSearchScope() {
        String scope = options.getScope();

        if (scope == null) {
            if (oclass.is(ObjectClass.ACCOUNT_NAME)) {
                scope = ((ADConfiguration) conn.getConfiguration()).getUserSearchScope();
            } else {
                scope = ((ADConfiguration) conn.getConfiguration()).getGroupSearchScope();
            }
        }

        if (OperationOptions.SCOPE_OBJECT.equals(scope)) {
            return SearchControls.OBJECT_SCOPE;
        } else if (OperationOptions.SCOPE_ONE_LEVEL.equals(scope)) {
            return SearchControls.ONELEVEL_SCOPE;
        } else if (OperationOptions.SCOPE_SUBTREE.equals(scope) || scope == null) {
            return SearchControls.SUBTREE_SCOPE;
        } else {
            throw new IllegalArgumentException("Invalid search scope " + scope);
        }
    }
}
