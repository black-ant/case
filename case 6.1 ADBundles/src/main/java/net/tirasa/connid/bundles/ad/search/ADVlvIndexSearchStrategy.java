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

import static org.identityconnectors.common.StringUtil.isNotBlank;

import com.sun.jndi.ldap.ctl.VirtualListViewControl;
import com.sun.jndi.ldap.ctl.VirtualListViewResponseControl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.SortControl;
import javax.naming.ldap.SortResponseControl;
import net.tirasa.connid.bundles.ldap.search.LdapSearchResultsHandler;
import net.tirasa.connid.bundles.ldap.search.VlvIndexSearchStrategy;
import org.identityconnectors.common.logging.Log;

public class ADVlvIndexSearchStrategy extends VlvIndexSearchStrategy {

    private static final Log LOG = Log.getLog(ADVlvIndexSearchStrategy.class);

    private final String vlvIndexAttr;

    private final int blockSize;

    private int index;

    private int lastListSize;

    private byte[] cookie;

    static String searchControlsToString(SearchControls controls) {
        StringBuilder builder = new StringBuilder();
        builder.append("SearchControls: {returningAttributes=");
        String[] attrs = controls.getReturningAttributes();
        builder.append(attrs != null ? Arrays.asList(attrs) : "null");
        builder.append(", scope=");
        switch (controls.getSearchScope()) {
            case SearchControls.OBJECT_SCOPE:
                builder.append("OBJECT");
                break;
            case SearchControls.ONELEVEL_SCOPE:
                builder.append("ONELEVEL");
                break;
            case SearchControls.SUBTREE_SCOPE:
                builder.append("SUBTREE");
                break;
        }
        builder.append('}');
        return builder.toString();
    }

    public ADVlvIndexSearchStrategy(String vlvSortAttr, int blockSize) {
        super(vlvSortAttr, blockSize);
        this.vlvIndexAttr = isNotBlank(vlvSortAttr) ? vlvSortAttr : "uid";
        this.blockSize = blockSize;
    }

    @Override
    public void doSearch(final LdapContext initCtx,
            final List<String> baseDNs,
            final String query,
            final SearchControls searchControls,
            final LdapSearchResultsHandler handler)
            throws IOException, NamingException {

        if (LOG.isOk()) {
            LOG.ok("Searching in {0} with filter {1} and {2}",
                    baseDNs, query, searchControlsToString(searchControls));
        }

        Iterator<String> baseDNIter = baseDNs.iterator();
        boolean proceed = true;

        LdapContext ctx = initCtx.newInstance(null);
        try {
            while (baseDNIter.hasNext() && proceed) {
                proceed = searchBaseDN(ctx, baseDNIter.next(), query, searchControls, handler);
            }
        } finally {
            ctx.close();
        }
    }

    private boolean searchBaseDN(
            final LdapContext ctx,
            final String baseDN,
            final String query,
            final SearchControls searchControls,
            final LdapSearchResultsHandler handler)
            throws IOException, NamingException {

        if (LOG.isOk()) {
            LOG.ok("Searching in {0}", baseDN);
        }

        index = 1;
        lastListSize = 0;
        cookie = null;

        String lastResultName = null;

        for (;;) {
            SortControl sortControl = new SortControl(vlvIndexAttr, Control.CRITICAL);

            int afterCount = blockSize - 1;
            VirtualListViewControl vlvControl = new VirtualListViewControl(
                    index, lastListSize, 0, afterCount, Control.CRITICAL);
            vlvControl.setContextID(cookie);

            if (LOG.isOk()) {
                LOG.ok("New search: target = {0}, afterCount = {1}",
                        index, afterCount);
            }

            ctx.setRequestControls(new Control[] { sortControl, vlvControl });

            // Need to process the response controls, which are available after
            // all results have been processed, before sending anything to the caller
            // (because processing the response controls might throw exceptions that
            // invalidate anything we might have sent otherwise).
            // So storing the results before actually sending them to the handler.
            List<SearchResult> resultList = new ArrayList<SearchResult>(blockSize);

            NamingEnumeration<SearchResult> results = ctx.search(baseDN, query, searchControls);
            try {
                // hasMore call for referral resolution ... it fails with AD
                // while (results.hasMore()) {
                while (results.hasMoreElements()) {
                    SearchResult result = results.next();

                    boolean overlap = false;
                    if (lastResultName != null) {
                        if (lastResultName.equals(result.getName())) {
                            LOG.warn(
                                    "Working around rounding error overlap at "
                                    + "index " + index);
                            overlap = true;
                        }
                        lastResultName = null;
                    }

                    if (!overlap) {
                        resultList.add(result);
                    }
                }
            } finally {
                results.close();
            }

            processResponseControls(ctx.getResponseControls());

            SearchResult result = null;
            Iterator<SearchResult> resultIter = resultList.iterator();
            while (resultIter.hasNext()) {
                result = resultIter.next();
                index++;
                if (!handler.handle(baseDN, result)) {
                    return false;
                }
            }
            if (result != null) {
                lastResultName = result.getName();
            }

            if (index > lastListSize) {
                break;
            }

            // DSEE seems to only have a single VLV index (although it claims to support more).
            // It returns at the server content count the sum of sizes of all indexes,
            // but it only returns the entries in the base context we are asking for.
            // So, in this case, index will never reach lastListSize. To avoid an infinite loop,
            // ending search if we received no results in the last iteration.
            if (resultList.isEmpty()) {
                LOG.warn("Ending search because received no results");
                break;
            }
        }
        return true;
    }

    private void processResponseControls(final Control[] controls)
            throws NamingException {
        if (controls != null) {
            for (Control control : controls) {
                if (control instanceof SortResponseControl) {
                    SortResponseControl sortControl = (SortResponseControl) control;
                    if (!sortControl.isSorted()
                            || (sortControl.getResultCode() != 0)) {
                        throw sortControl.getException();
                    }
                }
                if (control instanceof VirtualListViewResponseControl) {
                    VirtualListViewResponseControl vlvControl = (VirtualListViewResponseControl) control;
                    if (vlvControl.getResultCode() == 0) {
                        lastListSize = vlvControl.getListSize();
                        cookie = vlvControl.getContextID();

                        if (LOG.isOk()) {
                            LOG.ok("Response control: lastListSize = {0}",
                                    lastListSize);
                        }
                    } else {
                        throw vlvControl.getException();
                    }
                }
            }
        }
    }
}
