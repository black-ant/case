package com.gang.adbundles.demo.config;

import net.tirasa.connid.bundles.ad.ADConfiguration;
import net.tirasa.connid.bundles.ad.search.ADDefaultSearchStrategy;
import net.tirasa.connid.bundles.ad.util.ADUtilities;
import net.tirasa.connid.bundles.ldap.LdapConfiguration;
import net.tirasa.connid.bundles.ldap.commons.LdapConstants;
import net.tirasa.connid.bundles.ldap.commons.ObjectClassMappingConfig;
import net.tirasa.connid.bundles.ldap.search.DefaultSearchStrategy;
import org.identityconnectors.common.CollectionUtil;
import org.identityconnectors.common.StringUtil;
import org.identityconnectors.common.security.GuardedString;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.ObjectClassUtil;
import org.identityconnectors.framework.spi.ConfigurationProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/5/25 22:59
 * @Version 1.0
 **/
public class ADConfiguration extends LdapCon {

    public static final int Exchange_DEFAULT_PORT = 636;

    private final Logger LOG = LoggerFactory.getLogger(ADConfiguration.class);

    private boolean isvip;

    private int ServerNumber;

    private int vipServerCount;

    private int generalServerCount;

    private String exchangeserip;

    private int exchangeport = Exchange_DEFAULT_PORT;

    private String exchangeuser;

    private String exchangehost;

    private GuardedString exchangepassword;

    private boolean ssl = true;

    private boolean retrieveDeletedUser = true;

    private boolean retrieveDeletedGroup = true;

    private boolean retrieveDeletedOrganization = true;

    public static final String PROMPT_USER_FLAG = "pwdLastSet";

    public static final String PROMPT_USER_VALUE = "0";

    public static final String LOCK_OUT_FLAG = "lockoutTime";

    public static final String LOCK_OUT_DEFAULT_VALUE = "0";

    public static final String UCCP_FLAG = "userCannotChangePassword";

    public static final String OBJECTGUID = "objectGUID";

    public static final String DISTINGUISHEDNAME="distinguishedName";

    public static final String PRIMARY_GROUP_DN_NAME = "primaryGroupDN";

    private List<String> memberships;

    private boolean membershipConservativePolicy;

    private boolean trustAllCerts;

    private boolean membershipsInOr = false;

    private boolean startSyncFromToday = true;
    private SearchScope userSearchScope;

    private SearchScope groupSearchScope;

    private String groupSearchFilter;

    private String[] groupBaseContexts = {};

    private String[] userBaseContexts = {};

    private String groupMemberReferenceAttribute = "member";

    private String groupOwnerReferenceAttribute = "managedBy";

    private boolean pwdUpdateOnly = false;

    private String defaultIdAttribute = OBJECTGUID;

    private final ObjectClassMappingConfig accountConfig = new ObjectClassMappingConfig(
            ObjectClass.ACCOUNT,
            CollectionUtil.newList("top", "person", "organizationalPerson", "user"),
            false,
            CollectionUtil.newList("cn","sAMAccountName"),
            LdapConstants.PASSWORD);

    private final ObjectClassMappingConfig groupConfig = new ObjectClassMappingConfig(
            ObjectClass.GROUP,
            CollectionUtil.newList("top", "group"),
            false,
            CollectionUtil.newList("cn","sAMAccountName"));

    //TODO LADP Organization
    private final ObjectClassMappingConfig organizationConfig = new ObjectClassMappingConfig(
            new ObjectClass(ObjectClassUtil.createSpecialName("ORGANIZATION")),
            CollectionUtil.newList("top", "organizationalUnit"),
            true,
            CollectionUtil.newList("ou"));

    private final ObjectClassMappingConfig allConfig = new ObjectClassMappingConfig(
            ObjectClass.ALL,
            CollectionUtil.newList("top"),
            false,
            CollectionUtil.newList("cn"));

