package com.gang.study.security.saml.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //该方法用于用户认证，此处添加内存用户，并且指定了权限
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("user").password(new BCryptPasswordEncoder().encode("123456")).roles("USER")
                .and()
                .withUser("admin").password(new BCryptPasswordEncoder().encode("123456")).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //此方法中进行了请求授权，用来规定对哪些请求进行拦截
        //其中：antMatchers--使用ant风格的路径匹配
        //regexMatchers--使用正则表达式匹配
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()
                .regexMatchers(".*sign.*").permitAll()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/admin2").hasAnyRole("ADMIN","USER")
                .anyRequest().authenticated()                      //其它请求都需要校验才能访问
                .and()
                .formLogin()
                .loginPage("/login")                             //定义登录的页面"/login"，允许访问
                .permitAll()
                .and()
                .logout()                                           //默认的"/logout", 允许访问
                .permitAll();

    }
}
