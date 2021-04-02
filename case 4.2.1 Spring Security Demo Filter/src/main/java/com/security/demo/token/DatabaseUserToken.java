package com.security.demo.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * @Classname DatabaseUserToken
 * @Description TODO
 * @Date 2020/5/31 18:48
 * @Created by zengzg
 */
public class DatabaseUserToken extends AbstractAuthenticationToken {


    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;
    private String credentials;
    private String type;
    private Collection<? extends GrantedAuthority> authorities;

    public DatabaseUserToken(Object principal, String credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.type = "common";
        setAuthenticated(false);
    }

    public DatabaseUserToken(Object principal, String credentials, String type) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
//        this.type = StringUtils.isEmpty(type) ? "common" : type;
        setAuthenticated(false);
    }

    public DatabaseUserToken(Object principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public DatabaseUserToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = null;
        super.setAuthenticated(true); // must use super, as we override
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * @param isAuthenticated
     * @throws IllegalArgumentException
     */
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
