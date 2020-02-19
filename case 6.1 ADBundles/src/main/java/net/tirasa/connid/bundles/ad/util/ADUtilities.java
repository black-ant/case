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

import static net.tirasa.connid.bundles.ad.ADConfiguration.PNE_FLAG;
import static net.tirasa.connid.bundles.ad.ADConfiguration.UCCP_FLAG;
import static net.tirasa.connid.bundles.ad.ADConnector.OBJECTGUID;
import static net.tirasa.connid.bundles.ad.ADConnector.OBJECTSID;
import static net.tirasa.connid.bundles.ad.ADConnector.PRIMARYGROUPID;
import static net.tirasa.connid.bundles.ad.ADConnector.SDDL_ATTR;
import static net.tirasa.connid.bundles.ad.ADConnector.UACCONTROL_ATTR;
import static net.tirasa.connid.bundles.ad.ADConnector.UF_ACCOUNTDISABLE;
import static net.tirasa.connid.bundles.ad.ADConnector.ADDS2012_ATTRIBUTES_TO_BE_REMOVED;
import static net.tirasa.connid.bundles.ad.ADConnector.UF_DONT_EXPIRE_PASSWD;
import static net.tirasa.connid.bundles.ldap.commons.LdapUtil.escapeAttrValue;
import static org.identityconnectors.common.CollectionUtil.newCaseInsensitiveSet;
import static org.identityconnectors.common.CollectionUtil.newSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.naming.InvalidNameException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.LdapName;
import net.tirasa.adsddl.ntsd.SDDL;
import net.tirasa.adsddl.ntsd.SID;
import net.tirasa.adsddl.ntsd.utils.GUID;
import net.tirasa.adsddl.ntsd.utils.Hex;
import net.tirasa.adsddl.ntsd.utils.NumberFacility;
import net.tirasa.adsddl.ntsd.utils.SDDLHelper;
import net.tirasa.connid.bundles.ad.ADConfiguration;
import net.tirasa.connid.bundles.ad.ADConnection;
import net.tirasa.connid.bundles.ad.ADConnector;
import net.tirasa.connid.bundles.ldap.LdapConnection;
import net.tirasa.connid.bundles.ldap.commons.GroupHelper;
import net.tirasa.connid.bundles.ldap.commons.LdapConstants;
import net.tirasa.connid.bundles.ldap.commons.LdapEntry;
import net.tirasa.connid.bundles.ldap.commons.LdapUtil;
import net.tirasa.connid.bundles.ldap.schema.LdapSchemaMapping;
import net.tirasa.connid.bundles.ldap.search.LdapFilter;
import net.tirasa.connid.bundles.ldap.search.LdapInternalSearch;
import net.tirasa.connid.bundles.ldap.search.LdapSearches;
import org.identityconnectors.common.CollectionUtil;
import org.identityconnectors.common.StringUtil;
import org.identityconnectors.common.logging.Log;
import org.identityconnectors.common.security.GuardedString;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.AttributeBuilder;
import org.identityconnectors.framework.common.objects.AttributeInfo;
import org.identityconnectors.framework.common.objects.ConnectorObject;
import org.identityconnectors.framework.common.objects.ConnectorObjectBuilder;
import org.identityconnectors.framework.common.objects.Name;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.ObjectClassInfo;
import org.identityconnectors.framework.common.objects.OperationalAttributes;
import org.identityconnectors.framework.common.objects.Uid;

public class ADUtilities {

    private final static Log LOG = Log.getLog(ADUtilities.class);

    private final ADConnection connection;

    private final GroupHelper groupHelper;

    public ADUtilities(final ADConnection connection) {
        this.connection = connection;
        groupHelper = new GroupHelper(connection);
    }

    public static SID getPrimaryGroupSID(final SID sid, final byte[] pgID) {
        final SID pgSID = SID.newInstance(sid.getIdentifierAuthority());
        pgSID.setRevision(sid.getRevision());

        final List<byte[]> subAuthorities = sid.getSubAuthorities();

        if (subAuthorities != null && !subAuthorities.isEmpty()) {
            for (int i = 0; i < subAuthorities.size() - 1; i++) {
                pgSID.addSubAuthority(subAuthorities.get(i));
            }
        }

        pgSID.addSubAuthority(pgID);

        return pgSID;
    }

