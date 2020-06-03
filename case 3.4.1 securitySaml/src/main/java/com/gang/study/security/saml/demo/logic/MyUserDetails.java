package com.gang.study.security.saml.demo.logic;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

/**
 * @Classname MyUserDetails
 * @Description TODO
 * @Date 2020/6/3 12:42
 * @Created by zengzg
 */

public class MyUserDetails implements SAMLUserDetailsService {

    @Override
    public Object loadUserBySAML(SAMLCredential samlCredential) throws UsernameNotFoundException {
        return null;
    }
}
