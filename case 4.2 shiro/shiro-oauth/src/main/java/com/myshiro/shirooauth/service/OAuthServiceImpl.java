package com.myshiro.shirooauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/26 22:43
 * @Version 1.0
 **/
@Transactional
@Service
public class OAuthServiceImpl implements OAuthService {

    private static Map<String, String> cacheMap = new HashMap<>();
    private static Map<String, String> tokenMap = new HashMap<>();
    @Autowired
    private ClientService clientService;

    @Override
    public void addAuthCode(String authCode, String username) {
        cacheMap.put(authCode, username);
    }

    @Override
    public void addAccessToken(String accessToken, String username) {
        tokenMap.put(accessToken, username);
    }

    @Override
    @CacheEvict
    public String getUsernameByAuthCode(String authCode) {
        return cacheMap.get(authCode);
    }

    @Override
    public String getUsernameByAccessToken(String accessToken) {
        return tokenMap.get(accessToken);
    }

    @Override
    public boolean checkAuthCode(String authCode) {
        return cacheMap.containsKey(authCode);
    }

    @Override
    public boolean checkAccessToken(String accessToken) {
        return tokenMap.containsKey(accessToken);
    }

    @Override
    public boolean checkClientId(String clientId) {
        return clientService.findByClientId(clientId) != null;
    }

    @Override
    public boolean checkClientSecret(String clientSecret) {
        return clientService.findByClientSecret(clientSecret) != null;
    }

    @Override
    public long getExpireIn() {
        return 3600L;
    }
}
