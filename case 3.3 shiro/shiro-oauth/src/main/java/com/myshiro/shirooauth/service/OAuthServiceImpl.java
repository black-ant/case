package com.myshiro.shirooauth.service;

import com.myshiro.shirooauth.entity.AccessToken;
import com.myshiro.shirooauth.mapper.AccessTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    @Autowired
    private AccessTokenMapper accessTokenMapper;

    @Override
    public void addAuthCode(String authCode, String username) {
        AccessToken accessToken = new AccessToken(username, authCode, "", new Date(), new Date());
        accessTokenMapper.insertTokem(accessToken);
    }

    @Override
    public void addAccessToken(String accessToken, String username) {
        accessTokenMapper.updateTokem(accessToken, username);
    }

    @Override
    public String getUsernameByAuthCode(String authCode) {
        return accessTokenMapper.findOneByCode(authCode).getUsername();
    }

    @Override
    public String getUsernameByAccessToken(String accessToken) {

        return accessTokenMapper.findByAccessToken(accessToken).getUsername();
    }

    @Override
    public boolean checkAuthCode(String authCode) {
        return accessTokenMapper.countOneCode(authCode) > 0;
    }

    @Override
    public boolean checkAccessToken(String accessToken) {
        return accessTokenMapper.countOneTokenn(accessToken) > 0;
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