    public javax.naming.directory.Attribute getGroupID(final String dn) throws InvalidNameException {
        try {
            final LdapName name = new LdapName(dn);
            final Attributes group = connection.getInitialContext().getAttributes(name, new String[] { OBJECTSID });
            final SID gsid = SID.parse((byte[]) group.get(OBJECTSID).get());
            final byte[] groupID = gsid.getSubAuthorities().get(gsid.getSubAuthorityCount() - 1);
            return new BasicAttribute(PRIMARYGROUPID, String.valueOf(NumberFacility.getUInt(groupID)));
        } catch (Exception e) {
            LOG.error(e, "Invalid group DN '{0}'", dn);
            throw new ConnectorException(e);
        }
    }

    public String getPrimaryGroupDN(final LdapEntry entry, final Attributes profile) throws NamingException {

        final javax.naming.directory.Attribute primaryGroupID = profile.get(PRIMARYGROUPID);
        final javax.naming.directory.Attribute objectSID = profile.get(OBJECTSID);

        final String pgDN;

        if (primaryGroupID == null || primaryGroupID.get() == null || objectSID == null || objectSID.get() == null) {
            pgDN = null;
        } else {
            final SID groupSID = getPrimaryGroupSID(SID.parse((byte[]) objectSID.get()),
                    NumberFacility.getUIntBytes(Long.parseLong(primaryGroupID.get().toString())));

            final Set<SearchResult> res = basicLdapSearch(String.format(
                    "(&(objectclass=group)(%s=%s))", OBJECTSID, Hex.getEscaped(groupSID.toByteArray())),
                    ((ADConfiguration) connection.getConfiguration()).getGroupBaseContexts());

            if (res == null || res.isEmpty()) {
                LOG.warn("Error retrieving primary group for {0}", entry.getDN());
                pgDN = null;
            } else {
                pgDN = res.iterator().next().getNameInNamespace();
                LOG.info("Found primary group {0}", pgDN);
            }
        }

        return pgDN;
    }

    public Set<String> getAttributesToGet(final String[] attributesToGet, final ObjectClass oclass) {
        final Set<String> result;

        if (attributesToGet != null) {
            result = CollectionUtil.newCaseInsensitiveSet();
            result.addAll(Arrays.asList(attributesToGet));
            removeNonReadableAttributes(result, oclass);
            result.add(Name.NAME);
        } else {
            // This should include Name.NAME.
            result = getAttributesReturnedByDefault(connection, oclass);
        }

        // Uid is required to build a ConnectorObject.
        result.add(Uid.NAME);

        if (oclass.is(ObjectClass.ACCOUNT_NAME)) {
            // AD specific, for checking wether a user is enabled or not
            result.add(UACCONTROL_ATTR);
        }

        // -------------------------------------------------
        // AD-52 (paged membership retrieving: 1k a time)
        // -------------------------------------------------
        final String memberships
                = ADConfiguration.class.cast(connection.getConfiguration()).getGroupMemberReferenceAttribute();

        if (oclass.is(ObjectClass.GROUP_NAME) && result.contains(memberships)) {
            // AD specific, for checking wether a user is enabled or not
            result.remove(memberships);
            result.add(String.format("%s;range=%d-%d", memberships, 0, 999));
        }
        // -------------------------------------------------

        // Our password is marked as readable because of sync().
        // We really can't return it from basicLdapSearch.
        if (result.contains(OperationalAttributes.PASSWORD_NAME)) {
            LOG.warn("Reading passwords not supported");
        }

        if (result.contains(UCCP_FLAG)) {
            result.remove(UCCP_FLAG);
            result.add(SDDL_ATTR);
        }

        // Required attribute for 
        if (result.contains(LdapConstants.LDAP_GROUPS_NAME)) {
            result.add(OBJECTSID);
            result.add(PRIMARYGROUPID);
        }

        return result;
    }