    public ADConfiguration() {
        super();

        super.setAccountUserNameAttributes("sAMAccountName");

        setUidAttribute("sAMAccountName");
        setGidAttribute("sAMAccountName");
        setOidAttribute("ou");
        setDefaultIdAttribute("cn");
        setDnAttribute(DISTINGUISHEDNAME);
        setSynchronizePasswords(false);

        setObjectClassesToSynchronize(new String[] { "user" });
        setGroupMemberAttribute("member");

        setPasswordAttribute("unicodePwd");
        setPort(636);
        setNameMacros(ObjectClass.ACCOUNT_NAME + ":"+OBJECTGUID, ObjectClass.GROUP_NAME + ":"+OBJECTGUID,
                __ORGANIZATION__ + ":"+OBJECTGUID);

        memberships = new ArrayList<String>();

        userSearchScope = SearchScope.subtree;
        groupSearchScope = SearchScope.subtree;
    }

    @Override
    public DefaultSearchStrategy newDefaultSearchStrategy(boolean ignoreNonExistingBaseDN) {
        return new ADDefaultSearchStrategy(ignoreNonExistingBaseDN);
    }



    @ConfigurationProperty(displayMessageKey = "servernumber.display",
            helpMessageKey = "servernumber.help",order = 1)
    public int getServerNumber() {
        return ServerNumber;
    }

    public void setServerNumber(int serverNumber) {
        ServerNumber = serverNumber;
    }

    @ConfigurationProperty(displayMessageKey = "isvip.display",
            helpMessageKey = "isvip.help",order = 1)
    public boolean isIsvip() {
        return isvip;
    }

    public void setIsvip(boolean isvip) {
        this.isvip = isvip;
    }

    @ConfigurationProperty(displayMessageKey = "vipservercount.display",
            helpMessageKey = "vipservercount.help",order = 1)
    public int getVipServerCount() {
        return vipServerCount;
    }

    public void setVipServerCount(int vipServerCount) {
        this.vipServerCount = vipServerCount;
    }

    @ConfigurationProperty(displayMessageKey = "generalservercount.display",
            helpMessageKey = "generalservercount.help",order = 1)
    public int getGeneralServerCount() {
        return generalServerCount;
    }

    public void setGeneralServerCount(int generalServerCount) {
        this.generalServerCount = generalServerCount;
    }

    @ConfigurationProperty(displayMessageKey = "exchangeserip.display",
            helpMessageKey = "exchangeserip.help",order = 2)
    public String getExchangeserip() {
        return exchangeserip;
    }

    public void setExchangeserip(String exchangeserip) {
        this.exchangeserip = exchangeserip;
    }

    @ConfigurationProperty(displayMessageKey = "exchangeport.display",
            helpMessageKey = "exchangeport.help",order = 2)
    public int getExchangeport() {
        return exchangeport;
    }

    public void setExchangeport(int exchangeport) {
        this.exchangeport = exchangeport;
    }

    @ConfigurationProperty(displayMessageKey = "exchangeuser.display",
            helpMessageKey = "exchangeuser.help",order = 2)
    public String getExchangeuser() {
        return exchangeuser;
    }

    public void setExchangeuser(String exchangeuser) {
        this.exchangeuser = exchangeuser;
    }

    @ConfigurationProperty(displayMessageKey = "exchangehost.display",
            helpMessageKey = "exchangehost.help",order = 2)
    public String getExchangehost() {
        return exchangehost;
    }

    public void setExchangehost(String exchangehost) {
        this.exchangehost = exchangehost;
    }


    @ConfigurationProperty(confidential = true,displayMessageKey = "exchangepassword.display",
            helpMessageKey = "exchangepassword.help",order = 2)
    public GuardedString getExchangepassword() {
        return exchangepassword;
    }

    public void setExchangepassword(GuardedString exchangepassword) {
        this.exchangepassword = exchangepassword != null
                ? exchangepassword.copy()
                : null;
    }

    @Override
    @ConfigurationProperty(displayMessageKey = "ssl.display",
            helpMessageKey = "ssl.help",order = 1)
    public boolean isSsl() {
        return ssl;
    }

    @Override
    public void setSsl(final boolean ssl) {
        super.setSsl(ssl);
        this.ssl = ssl;
    }

