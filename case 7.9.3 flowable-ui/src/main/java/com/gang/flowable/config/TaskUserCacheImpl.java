/** * $Id: TaskUserCacheImpl.java,v 1.0 2019-06-25 16:12 chenmin Exp $ */package com.gang.flowable.config;import com.google.common.cache.CacheBuilder;import com.google.common.cache.CacheLoader;import com.google.common.cache.LoadingCache;import com.google.common.util.concurrent.UncheckedExecutionException;import java.util.ArrayList;import java.util.Collection;import java.util.concurrent.ExecutionException;import java.util.concurrent.TimeUnit;import javax.annotation.PostConstruct;import org.flowable.idm.api.User;import org.flowable.ui.common.properties.FlowableCommonAppProperties;import org.flowable.ui.common.service.idm.RemoteIdmService;import org.flowable.ui.task.service.api.UserCache;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.security.authentication.LockedException;import org.springframework.security.core.GrantedAuthority;import org.springframework.security.core.userdetails.UsernameNotFoundException;import org.springframework.stereotype.Service;/** * 重写 {@link org.flowable.ui.task.service.idm.UserCacheImpl} 类,避免启动时和 {@link org.flowable.ui.idm.service.UserCacheImpl} 命名冲突 * Cache containing User objects to prevent too much DB-traffic (users exist separately from the Flowable tables, they need to be fetched afterward one by one to join with those entities). * <p> * * @author Frederik Heremans * @author Joram Barrez * @author Filip Hrisafov */@Servicepublic class TaskUserCacheImpl implements UserCache {    @Autowired    protected FlowableCommonAppProperties properties;    @Autowired    protected RemoteIdmService remoteIdmService;    protected LoadingCache<String, CachedUser> userCache;    @PostConstruct    protected void initCache() {        FlowableCommonAppProperties.Cache cache = properties.getCacheUsers();        long userCacheMaxSize = cache.getMaxSize();        long userCacheMaxAge = cache.getMaxAge();        userCache = CacheBuilder.newBuilder().maximumSize(userCacheMaxSize)                .expireAfterAccess(userCacheMaxAge, TimeUnit.SECONDS).recordStats().build(new CacheLoader<String, CachedUser>() {                    public CachedUser load(final String userId) throws Exception {                        User user = remoteIdmService.getUser(userId);                        if (user == null) {                            throw new UsernameNotFoundException("User " + userId + " was not found in the database");                        }                        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();                        return new CachedUser(user, grantedAuthorities);                    }                });    }    @Override    public void putUser(String userId, CachedUser cachedUser) {        userCache.put(userId, cachedUser);    }    @Override    public CachedUser getUser(String userId) {        return getUser(userId, false, false, true); // always check validity by default    }    @Override    public CachedUser getUser(String userId, boolean throwExceptionOnNotFound, boolean throwExceptionOnInactive, boolean checkValidity) {        try {            // The cache is a LoadingCache and will fetch the value itself            CachedUser cachedUser = userCache.get(userId);            return cachedUser;        } catch (ExecutionException e) {            return null;        } catch (UncheckedExecutionException uee) {            // Some magic with the exceptions is needed:            // the exceptions like UserNameNotFound and Locked cannot            // bubble up, since Spring security will react on them otherwise            if (uee.getCause() instanceof RuntimeException) {                RuntimeException runtimeException = (RuntimeException) uee.getCause();                if (runtimeException instanceof UsernameNotFoundException) {                    if (throwExceptionOnNotFound) {                        throw runtimeException;                    } else {                        return null;                    }                }                if (runtimeException instanceof LockedException) {                    if (throwExceptionOnNotFound) {                        throw runtimeException;                    } else {                        return null;                    }                }            }            throw uee;        }    }    @Override    public void invalidate(String userId) {        userCache.invalidate(userId);    }}