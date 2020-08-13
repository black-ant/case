package com.gang.study.ldap.demo.ldapactive;

import com.gang.study.ldap.demo.to.AbstractLdapAuthenticationProperties;
import com.gang.study.ldap.demo.to.AbstractLdapProperties;
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

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname BaseLdapActive
 * @Description TODO
 * @Date 2020/8/13 10:23
 * @Created by zengzg
 */
public class BaseLdapActive {

    private static Logger LOGGER = LoggerFactory.getLogger(LdapAuthService.class);

    protected static Authenticator getAuthenticatedOrAnonSearchAuthenticator(final AbstractLdapAuthenticationProperties l) {
        if (StringUtils.isBlank(l.getBaseDn())) {
            throw new IllegalArgumentException("Base dn cannot be empty/blank for authenticated/anonymous authentication");
        }
        if (StringUtils.isBlank(l.getUserFilter())) {
            throw new IllegalArgumentException("User filter cannot be empty/blank for authenticated/anonymous authentication");
        }
        final PooledConnectionFactory connectionFactoryForSearch = newLdaptivePooledConnectionFactory(l);
        final PooledSearchDnResolver resolver = new PooledSearchDnResolver();
        resolver.setBaseDn(l.getBaseDn());
        resolver.setSubtreeSearch(l.isSubtreeSearch());
        resolver.setAllowMultipleDns(l.isAllowMultipleDns());
        resolver.setConnectionFactory(connectionFactoryForSearch);
        resolver.setUserFilter(l.getUserFilter());

        final Authenticator auth;
        if (StringUtils.isBlank(l.getPrincipalAttributePassword())) {
            auth = new Authenticator(resolver, getPooledBindAuthenticationHandler(l, newLdaptivePooledConnectionFactory(l)));
        } else {
            auth = new Authenticator(resolver, getPooledCompareAuthenticationHandler(l, newLdaptivePooledConnectionFactory(l)));
        }

        if (l.isEnhanceWithEntryResolver()) {
            auth.setEntryResolver(newLdaptiveSearchEntryResolver(l, newLdaptivePooledConnectionFactory(l)));
        }
        return auth;
    }

    public static PooledConnectionFactory newLdaptivePooledConnectionFactory(final AbstractLdapProperties l) {
        final ConnectionPool cp = newLdaptiveBlockingConnectionPool(l);
        return new PooledConnectionFactory(cp);
    }

    protected static PooledBindAuthenticationHandler getPooledBindAuthenticationHandler(final AbstractLdapAuthenticationProperties l,
                                                                                        final PooledConnectionFactory factory) {
        final PooledBindAuthenticationHandler handler = new PooledBindAuthenticationHandler(factory);
        handler.setAuthenticationControls(new PasswordPolicyControl());
        return handler;
    }

    protected static PooledCompareAuthenticationHandler getPooledCompareAuthenticationHandler(final AbstractLdapAuthenticationProperties l,
                                                                                              final PooledConnectionFactory factory) {
        final PooledCompareAuthenticationHandler handler = new PooledCompareAuthenticationHandler(factory);
        handler.setPasswordAttribute(l.getPrincipalAttributePassword());
        return handler;
    }