    @Override
    public Map<ObjectClass, ObjectClassMappingConfig> getObjectClassMappingConfigs() {
        HashMap<ObjectClass, ObjectClassMappingConfig> result = new HashMap<ObjectClass, ObjectClassMappingConfig>();
        result.put(accountConfig.getObjectClass(), accountConfig);
        result.put(groupConfig.getObjectClass(), groupConfig);

        //TODO LDAP Organization
        result.put(organizationConfig.getObjectClass(), organizationConfig);
        allConfig.setShortNameLdapAttributes(CollectionUtil.newList(getDefaultIdAttribute()));
        result.put(allConfig.getObjectClass(), allConfig);
        return result;
    }

    @ConfigurationProperty(displayMessageKey = "memberships.display",
            helpMessageKey = "memberships.help",order = 2)
    public String[] getMemberships() {
        return memberships.toArray(new String[memberships.size()]);
    }

    public void setMemberships(final String... memberships) {
        this.memberships = new ArrayList<String>();

        if (memberships != null) {
            for (String membership : memberships) {
                if (ADUtilities.isDNValue(membership)) {
                    this.memberships.add(membership.trim());
                } else {
                    LOG.warn("Skip membership! \"{0}\" is not a valid distinguished name (DN)", membership);
                }
            }
        }
    }

    @ConfigurationProperty(displayMessageKey = "retrieveDeletedUser.display",
            helpMessageKey = "retrieveDeletedUser.help",order = 3)
    public boolean isRetrieveDeletedUser() {
        return retrieveDeletedUser;
    }

    public void setRetrieveDeletedUser(boolean retrieveDeletedUser) {
        this.retrieveDeletedUser = retrieveDeletedUser;
    }

    @ConfigurationProperty(displayMessageKey = "retrieveDeletedGroup.display",
            helpMessageKey = "retrieveDeletedGroup.help",order = 4)
    public boolean isRetrieveDeletedGroup() {
        return this.retrieveDeletedGroup;
    }

    public void setRetrieveDeletedGroup(boolean retrieveDeletedGroup) {
        this.retrieveDeletedGroup = retrieveDeletedGroup;
    }

    @ConfigurationProperty(displayMessageKey = "retrieveDeletedOrganization.display",
            helpMessageKey = "retrieveDeletedOrganization.help",order = 5)
    public boolean isRetrieveDeletedOrganization() {
        return this.retrieveDeletedOrganization;
    }

    public void setRetrieveDeletedOrganization(boolean retrieveDeletedOrganization) {
        this.retrieveDeletedOrganization = retrieveDeletedOrganization;
    }

    @ConfigurationProperty(displayMessageKey = "trustAllCerts.display",
            helpMessageKey = "trustAllCerts.help",order = 6)
    public boolean isTrustAllCerts() {
        return trustAllCerts;
    }

    public void setTrustAllCerts(final boolean trustAllCerts) {
        this.trustAllCerts = trustAllCerts;
    }

    public boolean isMembershipsInOr() {
        return membershipsInOr;
    }

    @ConfigurationProperty(displayMessageKey = "membershipsInOr.display",
            helpMessageKey = "membershipsInOr.help",order = 7)
    public void setMembershipsInOr(boolean membershipsInOr) {
        this.membershipsInOr = membershipsInOr;
    }

    @ConfigurationProperty(order = 8, required = true,
            displayMessageKey = "baseContextsToSynchronize.display",
            helpMessageKey = "baseContextsToSynchronize.help")
    @Override
    public String[] getBaseContextsToSynchronize() {
        return super.getBaseContextsToSynchronize();
    }

    @Override
    public void setBaseContextsToSynchronize(String... baseContextsToSynchronize) {
        super.setBaseContextsToSynchronize(baseContextsToSynchronize);
    }

    @ConfigurationProperty(displayMessageKey = "userSearchScope.display",
            helpMessageKey = "userSearchScope.help",order = 9)
    public String getUserSearchScope() {
        return userSearchScope == null ? SearchScope.subtree.toString() : userSearchScope.toString();
    }

    public void setUserSearchScope(final String userSearchScope) {
        this.userSearchScope = SearchScope.valueOf(userSearchScope.toLowerCase());
    }