    private void removeNonReadableAttributes(final Set<String> attributes, final ObjectClass oclass) {
        // Since the groups attributes are fake attributes, we don't want to
        // send them to LdapSchemaMapping. This, for example, avoid an 
        // (unlikely) conflict with a custom attribute defined in the server
        // schema.
        boolean ldapGroups = attributes.remove(LdapConstants.LDAP_GROUPS_NAME);
        boolean posixGroups = attributes.remove(LdapConstants.POSIX_GROUPS_NAME);

        connection.getSchemaMapping().removeNonReadableAttributes(oclass, attributes);

        if (ldapGroups) {
            attributes.add(LdapConstants.LDAP_GROUPS_NAME);
        }

        if (posixGroups) {
            attributes.add(LdapConstants.POSIX_GROUPS_NAME);
        }
    }

    public static Set<String> getAttributesReturnedByDefault(final LdapConnection conn, final ObjectClass oclass) {
        if (oclass.equals(LdapSchemaMapping.ANY_OBJECT_CLASS)) {
            return newSet(Name.NAME);
        }

        final Set<String> result = newCaseInsensitiveSet();

        final ObjectClassInfo oci = conn.getSchemaMapping().schema().findObjectClassInfo(oclass.getObjectClassValue());

        if (oci != null) {
            for (AttributeInfo info : oci.getAttributeInfo()) {
                if (info.isReturnedByDefault() && !ADDS2012_ATTRIBUTES_TO_BE_REMOVED.contains(info.getName())) {
                    result.add(info.getName());
                }
            }
        }

        return result;
    }

    public Set<String> getLdapAttributesToGet(final Set<String> attrsToGet, final ObjectClass oclass) {
        final Set<String> cleanAttrsToGet = newCaseInsensitiveSet();
        cleanAttrsToGet.addAll(attrsToGet);
        cleanAttrsToGet.remove(LdapConstants.LDAP_GROUPS_NAME);

        boolean posixGroups = cleanAttrsToGet.remove(LdapConstants.POSIX_GROUPS_NAME);

        final Set<String> result = connection.getSchemaMapping().getLdapAttributes(oclass, cleanAttrsToGet, true);

        if (posixGroups) {
            result.add(GroupHelper.getPosixRefAttribute());
        }

        return result;
    }

    public ConnectorObject createConnectorObject(
            final String baseDN,
            final SearchResult result,
            final Collection<String> attrsToGet,
            final ObjectClass oclass)
            throws NamingException {

        return createConnectorObject(baseDN, result.getAttributes(), attrsToGet, oclass);
    }

