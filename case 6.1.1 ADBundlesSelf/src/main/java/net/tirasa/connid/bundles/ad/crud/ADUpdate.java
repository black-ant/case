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
import static net.tirasa.connid.bundles.ad.ADConnector.OBJECTSID;
import static net.tirasa.connid.bundles.ad.ADConnector.PRIMARYGROUPID;
import static net.tirasa.connid.bundles.ldap.commons.LdapUtil.checkedListByFilter;
import static net.tirasa.connid.bundles.ad.ADConnector.UACCONTROL_ATTR;
import static net.tirasa.connid.bundles.ad.ADConnector.UF_ACCOUNTDISABLE;
import static net.tirasa.connid.bundles.ad.util.ADUtilities.getPrimaryGroupSID;
import static org.identityconnectors.common.CollectionUtil.isEmpty;
import static org.identityconnectors.common.CollectionUtil.newSet;
import static org.identityconnectors.common.CollectionUtil.nullAsEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.naming.InvalidNameException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;

import net.tirasa.adsddl.ntsd.SID;
import net.tirasa.adsddl.ntsd.utils.Hex;
import net.tirasa.adsddl.ntsd.utils.NumberFacility;
import net.tirasa.connid.bundles.ad.ADConfiguration;
import net.tirasa.connid.bundles.ad.ADConnection;
import net.tirasa.connid.bundles.ad.util.ADGuardedPasswordAttribute;
import net.tirasa.connid.bundles.ad.util.ADGuardedPasswordAttribute.Accessor;
import net.tirasa.connid.bundles.ad.util.ADUtilities;
import net.tirasa.connid.bundles.ldap.commons.GroupHelper.GroupMembership;
import net.tirasa.connid.bundles.ldap.commons.GroupHelper.Modification;
import net.tirasa.connid.bundles.ldap.commons.LdapConstants;
import net.tirasa.connid.bundles.ldap.commons.LdapEntry;
import net.tirasa.connid.bundles.ldap.commons.LdapModifyOperation;
import org.identityconnectors.common.Pair;
import org.identityconnectors.common.StringUtil;
import org.identityconnectors.common.logging.Log;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.AttributeBuilder;
import org.identityconnectors.framework.common.objects.AttributeUtil;
import org.identityconnectors.framework.common.objects.ConnectorObject;
import org.identityconnectors.framework.common.objects.Name;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.OperationalAttributes;
import org.identityconnectors.framework.common.objects.Uid;

public class ADUpdate extends LdapModifyOperation {

    private static final Log LOG = Log.getLog(ADUpdate.class);

    private final ObjectClass oclass;

    private final Uid uid;

    private final ADUtilities utils;

    @SuppressWarnings("FieldNameHidesFieldInSuperclass")
    private final ADConnection conn;

    /**
     * Retrieve new name if specified.
     *
     * @return new Name if provider; null otherwise.
     */
    private Name getNewName(final String entryDN, final Set<Attribute> attrs) {
        // purge CN
        final Attribute cnAttr = AttributeUtil.find(ADConfiguration.CN_NAME, attrs);

        if (cnAttr != null) {
            attrs.remove(cnAttr);
        }

        // retrieve new name...
        final Name name = AttributeUtil.getNameFromAttributes(attrs);

        Name newName = null;

        if (name != null) {
            attrs.remove(name);

            if (ADUtilities.isDN(name.getNameValue())) {
                newName = new Name(conn.getSchemaMapping().getEntryDN(oclass, name));
            }
        }

        if (newName == null
                && !conn.getSchemaMapping().getLdapUidAttribute(oclass).equalsIgnoreCase(ADConfiguration.CN_NAME)
                && cnAttr != null) {
            final String cnValue = cnAttr.getValue() == null
                    || cnAttr.getValue().isEmpty()
                    || cnAttr.getValue().get(0) == null
                    ? null
                    : cnAttr.getValue().get(0).toString();

            try {
                // rename if and only if Name is a DN or CN has been provided (consider that the CN can be the Name)
                final List<Rdn> rdns = new ArrayList<Rdn>(new LdapName(entryDN).getRdns());

                Rdn naming = new Rdn(rdns.get(rdns.size() - 1).getType(), cnValue);
                rdns.remove(rdns.size() - 1);
                rdns.add(naming);

                newName = new Name(new LdapName(rdns).toString());
            } catch (InvalidNameException e) {
                LOG.error("Error retrieving new DN. Ignore rename request.", e);
            }
        }

        return newName;
    }