    @ConfigurationProperty(displayMessageKey = "groupSearchScope.display",
            helpMessageKey = "groupSearchScope.help",order = 10)
    public String getGroupSearchScope() {
        return groupSearchFilter == null ? SearchScope.subtree.toString() : groupSearchScope.toString();
    }

    public void setGroupSearchScope(final String groupSearchScope) {
        this.groupSearchScope = SearchScope.valueOf(groupSearchScope.toLowerCase());
    }

    @ConfigurationProperty(displayMessageKey = "groupSearchFilter.display",
            helpMessageKey = "groupSearchFilter.help",order = 11)
    @Override
    public String getGroupSearchFilter() {
        return groupSearchFilter;
    }

    @Override
    public void setGroupSearchFilter(String groupSearchFilter) {
        this.groupSearchFilter = groupSearchFilter;
    }

    @ConfigurationProperty(displayMessageKey = "groupBaseContexts.display",
            helpMessageKey = "groupBaseContexts.help",order = 12)
    public String[] getGroupBaseContexts() {
        if (groupBaseContexts != null && groupBaseContexts.length > 0) {
            // return specified configuration
            return groupBaseContexts.clone();
        } else {
            // return root suffixes
            return getBaseContextsToSynchronize();
        }
    }

    public void setGroupBaseContexts(String... baseContexts) {
        this.groupBaseContexts = baseContexts;
        // update base context everytime ...
        super.setBaseContexts(this.getBaseContexts());
    }

    @ConfigurationProperty(displayMessageKey = "userBaseContexts.display",
            helpMessageKey = "userBaseContexts.help",order = 13)
    public String[] getUserBaseContexts() {
        if (userBaseContexts != null && userBaseContexts.length > 0) {
            // return specified configuration
            return userBaseContexts.clone();
        } else {
            // return root suffixes
            return getBaseContextsToSynchronize();
        }
    }

    //TODO LDAP Organization
    private String[] organiztionBaseContexts = {};

    @ConfigurationProperty(displayMessageKey = "organiztionBaseContexts.display",
            helpMessageKey = "organiztionBaseContexts.help",order = 14)
    public String[] getOrganiztionBaseContexts() {

        if (organiztionBaseContexts != null && organiztionBaseContexts.length > 0) {
            // return specified configuration
            return organiztionBaseContexts;
        } else {
            // return root suffixes
            return getBaseContextsToSynchronize();
        }
    }

    public void setOrganiztionBaseContexts(String... baseContexts) {
        this.organiztionBaseContexts = baseContexts;
        // update base context everytime ...
        //super.setBaseContexts(this.getBaseContexts());
    }

    public void setUserBaseContexts(final String... baseContexts) {
        this.userBaseContexts = baseContexts;
        // update base context everytime ...
        ///super.setBaseContexts(this.getBaseContexts());
    }

    @Override
    public String[] getBaseContexts() {
        // return root suffixes
        return this.getBaseContextsToSynchronize();
    }

    @ConfigurationProperty(displayMessageKey = "groupMemberReferenceAttribute.display",
            helpMessageKey = "groupMemberReferenceAttribute.help",order = 15)
    public String getGroupMemberReferenceAttribute() {
        return StringUtil.isBlank(groupMemberReferenceAttribute) ? "member" : groupMemberReferenceAttribute;
    }

    public void setGroupMemberReferenceAttribute(String groupMemberReferenceAttribute) {
        this.groupMemberReferenceAttribute = groupMemberReferenceAttribute;
    }

    @ConfigurationProperty(displayMessageKey = "groupOwnerReferenceAttribute.display",
            helpMessageKey = "groupOwnerReferenceAttribute.help",order = 16)
    public String getGroupOwnerReferenceAttribute() {
        return StringUtil.isBlank(groupOwnerReferenceAttribute) ? "managedBy" : groupOwnerReferenceAttribute;
    }

    public void setGroupOwnerReferenceAttribute(String groupOwnerReferenceAttribute) {
        this.groupOwnerReferenceAttribute = groupOwnerReferenceAttribute;
    }

    @ConfigurationProperty(displayMessageKey = "startSyncFromToday.display",
            helpMessageKey = "startSyncFromToday.help",order = 17)
    public boolean isStartSyncFromToday() {
        return startSyncFromToday;
    }