    public ConnectorObject createConnectorObject(
            final String baseDN,
            final Attributes profile,
            final Collection<String> attrsToGet,
            final ObjectClass oclass)
            throws NamingException {

        final LdapEntry entry = LdapEntry.create(baseDN, profile);

        final ConnectorObjectBuilder builder = new ConnectorObjectBuilder();
        builder.setObjectClass(oclass);

        if (OBJECTGUID.equals(connection.getSchemaMapping().getLdapUidAttribute(oclass))) {
            builder.setUid(GUID.getGuidAsString((byte[]) entry.getAttributes().get(OBJECTGUID).get()));
        } else {
            builder.setUid(connection.getSchemaMapping().createUid(oclass, entry));
        }

        builder.setName(connection.getSchemaMapping().createName(oclass, entry));

        String pgDN = null;

        for (String attributeName : attrsToGet) {

            Attribute attribute = null;

            if (LdapConstants.isLdapGroups(attributeName) || attributeName.equals(ADConnector.MEMBEROF)) {
                final Set<String> ldapGroups = getGroups(entry.getDN().toString());
                if (StringUtil.isBlank(pgDN)) {
                    pgDN = getPrimaryGroupDN(entry, profile);
                }
                if (StringUtil.isNotBlank(pgDN)) {
                    ldapGroups.add(pgDN);
                }
                attribute = AttributeBuilder.build(attributeName, ldapGroups);
            } else if (LdapConstants.isPosixGroups(attributeName)) {
                final Set<String> posixRefAttrs = LdapUtil.getStringAttrValues(entry.getAttributes(), GroupHelper.
                        getPosixRefAttribute());
                final List<String> posixGroups = groupHelper.getPosixGroups(posixRefAttrs);
                attribute = AttributeBuilder.build(LdapConstants.POSIX_GROUPS_NAME, posixGroups);
            } else if (LdapConstants.PASSWORD.is(attributeName) && oclass.is(ObjectClass.ACCOUNT_NAME)) {
                // IMPORTANT!!! Return empty guarded string
                attribute = AttributeBuilder.build(attributeName, new GuardedString());
            } else if (PNE_FLAG.equalsIgnoreCase(attributeName) && oclass.is(ObjectClass.ACCOUNT_NAME)) {
                try {

                    final String uac = profile.get(UACCONTROL_ATTR) == null
                            || profile.get(UACCONTROL_ATTR).get() == null
                            ? null : profile.get(UACCONTROL_ATTR).get().toString();

                    if (LOG.isOk()) {
                        LOG.ok("User Account Control: {0}", uac);
                    }

                    // disabled if PNE_FLAG is not included (0x10000)
                    attribute = uac == null || (Integer.parseInt(uac) & UF_DONT_EXPIRE_PASSWD) != UF_DONT_EXPIRE_PASSWD
                            ? AttributeBuilder.build(PNE_FLAG, false)
                            : AttributeBuilder.build(PNE_FLAG, true);
                } catch (NamingException e) {
                    LOG.error(e, "While fetching " + UACCONTROL_ATTR);
                }
            } else if (UACCONTROL_ATTR.equalsIgnoreCase(attributeName) && oclass.is(ObjectClass.ACCOUNT_NAME)) {
                try {

                    final String status = profile.get(UACCONTROL_ATTR) == null
                            || profile.get(UACCONTROL_ATTR).get() == null
                            ? null : profile.get(UACCONTROL_ATTR).get().toString();

                    if (LOG.isOk()) {
                        LOG.ok("User Account Control: {0}", status);
                    }

                    // enabled if UF_ACCOUNTDISABLE is not included (0x00002)
                    builder.addAttribute(
                            status == null || Integer.parseInt(
                                    profile.get(UACCONTROL_ATTR).get().toString())
                            % 16 != UF_ACCOUNTDISABLE
                                    ? AttributeBuilder.buildEnabled(true)
                                    : AttributeBuilder.buildEnabled(false));

                    attribute = connection.getSchemaMapping().createAttribute(oclass, attributeName, entry, false);
                } catch (NamingException e) {
                    LOG.error(e, "While fetching " + UACCONTROL_ATTR);
                }
            } else if (OBJECTGUID.equalsIgnoreCase(attributeName)) {
                attribute = AttributeBuilder.build(
                        attributeName, GUID.getGuidAsString((byte[]) profile.get(OBJECTGUID).get()));
            } else if (SDDL_ATTR.equalsIgnoreCase(attributeName)) {
                javax.naming.directory.Attribute sddl = profile.get(SDDL_ATTR);
                if (sddl != null) {
                    attribute = AttributeBuilder.build(
                            UCCP_FLAG,
                            SDDLHelper.isUserCannotChangePassword(new SDDL(((byte[]) sddl.get()))));
                }
            } else if (ADConfiguration.PRIMARY_GROUP_DN_NAME.equalsIgnoreCase(attributeName)) {
                if (StringUtil.isBlank(pgDN)) {
                    pgDN = getPrimaryGroupDN(entry, profile);
                }
                attribute = AttributeBuilder.build(ADConfiguration.PRIMARY_GROUP_DN_NAME, pgDN);
            } else if (oclass.is(ObjectClass.GROUP_NAME)
                    && String.format("%s;range=%d-%d", ADConfiguration.class.cast(connection.getConfiguration()).
                            getGroupMemberReferenceAttribute(), 0, 999).equalsIgnoreCase(attributeName)) {
                // loop on membership ranges and populate member attribute

                final String membAttrPrefix
                        = ADConfiguration.class.cast(connection.getConfiguration()).
                                getGroupMemberReferenceAttribute();

                // search for less than 1k memberships
                String membAttrName = String.format("%s;range=0-*", membAttrPrefix);
                attribute = connection.getSchemaMapping().createAttribute(oclass, membAttrName, entry, true);

                final ArrayList<Object> values = new ArrayList<Object>(attribute.getValue());

                if (values.isEmpty()) {
                    // loop among ranges
                    int start = 0;
                    int end = 999;
                    membAttrName = String.format("%s;range=%d-%d", membAttrPrefix, start, end);
                    attribute = connection.getSchemaMapping().createAttribute(oclass, membAttrName, entry, true);

                    values.addAll(attribute.getValue());

                    boolean theEnd = CollectionUtil.isEmpty(attribute.getValue());
                    while (!theEnd) { // more than 1 page ....    
                        start += 1000;
                        end += 1000;
                        membAttrName = String.format("%s;range=%d-%d", membAttrPrefix, start, end);
                        Attributes membAttrs = getAttributes(entry.getDN().toString(), membAttrName);

                        if (membAttrs == null || membAttrs.size() <= 0) {
                            theEnd = true;
                        } else {
                            javax.naming.directory.Attribute membAttr = membAttrs.getAll().next();
                            theEnd = membAttr.getID().equalsIgnoreCase(
                                    String.format("%s;range=%d-*", membAttrPrefix, start));

                            final NamingEnumeration<?> ne = membAttr.getAll();
                            while (ne.hasMore()) {
                                values.add(ne.next());
                            }
                        }

                    }
                }

                attribute = AttributeBuilder.build(membAttrPrefix, values);
            } else if (profile.get(attributeName) != null) {
                attribute = connection.getSchemaMapping().createAttribute(oclass, attributeName, entry, false);
            }

            // Avoid attribute adding in case of attribute name not found
            if (attribute != null) {
                builder.addAttribute(attribute);
            }
        }

        return builder.build();
    }

