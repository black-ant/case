package com.gang.acttivity.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;

@Component
public class SecurityUtil {

    private Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void logInAs(String username) {

        UserDetails user = userDetailsService.loadUserByUsername(username);

        logger.info("> 用户安全配置 (1) : 简单校验用户是否存在 [{}]", username);
        if (user == null) {
            throw new IllegalStateException("User " + username + " doesn't exist, please provide a valid user");
        }

        logger.info("------> 用户安全配置 (2) , Security 中模拟登录对象 :{} <-------", username);
        SecurityContextHolder.setContext(new SecurityContextImpl(new UsernamePasswordAuthenticationToken(user.getUsername(), "123456")));

        logger.info("------> 用户安全配置 (3) , Activiti 中设置对象 :{} <-------", username);
        org.activiti.engine.impl.identity.Authentication.setAuthenticatedUserId(username);
    }
}