    public void setStartSyncFromToday(boolean startSyncFromToday) {
        this.startSyncFromToday = startSyncFromToday;
    }

    public boolean isPwdUpdateOnly() {
        return pwdUpdateOnly;
    }

    @ConfigurationProperty(displayMessageKey = "pwdUpdateOnly.display",
            helpMessageKey = "pwdUpdateOnly.help", required = true,order = 18)
    public void setPwdUpdateOnly(boolean pwdUpdateOnly) {
        this.pwdUpdateOnly = pwdUpdateOnly;
    }

    @ConfigurationProperty(displayMessageKey = "membershipConservativePolicy.display",
            helpMessageKey = "membershipConservativePolicy.help",order = 19)
    public boolean isMembershipConservativePolicy() {
        return membershipConservativePolicy;
    }

    public void setMembershipConservativePolicy(boolean membershipConservativePolicy) {
        this.membershipConservativePolicy = membershipConservativePolicy;
    }

    @ConfigurationProperty(order = 20,
            displayMessageKey = "defaultIdAttribute.display",
            helpMessageKey = "defaultIdAttribute.help")
    public String getDefaultIdAttribute() {
        return defaultIdAttribute;
    }




    @ConfigurationProperty(order = 21,
            displayMessageKey = "accountUserNameAttributes.display",
            helpMessageKey = "accountUserNameAttributes.help")
    @Override
    public String[] getAccountUserNameAttributes() {
        List<String> shortNameLdapAttributes = accountConfig.getShortNameLdapAttributes();
        return shortNameLdapAttributes.toArray(new String[shortNameLdapAttributes.size()]);
    }

    @Override
    public void setAccountUserNameAttributes(String... accountUserNameAttributes) {
        accountConfig.setShortNameLdapAttributes(Arrays.asList(accountUserNameAttributes));
    }


    @ConfigurationProperty(order = 22,
            displayMessageKey = "groupNameAttributes.display",
            helpMessageKey = "groupNameAttributes.help")
    @Override
    public String[] getGroupNameAttributes() {
        List<String> shortNameLdapAttributes = groupConfig.getShortNameLdapAttributes();
        return shortNameLdapAttributes.toArray(new String[shortNameLdapAttributes.size()]);
    }

    @Override
    public void setGroupNameAttributes(String... groupNameAttributes) {
        groupConfig.setShortNameLdapAttributes(Arrays.asList(groupNameAttributes));
    }



    @ConfigurationProperty(order = 23,required=true,
            displayMessageKey = "organizationNameAttributes.display",
            helpMessageKey = "organizationNameAttributes.help")
    @Override
    public String[] getOrganizationNameAttributes() {
        List<String> shortNameLdapAttributes = organizationConfig.getShortNameLdapAttributes();
        return shortNameLdapAttributes.toArray(new String[shortNameLdapAttributes.size()]);
    }

    @Override
    public void setOrganizationNameAttributes(String... organizationUserNameAttributes) {
        organizationConfig.setShortNameLdapAttributes(Arrays.asList(organizationUserNameAttributes));
    }



    @ConfigurationProperty(order = 24,
            displayMessageKey = "defaultNameAttributes.display",
            helpMessageKey = "defaultNameAttributes.help")
    @Override
    public String[] getDefaultNameAttributes() {
        List<String> shortNameLdapAttributes = allConfig.getShortNameLdapAttributes();
        return shortNameLdapAttributes.toArray(new String[shortNameLdapAttributes.size()]);
    }

    @Override
    public void setDefaultNameAttributes(String... defaultUserNameAttributes) {
        allConfig.setShortNameLdapAttributes(Arrays.asList(defaultUserNameAttributes));
    }


    @Override
    public String[] getObjectClassBaseContexts(ObjectClass oclass) {
        return oclass.is(ObjectClass.ACCOUNT_NAME)
                ? getUserBaseContexts()
                : oclass.is(ObjectClass.GROUP_NAME)
                ? getGroupBaseContexts()
                : oclass.is(LdapConfiguration.__ORGANIZATION__)
                ?getOrganiztionBaseContexts()
                :getObjectClassBaseContexts(oclass);
    }