    /**
     * Create a DN string starting from a set attributes and a default people container. This method has to be used
     * if
     * __NAME__ attribute is not provided or it it is not a DN.
     *
     * @param oclass object class.
     * @param nameAttr naming attribute.
     * @param cnAttr cn attribute.
     * @return distinguished name string.
     */
    public final String getDN(final ObjectClass oclass, final Name nameAttr, final Attribute cnAttr) {

        String cn;

        if (cnAttr == null || cnAttr.getValue() == null
                || cnAttr.getValue().isEmpty()
                || cnAttr.getValue().get(0) == null
                || StringUtil.isBlank(cnAttr.getValue().get(0).toString())) {
            // Get the name attribute and consider this as the principal name.
            // Use the principal name as the CN to generate DN.
            cn = nameAttr.getNameValue();
        } else {
            // Get the common name and use this to generate the DN.
            cn = cnAttr.getValue().get(0).toString();
        }

        return "cn=" + cn + ","
                + (oclass.is(ObjectClass.ACCOUNT_NAME)
                ? ((ADConfiguration) (connection.getConfiguration())).getDefaultPeopleContainer()
                : ((ADConfiguration) (connection.getConfiguration())).getDefaultGroupContainer());
    }

    /**
     * Check if the String is an ldap DN.
     *
     * @param dn string to be checked.
     * @return TRUE if the value provided is a DN; FALSE otherwise.
     */
    public static boolean isDN(final String dn) {
        try {
            return StringUtil.isNotBlank(dn) && new LdapName(dn) != null;
        } catch (InvalidNameException ex) {
            if (LOG.isOk()) {
                LOG.ok(ex, "Invalid DN {0}", dn);
            }
            return false;
        }
    }

    public String getMembershipSearchFilter(final ADConfiguration conf) {
        final StringBuilder ufilter = new StringBuilder();
        final String[] memberships = conf.getMemberships();
        if (memberships != null && memberships.length > 0) {
            ufilter.append(conf.isMembershipsInOr() ? "(|" : "(&");

            for (String group : memberships) {
                ufilter.append("(").append(ADConnector.MEMBEROF).append("=").append(group).append(")");
            }

            ufilter.append(")");
        }
        return ufilter.toString();
    }

    public LdapEntry getEntryToBeUpdated(final String entryDN) {
        LdapEntry obj = null;
        try {
            obj = LdapSearches.getEntry(
                    connection,
                    new LdapName(entryDN),
                    UACCONTROL_ATTR,
                    SDDL_ATTR,
                    OBJECTSID,
                    PRIMARYGROUPID);
        } catch (Exception e) {
            LOG.warn(e, "Invalid entry DN");
        }

        if (obj == null) {
            throw new ConnectorException("Entry not found");
        }

        return obj;
    }

