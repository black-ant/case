package com.gang.study.ldap.demo.to;

/**
 * @Classname DefaultProperties
 * @Description TODO
 * @Date 2020/8/7 22:04
 * @Created by zengzg
 */
public class DefaultProperties {

    public static final String LDAP_CTX_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    public static final String LDAP_CTX_SOCKET_FACTORY = "java.naming.ldap.factory.socket";
    public static final String LDAP_BINARY_ATTRIBUTE = "java.naming.ldap.attributes.binary";
    public static final String OBJECTGUID = "objectGUID";
    public static final String ENTRYUUID = "entryUUID";
    public static final String DNPARAMNAME = "distinguishedName";
    public static final String OBJECTSID = "objectSID";
    public static final String PRIMARYGROUPID = "primaryGroupID";
    public static final String MEMBEROF = "memberOf";
    public static final String UACCONTROL_ATTR = "userAccountControl";
    public static final String SDDL_ATTR = "ntSecurityDescriptor";

    public static final String EXCHANGE_PHOTO = "thumbnailPhoto";

    public static final String LDAP_GROUPS_NAME = "ldapGroups";
    public static final String LDAP_MEMBER = "member";

    // Name
    public static final String LDAP_OU_NAME = "cn";
    public static final String LDAP_SHOW_NAME = "name";
    public static final String USER_PRINCIPAL_NAME = "userPrincipalName";
    public static final String SAM_ACCOUNT_NAME = "sAMAccountName";
    public static final String DISPLAY_NAME = "displayName";


    public static final String POSIX_GROUPS_NAME = "posixGroups";
    public static final String LDAP_UID_ATTRS_NAME = "ldap_uid_attributes";
    public static final String SEARCH_FILTER_NAME = "searchFilter";
    public static final String LDAP_PARENT_GROUP_NAME = "memberOf";
    public static final String LDAP_GROUP_TYPE = "groupType";
    public static final String LDAP_USER_PASSWORD = "userPassword";
    public static final String LDAP_MANAGER = "manager";

    public static final String LDAP_SAMMCCOUNT_NAME = "sAMAccountName";

    public static final String OP_SEARCH_FILTER = "searchFilter";

    public static final String CONNECT_TIMEOUT_ENV_PROP = "com.sun.jndi.ldap.connect.timeout";

    public static final String READ_TIMEOUT_ENV_PROP = "com.sun.jndi.ldap.read.timeout";

    public static final String OUT_PARENT = "out_parent";

    public static final String LDAP_CN = "cn";
    public static final String LDAP_OU = "ou";

    public static final String LDAP_CN_UP = "CN";
    public static final String LDAP_OU_UP = "OU";
    public static final String LDAP_DC_UP = "DC";

    public static final String COUNTRY = "c";
    public static final String COUNTRY_NAME = "co";
    public static final String COUNTRY_CODE = "countryCode";

    public static boolean isLdapGroups(String attrName) {
        return LDAP_GROUPS_NAME.equalsIgnoreCase(attrName);
    }

    public static boolean isPosixGroups(String attrName) {
        return POSIX_GROUPS_NAME.equalsIgnoreCase(attrName);
    }

    private DefaultProperties() {
    }

}
