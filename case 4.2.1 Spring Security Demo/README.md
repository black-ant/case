# Spring Security Demo

## 项目说明

Spring Boot Security 安全框架示例，演示认证和授权。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL

## Spring Security 核心概念

| 概念 | 说明 |
|------|------|
| Authentication | 认证（验证用户身份） |
| Authorization | 授权（验证用户权限） |
| Principal | 当前认证用户 |
| GrantedAuthority | 授予的权限 |
| SecurityContext | 安全上下文 |
| Filter Chain | 过滤器链 |

## 项目结构

```
src/main/java/com/security/demo/
├── DemoApplication.java          # 启动类
├── config/
│   ├── WebSecurityConfig.java    # Security 配置
│   ├── WebMvcConfig.java         # MVC 配置
│   └── MyPasswordEncoder.java    # 密码编码器
├── controller/
│   ├── LoginController.java      # 登录控制器
│   └── UserManageController.java # 用户管理
├── entity/
│   ├── Users.java                # 用户实体
│   └── Roles.java                # 角色实体
├── repository/
│   └── UserRepository.java       # 用户仓库
├── service/
│   ├── UserService.java          # 用户服务
│   └── MyAuthenticationSuccessHandler.java  # 认证成功处理
└── filter/
    └── BeforeFilter.java         # 自定义过滤器
```

## 快速开始

### 配置数据库

编辑 `application.properties`：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/security_demo
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 启动应用

```bash
mvn spring-boot:run
```

### 访问页面

- 首页：http://localhost:8080/
- 登录：http://localhost:8080/login

## 代码示例

### Security 配置

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home", "/register").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
            .and()
            .logout()
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }
}
```

### 用户认证

```java
@Service
public class UserService implements UserDetailsService {
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            getAuthorities(user.getRoles())
        );
    }
}
```

## 常用注解

| 注解 | 说明 |
|------|------|
| @PreAuthorize | 方法执行前检查权限 |
| @PostAuthorize | 方法执行后检查权限 |
| @Secured | 指定角色访问 |
| @RolesAllowed | JSR-250 角色注解 |

## 学习资源

- [Spring Security 官方文档](https://docs.spring.io/spring-security/reference/)
- [Spring Security 参考](https://spring.io/guides/gs/securing-web/)