    public ConnectorObject getEntryToBeUpdated(final Uid uid, final ObjectClass oclass) {
        final String filter = connection.getSchemaMapping().getLdapUidAttribute(oclass) + "=" + uid.getUidValue();

        final ConnectorObject obj = LdapSearches.findObject(
                connection, oclass,
                LdapFilter.forNativeFilter(filter),
                UACCONTROL_ATTR,
                SDDL_ATTR,
                OBJECTSID,
                PRIMARYGROUPID);

        if (obj == null) {
            throw new ConnectorException("Entry not found");
        }

        return obj;
    }

    public Attributes getAttributes(final String entryDN, final String... attributes) {
        try {
            return connection.getInitialContext().getAttributes(entryDN, attributes);
        } catch (NamingException e) {
            throw new ConnectorException(e);
        }
    }

    public javax.naming.directory.Attribute userCannotChangePassword(final String entryDN, final Boolean cannot) {
        javax.naming.directory.Attribute ntSecurityDescriptor = getAttributes(entryDN, SDDL_ATTR).get(SDDL_ATTR);
        if (ntSecurityDescriptor == null) {
            return null;
        }
        try {
            return userCannotChangePassword((byte[]) ntSecurityDescriptor.get(), cannot);
        } catch (NamingException ex) {
            LOG.error(ex, "Error retrieving sddl");
            return null;
        }
    }

    public javax.naming.directory.Attribute userCannotChangePassword(final ConnectorObject obj, final Boolean cannot) {
        final Attribute ntSecurityDescriptor = obj.getAttributeByName(SDDL_ATTR);
        if (ntSecurityDescriptor == null
                || ntSecurityDescriptor.getValue() == null
                || ntSecurityDescriptor.getValue().isEmpty()) {
            return null;
        }

        return userCannotChangePassword((byte[]) ntSecurityDescriptor.getValue().get(0), cannot);
    }

    public javax.naming.directory.Attribute userCannotChangePassword(final byte[] obj, final Boolean cannot) {

        if (obj == null) {
            return null;
        }

        return new BasicAttribute(SDDL_ATTR, SDDLHelper.userCannotChangePassword(new SDDL(obj), cannot).toByteArray());
    }

    public Set<SearchResult> basicLdapSearch(final String filter, final String... baseContextDNs) {

        final LdapContext ctx = connection.getInitialContext();

        // -----------------------------------
        // Create basicLdapSearch control
        // -----------------------------------
        final SearchControls searchCtls = LdapInternalSearch.createDefaultSearchControls();
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchCtls.setReturningAttributes(new String[0]);
        // -----------------------------------

        final Set<SearchResult> result = new HashSet<SearchResult>();

        for (String baseContextDn : baseContextDNs) {

            if (LOG.isOk()) {
                LOG.ok("Searching from " + baseContextDn);
            }

            try {
                final NamingEnumeration<SearchResult> answer = ctx.search(baseContextDn, filter, searchCtls);

                while (answer.hasMoreElements()) {
                    result.add(answer.nextElement());
                }
            } catch (NamingException e) {
                LOG.error(e, "While searching base context {0} with filter {1} and search controls {2}",
                        baseContextDn, filter, searchCtls);
            }
        }

        return result;
    }

    public Set<String> getGroups(final String entryDN) {
        return getGroups(entryDN, ((ADConfiguration) connection.getConfiguration()).getGroupBaseContexts());
    }

    public Set<String> getGroups(final String entryDN, final String... baseContexts) {
        final String member = ((ADConfiguration) connection.getConfiguration()).
                getGroupMemberReferenceAttribute();

        final Set<String> ldapGroups = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (SearchResult res : basicLdapSearch(filterInOr(member, entryDN), baseContexts)) {
            ldapGroups.add(res.getNameInNamespace());
        }

        return ldapGroups;
    }

    private String filterInOr(final String attr, final String... values) {
        final StringBuilder builder = new StringBuilder();
        boolean multi = values != null && values.length > 1;
        if (multi) {
            builder.append("(|");
        }
        for (String memberValue : values) {
            builder.append('(');
            builder.append(attr);
            builder.append('=');
            escapeAttrValue(memberValue, builder);
            builder.append(')');
        }
        if (multi) {
            builder.append(")");
        }
        return builder.toString();
    }
}
