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
package net.tirasa.connid.bundles.ad.util;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import net.tirasa.connid.bundles.ad.ADConfiguration;
import net.tirasa.connid.bundles.ad.ADConnector;
import net.tirasa.connid.bundles.ldap.search.LdapInternalSearch;
import org.identityconnectors.common.StringUtil;
import org.identityconnectors.common.logging.Log;
import org.identityconnectors.framework.spi.AbstractConfiguration;

public class DirSyncUtils {

    private static final Log LOG = Log.getLog(DirSyncUtils.class);

    public static String createDirSyncUFilter(final ADConfiguration conf, final ADUtilities utils) {

        final String isDeleted = String.valueOf(conf.isRetrieveDeletedUser()).toUpperCase();

        final StringBuilder filter = new StringBuilder();
        final StringBuilder mfilter = new StringBuilder();
        final StringBuilder ufilter = new StringBuilder();

        mfilter.append("(objectClass=group)");

        ufilter.append(utils.getMembershipSearchFilter(conf));

        ufilter.insert(0, "(&(objectClass=user)").append(")");

        filter.append("(|").append(ufilter).append(mfilter).
                append("(&(isDeleted=").append(isDeleted).append(")(objectClass=user)))");

        return filter.toString();
    }

    public static String createDirSyncGFilter(final ADConfiguration conf) {

        final StringBuilder filter = new StringBuilder();

        if (conf.isRetrieveDeletedGroup()) {
            filter.append("(objectClass=group)");
        } else {
            filter.append("(&(objectClass=group)(! (isDeleted=TRUE)))");
        }

        return filter.toString();
    }

    public static String createLdapUFilter(final ADConfiguration conf) {

        final String[] memberships = conf.getMemberships();

        final String isDeleted = String.valueOf(conf.isRetrieveDeletedUser()).toUpperCase();

        final StringBuilder filter = new StringBuilder();
        final StringBuilder mfilter = new StringBuilder();
        final StringBuilder ufilter = new StringBuilder();

        if (memberships != null && memberships.length > 0) {
            mfilter.append("(&(objectClass=group)(|");
            ufilter.append(conf.isMembershipsInOr() ? "(|" : "(&");

            for (String group : memberships) {
                mfilter.append("(distinguishedName=").append(group).append(")");
                ufilter.append("(").append(ADConnector.MEMBEROF).append("=").append(group).append(")");
            }

            ufilter.append(")");
            mfilter.append("))");
        }

        ufilter.insert(0, "(&(objectClass=user)").append(")");

        filter.append("(|").append(ufilter).append(mfilter).
                append("(&(isDeleted=").append(isDeleted).append(")(objectClass=user)))");

        return filter.toString();
    }

    /**
     * Verify custom filter (used to validate any retrieved user).
     *
     * @param ctx ldap context.
     * @param dn user distinguished name.
     * @param conf connector configuration.
     * @return TRUE if verified; FALSE otherwise.
     */
    public static boolean verifyCustomFilter(
            final LdapContext ctx,
            final String dn,
            final ADConfiguration conf) {
        return verifyFilter(ctx, dn, getFilter(conf));
    }

    /**
     * Verify complete filter including the custom one. This method is used to validate users 'IN' group.
     *
     * @param conf connector configuration.
     * @return TRUE if verified; FALSE otherwise.
     */
    public static String getUserFilter(final ADConfiguration conf) {

        final StringBuilder filter = new StringBuilder();
        filter.append("(&(").append(createLdapUFilter(conf)).append(")");

        filter.append(getFilter(conf) != null ? getFilter(conf) : "").append(")");

        return filter.toString();
    }

    public static boolean verifyFilter(
            final LdapContext ctx,
            final String dn,
            final String filter) {

        final SearchControls searchCtls = LdapInternalSearch.createDefaultSearchControls();

        searchCtls.setSearchScope(SearchControls.OBJECT_SCOPE);
        searchCtls.setReturningAttributes(new String[] {});

        boolean found = true;

        if (StringUtil.isNotBlank(filter)) {
            try {
                final NamingEnumeration<SearchResult> res = ctx.search(dn, filter, searchCtls);
                found = res != null && res.hasMoreElements();
            } catch (NamingException ex) {
                LOG.warn(ex, "Error searching for {0}", filter);
                found = false;
            }
        }

        return found;
    }

    private static String getFilter(final AbstractConfiguration conf) {
        return ((ADConfiguration) conf).getAccountSearchFilter();
    }
}
