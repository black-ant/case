package com.security.demo.config;

import com.security.demo.service.security.JPAUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 10169
 * @Description 安全配置
 * * @ EnableWebSecurity 启用web安全配置
 * * @ EnableGlobalMethodSecurity 启用全局方法安全注解，就可以在方法上使用注解来对请求进行过滤
 * @Date 2019/1/17 22:27
 * @Version 1.0
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 注入用户Detail ,也可以使用@Autowired
     *
     * @return 用户信息服务对象
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new JPAUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * http安全配置
     * test 路径 允许
     * 其他路径均需要验证
     *
     * @throws Exception http安全异常信息
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers()
                .antMatchers("/oauth/**", "/login/**", "/logout/**")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/test").permitAll()
                .anyRequest().authenticated().and()
                .httpBasic().and()
                .csrf().disable();
    }


    /**
     * 全局用户信息 ，可以使用 inMemoryAuthentication 配置内存用户
     * 或者使用 userDetailsService 配置JDBC
     *
     * @return 用户信息服务对象
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.debug("载入加密配置AuthenticationManagerBuilder");
//        auth.userDetailsService(jpaUserDetailsService).passwordEncoder(passwordEncoder());
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
                .withUser("gang").password(new BCryptPasswordEncoder().encode("123456")).roles("ADMIN", "USER").and()
                .withUser("user").password(new BCryptPasswordEncoder().encode("123456")).roles("USER");
    }

    //不定义没有password grant_type
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
