package com.gang.study.ldap.demo.ldapactive;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.ldap.demo.to.AbstractLdapAuthenticationProperties;
import com.gang.study.ldap.demo.to.AbstractLdapProperties;
import com.gang.study.ldap.demo.to.LdapAuthenticationProperties;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.ldaptive.ActivePassiveConnectionStrategy;
import org.ldaptive.BindConnectionInitializer;
import org.ldaptive.BindRequest;
import org.ldaptive.CompareRequest;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.Credential;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.DefaultConnectionStrategy;
import org.ldaptive.DnsSrvConnectionStrategy;
import org.ldaptive.LdapAttribute;
import org.ldaptive.RandomConnectionStrategy;
import org.ldaptive.ReturnAttributes;
import org.ldaptive.RoundRobinConnectionStrategy;
import org.ldaptive.SearchFilter;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchScope;
import org.ldaptive.ad.extended.FastBindOperation;
import org.ldaptive.ad.handler.ObjectGuidHandler;
import org.ldaptive.ad.handler.ObjectSidHandler;
import org.ldaptive.ad.handler.PrimaryGroupIdHandler;
import org.ldaptive.ad.handler.RangeEntryHandler;
import org.ldaptive.auth.AuthenticationRequest;
import org.ldaptive.auth.AuthenticationResponse;
import org.ldaptive.auth.Authenticator;
import org.ldaptive.auth.EntryResolver;
import org.ldaptive.auth.PooledBindAuthenticationHandler;
import org.ldaptive.auth.PooledCompareAuthenticationHandler;
import org.ldaptive.auth.PooledSearchDnResolver;
import org.ldaptive.auth.PooledSearchEntryResolver;
import org.ldaptive.control.PasswordPolicyControl;
import org.ldaptive.handler.CaseChangeEntryHandler;
import org.ldaptive.handler.DnAttributeEntryHandler;
import org.ldaptive.handler.MergeAttributeEntryHandler;
import org.ldaptive.handler.RecursiveEntryHandler;
import org.ldaptive.handler.SearchEntryHandler;
import org.ldaptive.pool.BindPassivator;
import org.ldaptive.pool.BlockingConnectionPool;
import org.ldaptive.pool.ClosePassivator;
import org.ldaptive.pool.CompareValidator;
import org.ldaptive.pool.ConnectionPool;
import org.ldaptive.pool.IdlePruneStrategy;
import org.ldaptive.pool.PoolConfig;
import org.ldaptive.pool.PooledConnectionFactory;
import org.ldaptive.pool.SearchValidator;
import org.ldaptive.provider.Provider;
import org.ldaptive.referral.SearchReferralHandler;
import org.ldaptive.sasl.CramMd5Config;
import org.ldaptive.sasl.DigestMd5Config;
import org.ldaptive.sasl.ExternalConfig;
import org.ldaptive.sasl.GssApiConfig;
import org.ldaptive.sasl.QualityOfProtection;
import org.ldaptive.sasl.SaslConfig;
import org.ldaptive.sasl.SecurityStrength;
import org.ldaptive.ssl.KeyStoreCredentialConfig;
import org.ldaptive.ssl.SslConfig;
import org.ldaptive.ssl.X509CredentialConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


/**
 * @Classname LdapAuthService
 * @Description TODO
 * @Date 2020/8/12 16:09
 * @Created by zengzg
 */
@Component
public class LdapAuthService extends BaseLdapActive {

    private static Logger LOGGER = LoggerFactory.getLogger(LdapAuthService.class);

    //    private Authenticator authenticator = ladptiveAuthenticator();

    /**
     * Do Auth
     */
    public void auth() throws Exception {
        //        LOGGER.info("------> this is auth <-------");
        //        Authenticator authenticator = new Authenticator();
        //        AuthenticationRequest authRequest = new AuthenticationRequest(new org.ldaptive.auth.User("devad\\\\administrator"), new org.ldaptive.Credential("123456"));
        //        AuthenticationResponse adResponse = authenticator.authenticate(authRequest);
        //        LOGGER.info("------> this is auth end  <-------");
        //        LOGGER.info("------> adResponse :{} <-------");
        AbstractLdapAuthenticationProperties properties = new LdapAuthenticationProperties();
        properties.setLdapUrl("ldap://192.168.2.75");
        properties.setBaseDn("DC=devad,DC=com,DC=cn");
        properties.setUserFilter("(cn=Sync382)");
        properties.setUseSsl(Boolean.FALSE);
        properties.setBindDn("DC=devad,DC=com,DC=cn");

        Authenticator authenticator = getAuthenticatedOrAnonSearchAuthenticator(properties);

        AuthenticationRequest authRequest = new AuthenticationRequest(new org.ldaptive.auth.User("devad\\\\administrator"), new org.ldaptive.Credential("123456"));
        AuthenticationResponse adResponse = authenticator.authenticate(authRequest);

        LOGGER.info("------> Authenticator :{} <-------", adResponse);
    }



}
