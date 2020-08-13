package com.gang.study.ldap.demo.ldapactive;

import com.gang.study.ldap.demo.to.AbstractLdapAuthenticationProperties;
import com.gang.study.ldap.demo.to.AbstractLdapProperties;
import com.gang.study.ldap.demo.to.LdapAuthenticationProperties;
import org.apache.commons.lang3.StringUtils;
import org.ldaptive.BindRequest;
import org.ldaptive.CompareRequest;
import org.ldaptive.Credential;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapUtils;
import org.ldaptive.ReturnAttributes;
import org.ldaptive.SearchFilter;
import org.ldaptive.SearchRequest;
import org.ldaptive.auth.AuthenticationRequest;
import org.ldaptive.auth.AuthenticationResponse;
import org.ldaptive.auth.Authenticator;
import org.ldaptive.auth.PooledSearchDnResolver;
import org.ldaptive.pool.BindPassivator;
import org.ldaptive.pool.BlockingConnectionPool;
import org.ldaptive.pool.ClosePassivator;
import org.ldaptive.pool.CompareValidator;
import org.ldaptive.pool.ConnectionPool;
import org.ldaptive.pool.IdlePruneStrategy;
import org.ldaptive.pool.PoolConfig;
import org.ldaptive.pool.PooledConnectionFactory;
import org.ldaptive.pool.SearchValidator;
import org.ldaptive.referral.SearchReferralHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.beans.Beans;

/**
 * @Classname LdapNoneAuthService
 * @Description TODO
 * @Date 2020/8/13 10:16
 * @Created by zengzg
 */
@Component
public class LdapNoneAuthService extends BaseLdapActive {

    private static Logger LOGGER = LoggerFactory.getLogger(LdapNoneAuthService.class);

    /**
     *
     */
    public void run() throws Exception {
        AbstractLdapAuthenticationProperties properties = new LdapAuthenticationProperties();
        properties.setLdapUrl("ldap://192.168.2.11");
        properties.setBaseDn("DC=devad,DC=com,DC=cn");
        properties.setConnectTimeout("3000");
        properties.setUserFilter("(cn=Sync382)");
        properties.setUseSsl(Boolean.FALSE);
        properties.setUseStartTls(Boolean.FALSE);
        properties.setBindDn("");
        properties.setBindCredential("");
        properties.setSubtreeSearch(Boolean.TRUE);
        properties.setType(AbstractLdapAuthenticationProperties.AuthenticationTypes.AUTHENTICATED);

        // Test1 :
        properties.setEnhanceWithEntryResolver(Boolean.FALSE);

        Authenticator authenticator = newLdaptiveAuthenticator(properties);
        AuthenticationRequest authRequest = new AuthenticationRequest(new org.ldaptive.auth.User("administrator"), new org.ldaptive.Credential("123456"));

        AuthenticationResponse adResponse = authenticator.authenticate(authRequest);
        LOGGER.info("------> Authenticator :{} <-------", adResponse);
    }

    /**
     * New ldap authenticator.
     *
     * @param l the ldap settings.
     * @return the authenticator
     */
    public static Authenticator newLdaptiveAuthenticator(final AbstractLdapAuthenticationProperties l) {
        switch (l.getType()) {
            //            case AD:
            //                LOGGER.debug("Creating active directory authenticator for [{}]", l.getLdapUrl());
            //                return getActiveDirectoryAuthenticator(l);
            //            case DIRECT:
            //                LOGGER.debug("Creating direct-bind authenticator for [{}]", l.getLdapUrl());
            //                return getDirectBindAuthenticator(l);
            case AUTHENTICATED:
                LOGGER.debug("Creating authenticated authenticator for [{}]", l.getLdapUrl());
                return getAuthenticatedOrAnonSearchAuthenticator(l);
            default:
                LOGGER.debug("Creating anonymous authenticator for [{}]", l.getLdapUrl());
                return getAuthenticatedOrAnonSearchAuthenticator(l);
        }
    }


}