    /**
     * New dn resolver entry resolver.
     * Creates the necessary search entry resolver.
     *
     * @param l       the ldap settings
     * @param factory the factory
     * @return the entry resolver
     */
    public static EntryResolver newLdaptiveSearchEntryResolver(final AbstractLdapAuthenticationProperties l,
                                                               final PooledConnectionFactory factory) {


        final PooledSearchEntryResolver entryResolver = new PooledSearchEntryResolver();
        entryResolver.setBaseDn(l.getBaseDn());
        entryResolver.setUserFilter(l.getUserFilter());
        entryResolver.setSubtreeSearch(l.isSubtreeSearch());
        entryResolver.setConnectionFactory(factory);

        final List<SearchEntryHandler> handlers = new ArrayList<>();
        l.getSearchEntryHandlers().forEach(h -> {
            switch (h.getType()) {
                case CASE_CHANGE:
                    final CaseChangeEntryHandler eh = new CaseChangeEntryHandler();
                    eh.setAttributeNameCaseChange(h.getCasChange().getAttributeNameCaseChange());
                    eh.setAttributeNames(h.getCasChange().getAttributeNames());
                    eh.setAttributeValueCaseChange(h.getCasChange().getAttributeValueCaseChange());
                    eh.setDnCaseChange(h.getCasChange().getDnCaseChange());
                    handlers.add(eh);
                    break;
                case DN_ATTRIBUTE_ENTRY:
                    final DnAttributeEntryHandler ehd = new DnAttributeEntryHandler();
                    ehd.setAddIfExists(h.getDnAttribute().isAddIfExists());
                    ehd.setDnAttributeName(h.getDnAttribute().getDnAttributeName());
                    handlers.add(ehd);
                    break;
                case MERGE:
                    final MergeAttributeEntryHandler ehm = new MergeAttributeEntryHandler();
                    ehm.setAttributeNames(h.getMergeAttribute().getAttributeNames());
                    ehm.setMergeAttributeName(h.getMergeAttribute().getMergeAttributeName());
                    handlers.add(ehm);
                    break;
                case OBJECT_GUID:
                    handlers.add(new ObjectGuidHandler());
                    break;
                case OBJECT_SID:
                    handlers.add(new ObjectSidHandler());
                    break;
                case PRIMARY_GROUP:
                    final PrimaryGroupIdHandler ehp = new PrimaryGroupIdHandler();
                    ehp.setBaseDn(h.getPrimaryGroupId().getBaseDn());
                    ehp.setGroupFilter(h.getPrimaryGroupId().getGroupFilter());
                    handlers.add(ehp);
                    break;
                case RANGE_ENTRY:
                    handlers.add(new RangeEntryHandler());
                    break;
                case RECURSIVE_ENTRY:
                    handlers.add(new RecursiveEntryHandler(h.getRecursive().getSearchAttribute(), h.getRecursive().getMergeAttributes()));
                    break;
                default:
                    break;
            }
        });

        if (!handlers.isEmpty()) {
            LOGGER.debug("Search entry handlers defined for the entry resolver of [{}] are [{}]", l.getLdapUrl(), handlers);
            entryResolver.setSearchEntryHandlers(handlers.toArray(new SearchEntryHandler[]{}));
        }
        return entryResolver;
    }

    /**
     * New blocking connection pool connection pool.
     *
     * @param l the l
     * @return the connection pool
     */
    public static ConnectionPool newLdaptiveBlockingConnectionPool(final AbstractLdapProperties l) {
        final DefaultConnectionFactory bindCf = newLdaptiveConnectionFactory(l);
        final PoolConfig pc = newLdaptivePoolConfig(l);
        final BlockingConnectionPool cp = new BlockingConnectionPool(pc, bindCf);

        cp.setBlockWaitTime(newDuration(l.getBlockWaitTime()));
        cp.setPoolConfig(pc);

        final IdlePruneStrategy strategy = new IdlePruneStrategy();
        strategy.setIdleTime(newDuration(l.getIdleTime()));
        strategy.setPrunePeriod(newDuration(l.getPrunePeriod()));

        cp.setPruneStrategy(strategy);

        switch (l.getValidator().getType().trim().toLowerCase()) {
            case "compare":
                final CompareRequest compareRequest = new CompareRequest();
                compareRequest.setDn(l.getValidator().getDn());
                compareRequest.setAttribute(new LdapAttribute(l.getValidator().getAttributeName(),
                        l.getValidator().getAttributeValues().toArray(new String[]{})));
                compareRequest.setReferralHandler(new SearchReferralHandler());
                cp.setValidator(new CompareValidator(compareRequest));
                break;
            case "none":
                LOGGER.debug("No validator is configured for the LDAP connection pool of [{}]", l.getLdapUrl());
                break;
            case "search":
            default:
                final SearchRequest searchRequest = new SearchRequest();
                searchRequest.setBaseDn(l.getValidator().getBaseDn());
                searchRequest.setSearchFilter(new SearchFilter(l.getValidator().getSearchFilter()));
                searchRequest.setReturnAttributes(ReturnAttributes.NONE.value());
                searchRequest.setSearchScope(SearchScope.OBJECT);
                searchRequest.setSizeLimit(1L);
                searchRequest.setReferralHandler(new SearchReferralHandler());
                cp.setValidator(new SearchValidator(searchRequest));
                break;
        }

        cp.setFailFastInitialize(l.isFailFast());

        if (StringUtils.isNotBlank(l.getPoolPassivator())) {
            final AbstractLdapProperties.LdapConnectionPoolPassivator pass =
                    AbstractLdapProperties.LdapConnectionPoolPassivator.valueOf(l.getPoolPassivator().toUpperCase());
            switch (pass) {
                case CLOSE:
                    cp.setPassivator(new ClosePassivator());
                    LOGGER.debug("Created [{}] passivator for [{}]", l.getPoolPassivator(), l.getLdapUrl());
                    break;
                case BIND:
                    if (StringUtils.isNotBlank(l.getBindDn()) && StringUtils.isNoneBlank(l.getBindCredential())) {
                        final BindRequest bindRequest = new BindRequest();
                        bindRequest.setDn(l.getBindDn());
                        bindRequest.setCredential(new Credential(l.getBindCredential()));
                        cp.setPassivator(new BindPassivator(bindRequest));
                        LOGGER.debug("Created [{}] passivator for [{}]", l.getPoolPassivator(), l.getLdapUrl());
                    } else {
                        LOGGER.warn("No [{}] passivator could be created for [{}] given bind credentials are not specified",
                                l.getPoolPassivator(), l.getLdapUrl());
                    }
                    break;
                default:
                    break;
            }
        }

        LOGGER.debug("Initializing ldap connection pool for [{}] and bindDn [{}]", l.getLdapUrl(), l.getBindDn());
        cp.initialize();
        return cp;
    }

