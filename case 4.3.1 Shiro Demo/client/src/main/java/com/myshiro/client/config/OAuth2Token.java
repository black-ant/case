package com.myshiro.client.config;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/26 22:59
 * @Version 1.0
 **/
public class OAuth2Token implements AuthenticationToken {

    public OAuth2Token(String authCode) {
        this.authCode = authCode;
    }

    private String authCode;
    private String principal;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return authCode;
    }
}
