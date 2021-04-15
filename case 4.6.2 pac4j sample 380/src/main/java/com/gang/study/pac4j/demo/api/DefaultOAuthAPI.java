package com.gang.study.pac4j.demo.api;

import com.github.scribejava.core.builder.api.DefaultApi20;

/**
 * @Classname DefaultOAuthAPI
 * @Description TODO
 * @Date 2021/4/13
 * @Created by zengzg
 */
public class DefaultOAuthAPI extends DefaultApi20 {


    public String getRootEndpoint() {
        return "http://127.0.0.1/sso/oauth2.0/";
    }

    @Override
    public String getAccessTokenEndpoint() {
        return getRootEndpoint() + "accessToken";
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return getRootEndpoint() + "authorize";
    }
}