    public ADUpdate(final ADConnection conn, final ObjectClass oclass, final Uid uid) {
        super(conn);
        this.utils = new ADUtilities(conn);
        this.oclass = oclass;
        this.uid = uid;
        this.conn = conn;
    }

    public Uid update(final Set<Attribute> attrs) {
        final ConnectorObject obj = utils.getEntryByUid(uid, oclass);
        String entryDN = obj.getName().getNameValue();

        // ---------------------------------
        // Check if entry should be renamed.
        // ---------------------------------
        final Set<Attribute> attrsToBeUpdated = newSet(attrs);
        final Name newName = getNewName(entryDN, attrsToBeUpdated);
        // ---------------------------------

        // ---------------------------------
        // Perform modify/rename
        // ---------------------------------
        final Pair<Attributes, ADGuardedPasswordAttribute> attrToModify = getAttributesToModify(obj, attrsToBeUpdated);

        // Update the attributes.
        modifyAttributes(entryDN, attrToModify, DirContext.REPLACE_ATTRIBUTE);

        // Rename the entry if needed.
        if (newName != null && !newName.equals(obj.getName())) {
            entryDN = conn.getSchemaMapping().rename(oclass, entryDN, newName);
        }
        // ---------------------------------

        modifyMemberships(entryDN, attrsToBeUpdated);
        modifyPrimaryGroupID(entryDN, attrsToBeUpdated);

        try {
            return new Uid(utils.findEntryGUID(entryDN));
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return new Uid(entryDN);
        // TODO : WHY ?
        //        return conn.getSchemaMapping().createUid(oclass, entryDN);
    }

    public Uid addAttributeValues(final Set<Attribute> attrs) {
        final ConnectorObject obj = utils.getEntryToBeUpdated(uid, oclass);
        final String entryDN = obj.getName().getNameValue();

        final Pair<Attributes, ADGuardedPasswordAttribute> attrsToModify = getAttributesToModify(obj, attrs);

        modifyAttributes(entryDN, attrsToModify, DirContext.ADD_ATTRIBUTE);
        modifyMemberships(entryDN, attrs);
        modifyPrimaryGroupID(entryDN, attrs);

        return uid;
    }

    public Uid removeAttributeValues(final Set<Attribute> attrs) {
        final ConnectorObject obj = utils.getEntryToBeUpdated(uid, oclass);
        final String entryDN = obj.getName().getNameValue();

        final Pair<Attributes, ADGuardedPasswordAttribute> attrsToModify = getAttributesToModify(obj, attrs);

        modifyAttributes(entryDN, attrsToModify, DirContext.REMOVE_ATTRIBUTE);

        List<String> ldapGroups = getStringListValue(attrs, LdapConstants.LDAP_GROUPS_NAME);
        if (!isEmpty(ldapGroups)) {
            groupHelper.removeLdapGroupMemberships(entryDN, ldapGroups);
        }

        return uid;
    }

    private Pair<Attributes, ADGuardedPasswordAttribute> getAttributesToModify(
            final ConnectorObject obj, final Set<Attribute> attrs) {

        final BasicAttributes ldapAttrs = new BasicAttributes();

        ADGuardedPasswordAttribute pwdAttr = null;

        int uacValue = -1;

        for (Attribute attr : attrs) {
            javax.naming.directory.Attribute ldapAttr = null;
            if (attr.is(Uid.NAME)) {

                throw new IllegalArgumentException("Unable to modify an object's uid");

            } else if (attr.is(Name.NAME)) {

                // Such a change would have been handled in update() above.
                throw new IllegalArgumentException("Unable to modify an object's name");

            } else if (attr.is(ADConfiguration.UCCP_FLAG)) {
                final List<Object> value = attr.getValue();
                if (value != null && !value.isEmpty()) {
                    javax.naming.directory.Attribute ntSecurityDescriptor = utils.userCannotChangePassword(
                            obj, (Boolean) value.get(0));
                    if (ntSecurityDescriptor != null) {
                        ldapAttrs.put(ntSecurityDescriptor);
                    }
                }
            } else if (attr.is(ADConfiguration.PROMPT_USER_FLAG)) {
                final List<Object> value = attr.getValue();
                if (value != null && !value.isEmpty() && (Boolean) value.get(0)) {
                    ldapAttrs.put(
                            new BasicAttribute(ADConfiguration.PROMPT_USER_FLAG, ADConfiguration.PROMPT_USER_VALUE));
                }
            } else if (attr.is(ADConfiguration.LOCK_OUT_FLAG)) {
                final List<Object> value = attr.getValue();
                if (value != null && !value.isEmpty() && (Boolean) value.get(0)) {
                    ldapAttrs.put(
                            new BasicAttribute(ADConfiguration.LOCK_OUT_FLAG, ADConfiguration.LOCK_OUT_DEFAULT_VALUE));
                }
            } else if (LdapConstants.isLdapGroups(attr.getName())) {
                // Handled elsewhere.
            } else if (attr.is(OperationalAttributes.PASSWORD_NAME)) {

                pwdAttr = ADGuardedPasswordAttribute.create(conn.getConfiguration().getPasswordAttribute(), attr);

            } else if (attr.is(UACCONTROL_ATTR) && oclass.is(ObjectClass.ACCOUNT_NAME)) {
                uacValue = attr.getValue() == null || attr.getValue().isEmpty()
                        ? -1
                        : Integer.parseInt(attr.getValue().get(0).toString());
            } else if (attr.is(OperationalAttributes.ENABLE_NAME)
                    && oclass.is(ObjectClass.ACCOUNT_NAME)
                    && uacValue == -1) {
                final Attribute uac = obj.getAttributeByName(UACCONTROL_ATTR);

                uacValue = uac != null && uac.getValue() != null && !uac.getValue().isEmpty()
                        ? Integer.parseInt(uac.getValue().get(0).toString()) : 0;

                boolean enabled = attr.getValue() == null
                        || attr.getValue().isEmpty() || Boolean.parseBoolean(attr.getValue().get(0).toString());

                if (enabled) {
                    // if not enabled yet --> enable removing 0x00002
                    if (uacValue % 16 == UF_ACCOUNTDISABLE) {
                        uacValue -= UF_ACCOUNTDISABLE;
                    }
                } else {
                    // if not disabled yet --> disable adding 0x00002
                    if (uacValue % 16 != UF_ACCOUNTDISABLE) {
                        uacValue += UF_ACCOUNTDISABLE;
                    }
                }
            } else if (attr.is(OBJECTGUID)) {
                // ignore info
            } else {
                ldapAttr = conn.getSchemaMapping().encodeAttribute(oclass, attr);
            }

            addAttribute(ldapAttr, ldapAttrs);
        }

        if (uacValue != -1) {
            addAttribute(conn.getSchemaMapping().encodeAttribute(
                    oclass, AttributeBuilder.build(UACCONTROL_ATTR, Integer.toString(uacValue))),
                    ldapAttrs);
        }

        return new Pair<Attributes, ADGuardedPasswordAttribute>(ldapAttrs, pwdAttr);
    }

    private void addAttribute(final javax.naming.directory.Attribute ldapAttr, final BasicAttributes ldapAttrs) {
        if (ldapAttr != null) {
            final javax.naming.directory.Attribute existingAttr = ldapAttrs.get(ldapAttr.getID());

            if (existingAttr != null) {
                try {
                    NamingEnumeration<?> all = ldapAttr.getAll();
                    while (all.hasMoreElements()) {
                        existingAttr.add(all.nextElement());
                    }
                } catch (NamingException e) {
                    throw new ConnectorException(e);
                }
            } else {
                ldapAttrs.put(ldapAttr);
            }
        }
    }

    private void modifyAttributes(
            final String entryDN,
            final Pair<Attributes, ADGuardedPasswordAttribute> attrs,
            final int modifyOp) {

        final List<ModificationItem> modItems = new ArrayList<ModificationItem>(attrs.first.size());

        NamingEnumeration<? extends javax.naming.directory.Attribute> attrEnum = attrs.first.getAll();

        while (attrEnum.hasMoreElements()) {
            final javax.naming.directory.Attribute attr = attrEnum.nextElement();
            if (!attr.getID().equalsIgnoreCase(LdapConstants.LDAP_GROUPS_NAME)
                    && !attr.getID().equalsIgnoreCase(ADConfiguration.PRIMARY_GROUP_DN_NAME)) {
                modItems.add(new ModificationItem(modifyOp, attr));
            }
        }

        if (attrs.second != null) {
            attrs.second.access(new Accessor() {

                @Override
                public void access(BasicAttribute attr) {
                    try {
                        if (attr.get() != null) {
                            modItems.add(new ModificationItem(modifyOp, attr));
                            modifyAttributes(entryDN, modItems);
                        }
                    } catch (NamingException e) {
                        LOG.error(e, "Error retrieving password value");
                    }
                }
            });
        }

        modifyAttributes(entryDN, modItems);
    }

    private void modifyAttributes(final String entryDN, final List<ModificationItem> modItems) {
        try {
            conn.getInitialContext().modifyAttributes(entryDN, modItems.toArray(new ModificationItem[modItems.size()]));
        } catch (NamingException e) {
            throw new ConnectorException(e);
        }
    }

    private List<String> getStringListValue(final Set<Attribute> attrs, final String attrName) {
        final Attribute attr = AttributeUtil.find(attrName, attrs);

        if (attr != null && attr.getValue() != null) {
            return checkedListByFilter(nullAsEmpty(attr.getValue()), String.class);
        }

        return null;
    }

    /**
     * Modify primaryGrupID.
     *
     * @param entryDN entry to be modified.
     * @param attrs   source attributes.
     * @throws NamingException
     */
    private void modifyPrimaryGroupID(
            final String entryDN,
            final Set<org.identityconnectors.framework.common.objects.Attribute> attrs) {
        final List<String> primaryGroupDN = getStringListValue(attrs, ADConfiguration.PRIMARY_GROUP_DN_NAME);

        if (primaryGroupDN != null && !primaryGroupDN.isEmpty()) {
            try {
                conn.getInitialContext().modifyAttributes(entryDN, new ModificationItem[]{
                        new ModificationItem(DirContext.REPLACE_ATTRIBUTE, utils.getGroupID(primaryGroupDN.get(0)))});
            } catch (NamingException e) {
                LOG.error(e, "Error setting primaryGroupID '{0}' for '{1}'", primaryGroupDN, entryDN);
            }
        }
    }

    /**
     * Perform group membership.
     *
     * @param entryDN entry to be modified.
     * @param attrs   source attributes.
     */
    private void modifyMemberships(
            final String entryDN,
            final Set<org.identityconnectors.framework.common.objects.Attribute> attrs) {
        final List<String> ldapGroups = getStringListValue(attrs, LdapConstants.LDAP_GROUPS_NAME);

        if (ldapGroups != null) {
            // All current roles ....
            final Set<String> currents = utils.getGroups(entryDN,
                    ((ADConfiguration) conn.getConfiguration()).getBaseContextsToSynchronize());

            // Current role into the managed group base contexts
            final Set<String> oldMemberships = utils.getGroups(entryDN);

            String primaryGroup = null;

            final LdapEntry profile = utils.getEntryToBeUpdated(entryDN);

            try {
                final javax.naming.directory.Attribute primaryGroupID = profile.getAttributes().get(PRIMARYGROUPID);
                if (primaryGroupID != null && primaryGroupID.get() != null) {

                    final javax.naming.directory.Attribute objectsid = profile.getAttributes().get(OBJECTSID);

                    final SID groupSID = getPrimaryGroupSID(
                            SID.parse((byte[]) objectsid.get()),
                            NumberFacility.getUIntBytes(Long.parseLong(primaryGroupID.get().toString())));

                    final Set<SearchResult> res = utils.basicLdapSearch(String.format(
                            "(&(objectclass=group)(%s=%s))", OBJECTSID, Hex.getEscaped(groupSID.toByteArray())),
                            ((ADConfiguration) conn.getConfiguration()).getBaseContextsToSynchronize());

                    if (res == null || res.isEmpty()) {
                        LOG.warn("Error retrieving primary group for {0}", entryDN);
                    } else {
                        primaryGroup = res.iterator().next().getNameInNamespace();
                        LOG.info("Found primary group {0}", primaryGroup);
                    }
                }
            } catch (NamingException ne) {
                LOG.error(ne, "Error retrieving primary group");
                throw new ConnectorException(ne);
            }

            // Check if group already exists
            final Set<String> newMemberships = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
            for (String grp : ldapGroups) {
                if (currents.contains(grp)) {
                    oldMemberships.remove(grp);
                } else {
                    newMemberships.add(grp);
                }
            }

            if (StringUtil.isNotBlank(primaryGroup)) {
                newMemberships.remove(primaryGroup);
            }

            // Update the LDAP groups.
            final Modification<GroupMembership> ldapGroupMod = new Modification<GroupMembership>();

            if (!ADConfiguration.class.cast(conn.getConfiguration()).isMembershipConservativePolicy()) {
                for (String membership : oldMemberships) {
                    ldapGroupMod.remove(new GroupMembership(entryDN, membership));
                }
            }

            for (String membership : newMemberships) {
                ldapGroupMod.add(new GroupMembership(entryDN, ADUtilities.getDnByGuid(membership, conn)));
            }

            groupHelper.modifyLdapGroupMemberships(ldapGroupMod);
        }
    }
}
