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
package net.tirasa.connid.bundles.ad.crud;

import static net.tirasa.connid.bundles.ad.ADConnector.OBJECTGUID;

import java.util.Set;
import java.util.TreeSet;
import javax.naming.NamingException;

import net.tirasa.connid.bundles.ad.ADConnection;
import net.tirasa.connid.bundles.ldap.commons.LdapModifyOperation;
import net.tirasa.connid.bundles.ldap.search.LdapSearches;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.Uid;

public class ADDelete extends LdapModifyOperation {

    private final ObjectClass oclass;

    private final Uid uid;

    @SuppressWarnings("FieldNameHidesFieldInSuperclass")
    private final ADConnection conn;

    public ADDelete(final ADConnection conn, final ObjectClass oclass, final Uid uid) {
        super(conn);
        this.oclass = oclass;
        this.uid = uid;
        this.conn = conn;
    }

    public void delete() {
        final String entryDN;
        if (OBJECTGUID.equals(conn.getSchemaMapping().getLdapUidAttribute(oclass))) {
            entryDN = String.format("<GUID=%s>", uid.getUidValue());
        } else {
            entryDN = LdapSearches.getEntryDN(conn, oclass, uid);
        }

        final Set<String> ldapGroups = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        ldapGroups.addAll(groupHelper.getLdapGroups(entryDN));

        groupHelper.removeLdapGroupMemberships(entryDN, ldapGroups);

        try {
            conn.getInitialContext().destroySubcontext(entryDN);
        } catch (NamingException e) {
            throw new ConnectorException(e);
        }
    }
}
