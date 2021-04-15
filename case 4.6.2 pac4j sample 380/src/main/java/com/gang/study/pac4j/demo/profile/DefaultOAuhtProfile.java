package com.gang.study.pac4j.demo.profile;

import com.gang.study.pac4j.demo.definition.DefaultOAuthDefinition;
import org.pac4j.oauth.profile.OAuth20Profile;
import org.pac4j.oauth.profile.casoauthwrapper.CasOAuthWrapperProfileDefinition;

import java.util.Date;

/**
 * @Classname DefaultOAuhtProfile
 * @Description TODO
 * @Date 2021/4/14
 * @Created by zengzg
 */
public class DefaultOAuhtProfile extends OAuth20Profile {

    private static final long serialVersionUID = 1347249873352825528L;

    public Boolean isFromNewLogin() {
        return (Boolean) getAttribute(DefaultOAuthDefinition.IS_FROM_NEW_LOGIN);
    }

    public Date getAuthenticationDate() {
        return (Date) getAttribute(DefaultOAuthDefinition.AUTHENTICATION_DATE);
    }

    public String getAuthenticationMethod() {
        return (String) getAttribute(DefaultOAuthDefinition.AUTHENTICATION_METHOD);
    }

    public String getSuccessfulAuthenticationHandlers() {
        return (String) getAttribute(DefaultOAuthDefinition.SUCCESSFUL_AUTHENTICATION_HANDLERS);
    }

    public Boolean isLongTermAuthenticationRequestTokenUsed() {
        return (Boolean) getAttribute(DefaultOAuthDefinition.LONG_TERM_AUTHENTICATION_REQUEST_TOKEN_USED);
    }
}
