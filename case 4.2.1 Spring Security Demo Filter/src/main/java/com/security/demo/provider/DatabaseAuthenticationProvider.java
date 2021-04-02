package com.security.demo.provider;

import com.alibaba.fastjson.JSONObject;
import com.security.demo.entity.Users;
import com.security.demo.service.UserService;
import com.security.demo.token.DatabaseUserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @Classname DatabaseAuthenticationProvider
 * @Description TODO
 * @Date 2020/5/31 17:10
 * @Created by zengzg
 */
@Component
public class DatabaseAuthenticationProvider implements AuthenticationProvider {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        logger.info("------> auth database <-------");
        String username = (authentication.getPrincipal() == null)
                ? "NONE_PROVIDED" : String.valueOf(authentication.getPrincipal());
        String password = (String) authentication.getCredentials();

        if (StringUtils.isEmpty(password)) {
            throw new BadCredentialsException("密码不能为空");
        }

        Users user = userService.loadUserByUsername(username);
        logger.info("------> this is [{}] user  :{}<-------", username, String.valueOf(user));
        if (null == user) {
            logger.error("E----> error :{} --user not fount ", username);
            throw new BadCredentialsException("用户不存在");
        }

        logger.info("------> {} <-------", new BCryptPasswordEncoder().encode("123456"));
        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            logger.error("E----> user check error");
            throw new BadCredentialsException("用户名或密码不正确");
        } else {
            logger.info("user check success");
        }

        DatabaseUserToken result = new DatabaseUserToken(
                username,
                new BCryptPasswordEncoder().encode(password),
                listUserGrantedAuthorities(String.valueOf(user.getId())));

        result.setDetails(authentication.getDetails());
        logger.info("------> auth database result :{} <-------", JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (DatabaseUserToken.class.isAssignableFrom(authentication));
    }

    private Set<GrantedAuthority> listUserGrantedAuthorities(String uid) {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        if (StringUtils.isEmpty(uid)) {
            return authorities;
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }
}
