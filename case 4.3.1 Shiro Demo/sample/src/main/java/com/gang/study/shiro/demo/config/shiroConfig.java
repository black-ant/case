package com.gang.study.shiro.demo.config;

import com.gang.study.shiro.demo.service.CustomRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname shiroConfig
 * @Description TODO
 * @Date 2021/3/6 22:38
 * @Created by zengzg
 */
@Configuration
public class shiroConfig {


    /**
     * 配置 Realm
     *
     * @return
     */
    @Bean
    public CustomRealm myShiroRealm() {
        CustomRealm customRealm = new CustomRealm();
        return customRealm;
    }

    /**
     * 权限管理，配置主要是Realm的管理认证
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new HashMap<>();
        // logout url
        map.put("/logout", "logout");
        //对所有用户认证
        map.put("/**", "authc");
        //登录
        shiroFilterFactoryBean.setLoginUrl("/login");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /**
     * 注册 SecurityManager
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    /**
     * AOP 注解冲突解决方式
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }


    /**
     * 通过 URL 判断细粒度权限
     */
//    @Bean
//    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
//        DefaultShiroFilterChainDefinition chain = new DefaultShiroFilterChainDefinition();
//
//        /**
//         * 这里小心踩坑！我在application.yml中设置的context-path: /api/v1
//         * 但经过实际测试，过滤器的过滤路径，是context-path下的路径，无需加上"/api/v1"前缀
//         */
//
//        //访问控制
//        chain.addPathDefinition("/user/login", "anon");//可以匿名访问
//        chain.addPathDefinition("/page/401", "anon");//可以匿名访问
//        chain.addPathDefinition("/page/403", "anon");//可以匿名访问
//        chain.addPathDefinition("/t4/hello", "anon");//可以匿名访问
//
//        chain.addPathDefinition("/t4/changePwd", "authc");//需要登录
//        chain.addPathDefinition("/t4/user", "user");//已登录或“记住我”的用户可以访问
//        chain.addPathDefinition("/t4/mvnBuild", "authc,perms[mvn:install]");//需要mvn:build权限
//        chain.addPathDefinition("/t4/gradleBuild", "authc,perms[gradle:build]");//需要gradle:build权限
//        chain.addPathDefinition("/t4/js", "authc,roles[js]");//需要js角色
//        chain.addPathDefinition("/t4/python", "authc,roles[python]");//需要python角色
//
//        // shiro 提供的登出过滤器，访问指定的请求，就会执行登录，默认跳转路径是"/"，或者是"shiro.loginUrl"配置的内容
//        // 由于application-shiro.yml中配置了 shiro:loginUrl: /page/401，返回会返回对应的json内容
//        // 可以结合/user/login和/t1/js接口来测试这个/t4/logout接口是否有效
//        chain.addPathDefinition("/t4/logout", "anon,logout");
//
//        //其它路径均需要登录
//        chain.addPathDefinition("/**", "authc");
//
//        return chain;
//    }
}
