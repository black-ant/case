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
import static net.tirasa.connid.bundles.ad.ADConnector.UACCONTROL_ATTR;
import static net.tirasa.connid.bundles.ad.ADConnector.UF_ACCOUNTDISABLE;
import static net.tirasa.connid.bundles.ad.ADConnector.UF_NORMAL_ACCOUNT;
import static net.tirasa.connid.bundles.ldap.commons.LdapUtil.checkedListByFilter;
import static org.identityconnectors.common.CollectionUtil.isEmpty;
import static org.identityconnectors.common.CollectionUtil.nullAsEmpty;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import net.tirasa.adsddl.ntsd.utils.GUID;
import net.tirasa.connid.bundles.ad.ADConfiguration;
import net.tirasa.connid.bundles.ad.ADConnection;
import net.tirasa.connid.bundles.ad.util.ADGuardedPasswordAttribute;
import net.tirasa.connid.bundles.ad.util.ADGuardedPasswordAttribute.Accessor;
import net.tirasa.connid.bundles.ad.util.ADType;
import net.tirasa.connid.bundles.ad.util.ADUtilities;
import net.tirasa.connid.bundles.ldap.LdapConfiguration;
import net.tirasa.connid.bundles.ldap.LdapConnection;
import net.tirasa.connid.bundles.ldap.commons.LdapConstants;
import net.tirasa.connid.bundles.ldap.commons.LdapModifyOperation;
import org.apache.commons.lang3.StringUtils;
import org.identityconnectors.common.CollectionUtil;
import org.identityconnectors.common.StringUtil;
import org.identityconnectors.common.logging.Log;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.AttributeBuilder;
import org.identityconnectors.framework.common.objects.AttributeUtil;
import org.identityconnectors.framework.common.objects.Name;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.OperationOptions;
import org.identityconnectors.framework.common.objects.OperationalAttributes;
import org.identityconnectors.framework.common.objects.Uid;

public class ADCreate extends LdapModifyOperation {

    private static final Log LOG = Log.getLog(ADConnection.class);

    private final ObjectClass oclass;

    private final Set<Attribute> attrs;

    @SuppressWarnings("FieldNameHidesFieldInSuperclass")
    private final ADConnection conn;

    public ADCreate(
            final ADConnection conn,
            final ObjectClass oclass,
            final Set<Attribute> attrs,
            final OperationOptions options) {
        super(conn);

        this.oclass = oclass;
        this.attrs = attrs;
        this.conn = conn;
    }

    public Uid create() {
        try {
            return executeImpl();
        } catch (NamingException e) {
            throw new ConnectorException(e);
        }
    }

