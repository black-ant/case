package com.macro.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

/**
 * @Classname ClientService
 * @Description TODO
 * @Date 2020/2/23 20:31
 * @Created by zengzg
 */
@Component
public class ClientService implements ClientDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {

        BaseClientDetails details = new BaseClientDetails();
        details.setClientId("gang");
        details.setClientSecret(passwordEncoder.encode("admin123456"));
        details.setAccessTokenValiditySeconds(3600);
        details.setRefreshTokenValiditySeconds(864000);

        HashSet<String> set = new LinkedHashSet();
        set.add("http://www.baidu.com");
        //        set.add("http://localhost:9501/login");
        details.setRegisteredRedirectUri(set);

        HashSet<String> setScope = new LinkedHashSet();
        setScope.add("all");
        details.setScope(setScope);

        LinkedHashSet<String> types = new LinkedHashSet();
        types.add("authorization_code");
        types.add("password");
        details.setAuthorizedGrantTypes(types);

        return details;
    }
}