    public String getObjectSearchFilter(ObjectClass oclass) {
        return  oclass.equals(ObjectClass.ACCOUNT)
                ? getAccountSearchFilter()
                : oclass.equals(ObjectClass.GROUP)
                ? getGroupSearchFilter()
                : oclass.equals(new ObjectClass(ObjectClassUtil.createSpecialName("ORGANIZATION")))
                ? getOrganizationSearchFilter()
                : "";
    }


    @Override
    public String[] getObjectClassNameAttr(ObjectClass oclass) {
        return oclass.is(ObjectClass.ACCOUNT_NAME)?
                getAccountUserNameAttributes()
                :(oclass.is(ObjectClass.GROUP_NAME)
                ?getGroupNameAttributes()
                :(oclass.is(LdapConfiguration.__ORGANIZATION__)
                ?getOrganizationNameAttributes()
                :getDefaultNameAttributes()));
    }


    public void setDefaultIdAttribute(final String defaultIdAttribute) {
        this.defaultIdAttribute = defaultIdAttribute;
    }

    @Override
    public final void setUidAttribute(final String uidAttribute) {
        super.setUidAttribute(uidAttribute);
    }

    @Override
    public final void setGidAttribute(final String gidAttribute) {
        super.setGidAttribute(gidAttribute);
    }

    @Override
    public final void setOidAttribute(final String oidAttribute) {
        super.setOidAttribute(oidAttribute);
    }

    public enum SearchScope {

        object,
        onelevel,
        subtree

    }

    private String defaultPeopleContainer;

    private String defaultGroupContainer;

    private String defaultOrganizationContainer;

    @ConfigurationProperty(displayMessageKey = "defaultPeopleContainer.display",
            helpMessageKey = "defaultPeopleContainer.help",order = 25)
    public String getDefaultPeopleContainer() {
        if (StringUtil.isBlank(defaultPeopleContainer)) {
            return getBaseContextsToSynchronize() == null || getBaseContextsToSynchronize().length < 1
                    ? null : getBaseContextsToSynchronize()[0];
        } else {
            return defaultPeopleContainer;
        }
    }

    public void setDefaultPeopleContainer(String defaultPeopleContainer) {
        this.defaultPeopleContainer = defaultPeopleContainer;
    }

    @ConfigurationProperty(displayMessageKey = "defaultGroupContainer.display",
            helpMessageKey = "defaultGroupContainer.help",order = 26)
    public String getDefaultGroupContainer() {
        if (StringUtil.isBlank(defaultGroupContainer)) {
            return getBaseContextsToSynchronize() == null || getBaseContextsToSynchronize().length < 1
                    ? null : getBaseContextsToSynchronize()[0];
        } else {
            return defaultGroupContainer;
        }
    }

    public void setDefaultGroupContainer(String defaultGroupContainer) {
        this.defaultGroupContainer = defaultGroupContainer;
    }


    public void setDefaultOrganizationContainer(String defaultOrganizationContainer) {
        this.defaultOrganizationContainer=defaultOrganizationContainer;
    }

    @ConfigurationProperty(order = 27,
            displayMessageKey = "defaultOrganizationContainer.display",
            helpMessageKey = "defaultOrganizationContainer.help")
    public String getDefaultOrganizationContainer() {
        if (StringUtil.isBlank(defaultOrganizationContainer)) {
            return getBaseContextsToSynchronize() == null || getBaseContextsToSynchronize().length < 1
                    ? null : getBaseContextsToSynchronize()[0];
        } else {
            return defaultOrganizationContainer;
        }
    }

    public String getObjectClassBaseContainer(ObjectClass oclass) {
        return ((oclass.is(ObjectClass.ACCOUNT_NAME)
                ? getDefaultPeopleContainer()
                : oclass.is(ObjectClass.GROUP_NAME)
                ? getDefaultGroupContainer()
                : (oclass.is(LdapConfiguration.__ORGANIZATION__)
                ? getDefaultOrganizationContainer()
                : getBaseContexts()[0])));
    }

}
