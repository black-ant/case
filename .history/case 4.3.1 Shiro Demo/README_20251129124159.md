# Shiro Demo

## 项目说明

Apache Shiro 安全框架示例集合，包含多个子项目。

## 技术栈

- Java 11
- Spring Boot 2.7.x
- Apache Shiro
- MySQL

## 子项目列表

| 项目 | 说明 |
|------|------|
| sample | Shiro 基础示例 |
| client | OAuth 客户端 |
| gateway | 网关服务 |
| provide | 服务提供者 |
| register | 注册中心 |
| shiro-oauth | Shiro + OAuth 集成 |

## Shiro vs Spring Security

| 特性 | Shiro | Spring Security |
|------|-------|-----------------|
| 学习曲线 | 低 | 高 |
| 功能 | 够用 | 强大 |
| 与 Spring 集成 | 良好 | 原生 |
| 社区 | 小 | 大 |
| 适用场景 | 中小项目 | 企业级 |

## Shiro 核心概念

| 概念 | 说明 |
|------|------|
| Subject | 当前用户 |
| SecurityManager | 安全管理器 |
| Realm | 数据源（认证/授权信息） |
| Session | 会话管理 |
| Cryptography | 加密 |

## 代码示例

### Shiro 配置

```java
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager manager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        bean.setLoginUrl("/login");
        
        Map<String, String> map = new LinkedHashMap<>();
        map.put("/logout", "logout");
        map.put("/admin/**", "authc,roles[admin]");
        map.put("/**", "authc");
        bean.setFilterChainDefinitionMap(map);
        
        return bean;
    }

    @Bean
    public SecurityManager securityManager(Realm realm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm);
        return manager;
    }
}
```

### 自定义 Realm

```java
public class MyRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("user");
        info.addStringPermission("user:read");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        String username = (String) token.getPrincipal();
        User user = userService.findByUsername(username);
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }
}
```

## 学习资源

- [Apache Shiro 官方文档](https://shiro.apache.org/documentation.html)
- [Shiro 与 Spring Boot 集成](https://shiro.apache.org/spring-boot.html)

