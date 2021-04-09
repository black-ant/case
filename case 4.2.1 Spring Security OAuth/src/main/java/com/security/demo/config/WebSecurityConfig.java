package com.security.demo.config;

import com.security.demo.filter.BeforeFilter;
import com.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpointHandlerMapping;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private List<AuthorizationServerConfigurer> configurers = Collections.emptyList();

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler myAuthenctiationFailureHandler;

    @Autowired
    private AuthorizationServerEndpointsConfiguration endpoints;

    @Autowired
    private UserService userService;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Bean
    public AuthenticationManager authenticationManagerBean(DataSource dataSource) throws Exception {
        OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
        authenticationManager.setTokenServices(new DefaultTokenServices());
        authenticationManager.setClientDetailsService(new JdbcClientDetailsService(dataSource));
        return authenticationManager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //该方法用于用户认证，此处添加内存用户，并且指定了权限
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Autowired
    public void configure(ClientDetailsServiceConfigurer clientDetails) throws Exception {
        for (AuthorizationServerConfigurer configurer : configurers) {
            configurer.configure(clientDetails);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthorizationServerSecurityConfigurer configurer = new AuthorizationServerSecurityConfigurer();
        FrameworkEndpointHandlerMapping handlerMapping = endpoints.oauth2EndpointHandlerMapping();
        http.setSharedObject(FrameworkEndpointHandlerMapping.class, handlerMapping);
        configure(configurer);
        http.apply(configurer);
        String tokenEndpointPath = handlerMapping.getServletPath("/oauth/token");
        String authorizeEndpointPath = handlerMapping.getServletPath("/oauth/authorize");
        String tokenKeyPath = handlerMapping.getServletPath("/oauth/token_key");
        String checkTokenPath = handlerMapping.getServletPath("/oauth/check_token");
        if (!endpoints.getEndpointsConfigurer().isUserDetailsServiceOverride()) {
            UserDetailsService userDetailsService = http.getSharedObject(UserDetailsService.class);
            endpoints.getEndpointsConfigurer().userDetailsService(userDetailsService);
        }

        // PS : 注意 , OAuth 本身有一个 WebSecurityConfigurerAdapter ,我这里选择覆盖自定义
        http.authorizeRequests()
                .antMatchers("/test/**").permitAll()
                .antMatchers("/before/**").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers(authorizeEndpointPath).authenticated()
                .antMatchers(tokenEndpointPath).fullyAuthenticated()
                .antMatchers(tokenKeyPath).access(configurer.getTokenKeyAccess())
                .antMatchers(checkTokenPath).access(configurer.getCheckTokenAccess())
                .anyRequest().authenticated()                      //其它请求都需要校验才能访问
                .and()
                .requestMatchers()
                .antMatchers(tokenEndpointPath, tokenKeyPath, checkTokenPath)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .formLogin()
                .loginPage("/login")                             //定义登录的页面"/login"，允许访问
                .defaultSuccessUrl("/home")  //登录成功后默认跳转到"list"
                .successHandler(myAuthenticationSuccessHandler).failureHandler(myAuthenctiationFailureHandler).permitAll().and()
                .logout()                                           //默认的"/logout", 允许访问
                .logoutSuccessUrl("/index")
                .permitAll();
        http.addFilterBefore(new BeforeFilter(), UsernamePasswordAuthenticationFilter.class);
        http.setSharedObject(ClientDetailsService.class, clientDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/**/*.js", "/lang/*.json", "/**/*.css", "/**/*.js", "/**/*.map", "/**/*.html", "/**/*.png");
    }

    protected void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        for (AuthorizationServerConfigurer configurer : configurers) {
            configurer.configure(oauthServer);
        }
    }
}