    private Uid executeImpl() throws NamingException {

        if (LOG.isOk()) {
            LOG.info("------> Bundles in executeImpl - attrs :{0}", attrs.toString());
        }

        // -------------------------------------------------
        // Retrieve DN
        // -------------------------------------------------
        final Name nameAttr = AttributeUtil.getNameFromAttributes(attrs);

        if (nameAttr == null) {
            throw new IllegalArgumentException("No Name attribute provided in the attributes");
        }

        final Attribute cnAttr = AttributeUtil.find(ADConfiguration.CN_NAME, attrs);
        if (cnAttr != null) {
            attrs.remove(cnAttr);
        }

        final ADUtilities utils = new ADUtilities(conn);

        Name name;
        Uid uid = AttributeUtil.getUidAttribute(attrs);
        final Attribute parentUID = AttributeUtil.find(LdapConfiguration.PARENT_DN_NAME, attrs);

        if (ADUtilities.isDN(nameAttr.getNameValue())) {
            name = nameAttr;
        } else {
            if (uid == null && StringUtil.isNotBlank(nameAttr.getNameValue())) {
                uid = new Uid(nameAttr.getNameValue());
                attrs.add(uid);
            }

            if (StringUtils.isBlank((String) AttributeUtil.getSingleValue(parentUID))) {
                name = new Name(utils.getDN(oclass, nameAttr, cnAttr));
            } else {
                name = new Name(utils.getDNHavaParentOrg(oclass, parentUID, nameAttr));
            }

        }
        // -------------------------------------------------

        // -------------------------------------------------
        // Add gid/uidAttribute if missing and if value is available
        // -------------------------------------------------
        final String idAttrName;
        if (ObjectClass.ACCOUNT.equals(oclass)) {
            idAttrName = conn.getConfiguration().getUidAttribute();
        } else if (ObjectClass.GROUP.equals(oclass)) {
            idAttrName = conn.getConfiguration().getGidAttribute();
        } else {
            idAttrName = ADConfiguration.class.cast(conn.getConfiguration()).getDefaultIdAttribute();
        }

        final Attribute idAttr = AttributeUtil.find(idAttrName, attrs);

        if ((idAttr == null || CollectionUtil.isEmpty(idAttr.getValue())) && uid != null) {
            if (oclass.getObjectClassValue().contains("ORG")) {
                LOG.info("----> Create ORG , NO CN ");
            } else {
                attrs.add(AttributeBuilder.build(idAttrName, uid.getUidValue()));
            }

        }
        // -------------------------------------------------

        List<String> ldapGroups = null;

        String primaryGroupDN = null;

        ADGuardedPasswordAttribute pwdAttr = null;

        final BasicAttributes adAttrs = new BasicAttributes(true);

        int uacValue = -1;

        Boolean uccp = null;

        List<Object> memberList = new LinkedList<>();

        for (Attribute attr : attrs) {

            if (attr.is(Name.NAME)) {
                // Handled already.
            } else if (attr.is(ADConfiguration.UCCP_FLAG)) {
                final List<Object> value = attr.getValue();
                if (value != null && !value.isEmpty()) {
                    uccp = (Boolean) value.get(0);
                }
            } else if (attr.is(ADConfiguration.PRIMARY_GROUP_DN_NAME)) {
                final List<Object> value = attr.getValue();
                primaryGroupDN = value == null || value.isEmpty() ? null : String.class.cast(value.get(0));
            } else if (attr.is(ADConfiguration.PROMPT_USER_FLAG)) {
                final List<Object> value = attr.getValue();
                if (value != null && !value.isEmpty() && (Boolean) value.get(0)) {
                    adAttrs.put(
                            new BasicAttribute(ADConfiguration.PROMPT_USER_FLAG, ADConfiguration.PROMPT_USER_VALUE));
                }
            } else if (attr.is(ADConfiguration.LOCK_OUT_FLAG)) {
                final List<Object> value = attr.getValue();
                if (value != null && !value.isEmpty() && (Boolean) value.get(0)) {
                    adAttrs.put(
                            new BasicAttribute(ADConfiguration.LOCK_OUT_FLAG, ADConfiguration.LOCK_OUT_DEFAULT_VALUE));
                }
            } else if (LdapConstants.isLdapGroups(attr.getName())) {

                ldapGroups = checkedListByFilter(nullAsEmpty(attr.getValue()), String.class);

            } else if (attr.is(OperationalAttributes.PASSWORD_NAME)) {

                pwdAttr = ADGuardedPasswordAttribute.create(conn.getConfiguration().getPasswordAttribute(), attr);

            } else if (attr.is(UACCONTROL_ATTR) && oclass.is(ObjectClass.ACCOUNT_NAME)) {
                uacValue = attr.getValue() == null || attr.getValue().isEmpty()
                        ? -1
                        : Integer.parseInt(attr.getValue().get(0).toString());
            } else if (attr.is(OperationalAttributes.ENABLE_NAME)
                    && oclass.is(ObjectClass.ACCOUNT_NAME)
                    && uacValue == -1) {

                if (attr.getValue() == null
                        || attr.getValue().isEmpty()
                        || Boolean.parseBoolean(attr.getValue().get(0).toString())) {
                    uacValue = UF_NORMAL_ACCOUNT;
                } else {
                    uacValue = UF_NORMAL_ACCOUNT + UF_ACCOUNTDISABLE;
                }
            } else if (attr.is(OBJECTGUID)) {
                // ignore info
            } else {

                if (attr.getName().equals("__NAME__")) {
                    Attribute _NAME_Attr = AttributeUtil.find("__NAME__", attrs);
                    // TODO
                } else if (attr.getName().equals("__UID__")) {
                    // TODO
                } else {
                    javax.naming.directory.Attribute ldapAttr = conn.getSchemaMapping().encodeAttribute(oclass, attr);

                    // Do not send empty attributes.
                    if (ldapAttr != null && ldapAttr.size() > 0) {
                        adAttrs.put(ldapAttr);
                    }
                }
            }
        }

        final String pwdAttrName = conn.getConfiguration().getPasswordAttribute();

        if (oclass.is(ObjectClass.ACCOUNT_NAME)) {
            if (pwdAttr != null) {
                pwdAttr.access(new Accessor() {

                    @Override
                    public void access(BasicAttribute attr) {
                        try {
                            if (attr.get() != null && !attr.get().toString().isEmpty()) {
                                adAttrs.put(attr);
                            }
                        } catch (NamingException e) {
                            LOG.error(e, "Error retrieving password value");
                        }
                    }
                });
            }
            if (adAttrs.get(pwdAttrName) != null) {
                adAttrs.put("userAccountControl", Integer.toString(UF_NORMAL_ACCOUNT));
            } else {
                adAttrs.put("userAccountControl", Integer.toString(UF_NORMAL_ACCOUNT + UF_ACCOUNTDISABLE));
            }
        }
        if (LOG.isOk()) {
            LOG.info("------> Bundles logic create - oclass :{0} -- attrs :{1}", oclass.getObjectClassValue(),
                    adAttrs.toString());
            LOG.info("------> Create Object name is :{0}", name);
        }

        final String entryDN = conn.getSchemaMapping().create(oclass, name, adAttrs);

        LOG.info("------> create ok , entryDN is :{0}", entryDN);


        if (uccp != null) {
            // ---------------------------------
            // Change ntSecurityDescriptor
            // ---------------------------------
            conn.getInitialContext().modifyAttributes(entryDN, new ModificationItem[]{
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE, utils.userCannotChangePassword(entryDN, uccp))});

            // ---------------------------------
        }

