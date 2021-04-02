package com.security.demo.config;

import com.security.demo.filter.BeforeFilter;
import com.security.demo.filter.DatabaseAuthenticationFilter;
import com.security.demo.provider.DatabaseAuthenticationProvider;
import com.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler myAuthenctiationFailureHandler;

    @Autowired
    private DatabaseAuthenticationProvider databaseAuthenticationProvider;

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(databaseAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //此方法中进行了请求授权，用来规定对哪些请求进行拦截
        //其中：antMatchers--使用ant风格的路径匹配
        //regexMatchers--使用正则表达式匹配
        http.authorizeRequests()
                .antMatchers("/test/**").permitAll()
                .antMatchers("/before/**").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()                      //其它请求都需要校验才能访问
                .and()
                .formLogin()
                .loginPage("/login")                             //定义登录的页面"/login"，允许访问
                .defaultSuccessUrl("/home")  //登录成功后默认跳转到"list"
                .successHandler(myAuthenticationSuccessHandler).failureHandler(myAuthenctiationFailureHandler).permitAll().and()
                .logout()                                           //默认的"/logout", 允许访问
                .logoutSuccessUrl("/index")
                .permitAll();

        AbstractAuthenticationProcessingFilter filter = new DatabaseAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new BeforeFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/**/*.js", "/lang/*.json", "/**/*.css", "/**/*.js", "/**/*.map", "/**/*.html", "/**/*.png");
    }
}
