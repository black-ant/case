package com.macro.cloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname OAuthPasswordFilter
 * @Description TODO
 * @Date 2020/2/23 19:48
 * @Created by zengzg
 */
//@Component
public class OAuthPasswordFilter extends UsernamePasswordAuthenticationFilter {

    public static final String SPRING_SECURITY_FORM_REDERICT_KEY = "spring-security-redirect";

    /**
     * @deprecated If you want to retain the username, cache it in a customized {@code AuthenticationFailureHandler}
     */
    @Deprecated
    public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private String redirectParameter = SPRING_SECURITY_FORM_REDERICT_KEY;
    private boolean postOnly = true;

    //~ Constructors ===================================================================================================

    public OAuthPasswordFilter() {
        super();
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    //~ Methods ========================================================================================================

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        logger.info("------> this is in filter  OAuthPasswordFilter<-------");

        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String redirectUrl = obtainRedercitUrl(request);
        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }
        //自定义回调URL，若存在则放入Session
        if (redirectUrl != null && !"".equals(redirectUrl)) {
            request.getSession().setAttribute("callCustomRediretUrl", redirectUrl);
        }
        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * Enables subclasses to override the composition of the password, such as by including additional values
     * and a separator.<p>This might be used for example if a postcode/zipcode was required in addition to the
     * password. A delimiter such as a pipe (|) should be used to separate the password and extended value(s). The
     * <code>AuthenticationDao</code> will need to generate the expected password in a corresponding manner.</p>
     *
     * @param request so that request attributes can be retrieved
     * @return the password that will be presented in the <code>Authentication</code> request token to the
     * <code>AuthenticationManager</code>
     */
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

    /**
     * Enables subclasses to override the composition of the username, such as by including additional values
     * and a separator.
     *
     * @param request so that request attributes can be retrieved
     * @return the username that will be presented in the <code>Authentication</code> request token to the
     * <code>AuthenticationManager</code>
     */
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }


    protected String obtainRedercitUrl(HttpServletRequest request) {
        return request.getParameter(redirectParameter);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication request's details
     * property.
     *
     * @param request     that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details set
     */
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Sets the parameter name which will be used to obtain the username from the login request.
     *
     * @param usernameParameter the parameter name. Defaults to "j_username".
     */
    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    /**
     * Sets the parameter name which will be used to obtain the password from the login request..
     *
     * @param passwordParameter the parameter name. Defaults to "j_password".
     */
    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }

    /**
     * Defines whether only HTTP POST requests will be allowed by this filter.
     * If set to true, and an authentication request is received which is not a POST request, an exception will
     * be raised immediately and authentication will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method
     * will be called as if handling a failed authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }


}
