package com.security.demo.config;

import com.security.demo.entity.UserSummaryEntity;
import com.security.demo.service.security.JPAUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 10169
 * @Description 授权服务器配置 OAuth2
 * @Date 2019/1/17 22:45
 * @Version 1.0
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisConnectionFactory connectionFactory;
    @Autowired
    private JPAUserDetailsService jpaUserDetailsService;

    @Bean
    public RedisTokenStore tokenStore() {
        return new RedisTokenStore(connectionFactory);
    }


    /**
     * 定义了授权和令牌端点和令牌服务
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(jpaUserDetailsService)//若无，refresh_token会有UserDetailsService is required错误
                .tokenStore(tokenStore());
    }

    /**
     * AuthorizationServerSecurityConfigurer -> 用来配置令牌端点（Token Endpoint）的安全约束
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * ClientDetailsServiceConfigurer  ->  用来配置客户端详情服务 ,能够使用内存或者JDBC 来实现客户端详情服务
     * clientId:(必需）客户端ID。
     * secret:(可信客户端需要）客户端密钥（如果有）。
     * scope：客户受限的范围。如果范围未定义或为空（默认值），则客户端不受范围限制。
     * authorizedGrantTypes：授权客户端使用的授权类型。默认值为空。
     * - > 授权码模式（authorization code）
     * - > 简化模式（implicit）
     * - > 密码模式（resource owner password credentials）
     * - > 客户端模式（client credentials）
     * authorities：授予客户端的权限（常规Spring Security权限）
     *
     * @param clients)
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("android")
                .scopes("xx")
                .secret(new BCryptPasswordEncoder().encode("123456"))
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .and()
                .withClient("webapp")
                .scopes("xx")
                .authorizedGrantTypes("implicit")
                .and()
                .withClient("browser")
                .authorizedGrantTypes("refresh_token", "authorization_code", "password")
                .redirectUris("http://localhost:8082/oauth/login/browser")
                .scopes("ui");
    }

    /**
     * Jwt资源令牌转换器
     * @return accessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        return new JwtAccessTokenConverter(){

            /**
             * 重写增强token的方法
             * @param accessToken 资源令牌
             * @param authentication 认证
             * @return 增强的OAuth2AccessToken对象
             */
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

                String userName = authentication.getUserAuthentication().getName();
                UserSummaryEntity user = (UserSummaryEntity) authentication.getUserAuthentication().getPrincipal();
                Map<String,Object> infoMap = new HashMap<>();
                infoMap.put("userName",userName);
                infoMap.put("roles",user.getAuthorities());
                ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(infoMap);
                return super.enhance(accessToken, authentication);
            }
        };
    }
}