    /**
     * New duration. If the provided length is duration,
     * it will be parsed accordingly, or if it's a numeric value
     * it will be pared as a duration assuming it's provided as seconds.
     *
     * @param length the length in seconds.
     * @return the duration
     */
    public static Duration newDuration(final String length) {
        try {
            if (NumberUtils.isCreatable(length)) {
                return Duration.ofSeconds(Long.valueOf(length));
            }
            return Duration.parse(length);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * New connection factory connection factory.
     *
     * @param l the l
     * @return the connection factory
     */
    public static DefaultConnectionFactory newLdaptiveConnectionFactory(final AbstractLdapProperties l) {
        LOGGER.debug("Creating LDAP connection factory for [{}]", l.getLdapUrl());
        final ConnectionConfig cc = newLdaptiveConnectionConfig(l);
        final DefaultConnectionFactory bindCf = new DefaultConnectionFactory(cc);
        if (l.getProviderClass() != null) {
            try {
                final Class clazz = ClassUtils.getClass(l.getProviderClass());
                bindCf.setProvider((Provider) clazz.newInstance());
            } catch (final Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return bindCf;
    }

    /**
     * New connection config connection config.
     *
     * @param ldapProperties the ldap properties
     * @return the connection config
     */
    public static ConnectionConfig newLdaptiveConnectionConfig(final AbstractLdapProperties ldapProperties) {
        if (StringUtils.isBlank(ldapProperties.getLdapUrl())) {
            throw new IllegalArgumentException("LDAP url cannot be empty/blank");
        }

        LOGGER.debug("Creating LDAP connection configuration for [{}]", ldapProperties.getLdapUrl());
        final ConnectionConfig cc = new ConnectionConfig();

        final String urls = ldapProperties.getLdapUrl().contains(" ")
                ? ldapProperties.getLdapUrl() : String.join(StringUtils.SPACE, ldapProperties.getLdapUrl().split(","));
        LOGGER.debug("Transformed LDAP urls from [{}] to [{}]", ldapProperties.getLdapUrl(), urls);
        cc.setLdapUrl(urls);

        cc.setUseSSL(ldapProperties.isUseSsl());
        cc.setUseStartTLS(ldapProperties.isUseStartTls());
        cc.setConnectTimeout(newDuration(ldapProperties.getConnectTimeout()));
        cc.setResponseTimeout(newDuration(ldapProperties.getResponseTimeout()));

        if (StringUtils.isNotBlank(ldapProperties.getConnectionStrategy())) {
            final AbstractLdapProperties.LdapConnectionStrategy strategy =
                    AbstractLdapProperties.LdapConnectionStrategy.valueOf(ldapProperties.getConnectionStrategy());
            switch (strategy) {
                case RANDOM:
                    cc.setConnectionStrategy(new RandomConnectionStrategy());
                    break;
                case DNS_SRV:
                    cc.setConnectionStrategy(new DnsSrvConnectionStrategy());
                    break;
                case ACTIVE_PASSIVE:
                    cc.setConnectionStrategy(new ActivePassiveConnectionStrategy());
                    break;
                case ROUND_ROBIN:
                    cc.setConnectionStrategy(new RoundRobinConnectionStrategy());
                    break;
                case DEFAULT:
                default:
                    cc.setConnectionStrategy(new DefaultConnectionStrategy());
                    break;
            }
        }

        if (ldapProperties.getTrustCertificates() != null) {
            LOGGER.debug("Creating LDAP SSL configuration via trust certificates [{}]", ldapProperties.getTrustCertificates());
            final X509CredentialConfig cfg = new X509CredentialConfig();
            cfg.setTrustCertificates(ldapProperties.getTrustCertificates());
            cc.setSslConfig(new SslConfig(cfg));

        } else if (ldapProperties.getKeystore() != null) {
            LOGGER.debug("Creating LDAP SSL configuration via keystore [{}]", ldapProperties.getKeystore());
            final KeyStoreCredentialConfig cfg = new KeyStoreCredentialConfig();
            cfg.setKeyStore(ldapProperties.getKeystore());
            cfg.setKeyStorePassword(ldapProperties.getKeystorePassword());
            cfg.setKeyStoreType(ldapProperties.getKeystoreType());
            cc.setSslConfig(new SslConfig(cfg));
        } else {
            LOGGER.debug("Creating LDAP SSL configuration via the native JVM truststore");
            cc.setSslConfig(new SslConfig());
        }
        if (ldapProperties.getSaslMechanism() != null) {
            LOGGER.debug("Creating LDAP SASL mechanism via [{}]", ldapProperties.getSaslMechanism());

            final BindConnectionInitializer bc = new BindConnectionInitializer();
            final SaslConfig sc;
            switch (ldapProperties.getSaslMechanism()) {
                case "DIGEST_MD5":
                    sc = new DigestMd5Config();
                    ((DigestMd5Config) sc).setRealm(ldapProperties.getSaslRealm());
                    break;
                case "CRAM_MD5":
                    sc = new CramMd5Config();
                    break;
                case "EXTERNAL":
                    sc = new ExternalConfig();
                    break;
                case "GSSAPI":
                    sc = new GssApiConfig();
                    ((GssApiConfig) sc).setRealm(ldapProperties.getSaslRealm());
                    break;
                default:
                    throw new IllegalArgumentException("Unknown SASL mechanism " + ldapProperties.getSaslMechanism());
            }
            sc.setAuthorizationId(ldapProperties.getSaslAuthorizationId());
            sc.setMutualAuthentication(ldapProperties.getSaslMutualAuth());
            sc.setQualityOfProtection(QualityOfProtection.valueOf(ldapProperties.getSaslQualityOfProtection()));
            sc.setSecurityStrength(SecurityStrength.valueOf(ldapProperties.getSaslSecurityStrength()));
            bc.setBindSaslConfig(sc);
            cc.setConnectionInitializer(bc);
        } else if (StringUtils.equals(ldapProperties.getBindCredential(), "*") && StringUtils.equals(ldapProperties.getBindDn(), "*")) {
            LOGGER.debug("Creating LDAP fast-bind connection initializer");
            cc.setConnectionInitializer(new FastBindOperation.FastBindConnectionInitializer());
        } else if (StringUtils.isNotBlank(ldapProperties.getBindDn()) && StringUtils.isNotBlank(ldapProperties.getBindCredential())) {
            LOGGER.debug("Creating LDAP bind connection initializer via [{}]", ldapProperties.getBindDn());
            cc.setConnectionInitializer(new BindConnectionInitializer(ldapProperties.getBindDn(), new Credential(ldapProperties.getBindCredential())));
        }
        return cc;
    }

    /**
     * New pool config pool config.
     *
     * @param l the ldap properties
     * @return the pool config
     */
    public static PoolConfig newLdaptivePoolConfig(final AbstractLdapProperties l) {
        LOGGER.debug("Creating LDAP connection pool configuration for [{}]", l.getLdapUrl());
        final PoolConfig pc = new PoolConfig();
        pc.setMinPoolSize(l.getMinPoolSize());
        pc.setMaxPoolSize(l.getMaxPoolSize());
        pc.setValidateOnCheckOut(l.isValidateOnCheckout());
        pc.setValidatePeriodically(l.isValidatePeriodically());
        pc.setValidatePeriod(newDuration(l.getValidatePeriod()));
        pc.setValidateTimeout(newDuration(l.getValidateTimeout()));
        return pc;
    }
}