        if (!isEmpty(ldapGroups)) {
            //            groupHelper.addLdapGroupMemberships(entryDN, ldapGroups);
            // ADD TO GROUP : AD MUST GOUP ADD MEMBER , USER CAN NOT ADD MEMBER OF
            addUserToMemberGroup(entryDN, ldapGroups, conn);
        }

        if (StringUtil.isNotBlank(primaryGroupDN)) {
            // ---------------------------------
            // Change primaryGroupID
            // ---------------------------------
            conn.getInitialContext().modifyAttributes(entryDN, new ModificationItem[]{
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE, utils.getGroupID(primaryGroupDN))});

            // ---------------------------------
        }

        LOG.info("------> back uid , object can set :{0}",
                OBJECTGUID.equals(conn.getSchemaMapping().getLdapUidAttribute(oclass)));

        if (OBJECTGUID.equals(conn.getSchemaMapping().getLdapUidAttribute(oclass))) {
            final Attributes profile = conn.getInitialContext().getAttributes(entryDN, new String[]{OBJECTGUID});
            return new Uid(GUID.getGuidAsString((byte[]) profile.get(OBJECTGUID).get()));
        } else {
            return conn.getSchemaMapping().createUid(oclass, entryDN);
        }
    }

    /**
     * after add user , need add member of
     *
     * @param entryDN         AD 域创建后DN
     * @param groupObjectGUID 上级组唯一ID
     */

    public boolean addUserToMemberGroup(String entryDN, List<String> groupObjectGUID, ADConnection connection) {
        LOG.info("--> 用户加入组织组操作:{0} -- {1}", entryDN, groupObjectGUID);
        if (oclass.is(ObjectClass.ACCOUNT_NAME) && entryDN != null) {
            if (groupObjectGUID != null) {

                groupObjectGUID.stream().forEach(groupID -> {
                    String dn2 = ADUtilities.getDnByGuid((String) groupID, connection);
                    ADUtilities.updateFiledByName(DirContext.ADD_ATTRIBUTE, connection, dn2, "member", entryDN);
                });
            }
            return true;
        }
        return false;
    }
}
