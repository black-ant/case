package com.gang.antsso.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 在接口上配置权限时使用
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableOAuth2Sso
@Order(101)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
}
