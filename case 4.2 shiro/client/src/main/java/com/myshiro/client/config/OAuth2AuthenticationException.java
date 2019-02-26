package com.myshiro.client.config;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/26 23:03
 * @Version 1.0
 **/
public class OAuth2AuthenticationException extends AuthenticationException {

    public OAuth2AuthenticationException(Throwable cause) {
        super(cause);
    }
}
