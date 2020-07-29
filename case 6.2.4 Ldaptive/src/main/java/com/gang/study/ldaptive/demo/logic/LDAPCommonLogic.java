package com.gang.study.ldaptive.demo.logic;

import com.gang.study.ldaptive.demo.utils.LdapUtils;
import lombok.val;

import java.util.Collections;

/**
 * @Classname LDAPCommonLogic
 * @Description TODO
 * @Date 2020/7/24 14:47
 * @Created by zengzg
 */
public class LDAPCommonLogic {

    public void doExcutor() {
        val filter = LdapUtils.newLdaptiveSearchFilter(ldapProperties.getSearchFilter(),
                LdapUtils.LDAP_SEARCH_FILTER_DEFAULT_PARAM_NAME, Collections.singletonList(credential.getUsername()));
        LOGGER.trace("Constructed LDAP filter [{}] to locate user and update password", filter);

        val response = LdapUtils.executeSearchOperation(searchFactory, ldapProperties.getBaseDn(), filter, this.ldapProperties.getPageSize());
        LOGGER.debug("LDAP response is [{}]", response);

    }
}
