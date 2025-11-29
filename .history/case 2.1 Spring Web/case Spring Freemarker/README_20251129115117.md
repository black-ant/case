# Spring Freemarker Demo

## 项目说明

这是一个 Spring Boot + Freemarker 模板引擎的示例项目，演示如何使用 Freemarker 进行服务端页面渲染。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Freemarker 模板引擎
- Lombok

## 什么是 Freemarker？

Freemarker 是一个基于模板的 Java 模板引擎，用于生成文本输出（HTML、XML、邮件等）。它将数据模型和模板结合，生成最终的输出内容。

## 项目结构

```
src/main/java/com/gang/study/freemarker/demo/
├── DemoApplication.java          # 启动类
├── config/
│   └── WebConfig.java            # Web 配置
├── controller/
│   ├── StartController.java      # 首页控制器
│   ├── TestController.java       # 测试控制器
│   └── UserController.java       # 用户控制器
└── to/
    ├── ContentTO.java            # 内容传输对象
    └── UserTO.java               # 用户传输对象

src/main/resources/
├── application.properties        # 应用配置
├── static/                       # 静态资源
└── templates/                    # Freemarker 模板
    ├── login.ftl                 # 登录页模板
    └── user.ftl                  # 用户页模板
```

## 快速开始

### 启动应用

```bash
mvn spring-boot:run
```

### 访问页面

- 登录页：http://localhost:8080/login
- 用户页：http://localhost:8080/user

## Freemarker 语法示例

### 1. 变量输出

```ftl
<h1>Hello, ${user.username}!</h1>
```

### 2. 条件判断

```ftl
<#if user.age?? && user.age > 18>
    <p>成年用户</p>
<#else>
    <p>未成年用户</p>
</#if>
```

### 3. 循环遍历

```ftl
<ul>
<#list users as user>
    <li>${user.username}</li>
</#list>
</ul>
```

### 4. 空值处理

```ftl
${user.email!"未设置邮箱"}
```

### 5. 日期格式化

```ftl
${createTime?string("yyyy-MM-dd HH:mm:ss")}
```

## 配置说明

在 `application.properties` 中配置 Freemarker：

```properties
# 模板文件后缀
spring.freemarker.suffix=.ftl
# 模板文件位置
spring.freemarker.template-loader-path=classpath:/templates/
# 编码
spring.freemarker.charset=UTF-8
# 开发时关闭缓存
spring.freemarker.cache=false
```

## 运行测试

```bash
mvn test
```

## 学习要点

1. **ModelAndView** - 包含视图名和模型数据的对象
2. **模板位置** - 默认在 `resources/templates/` 目录
3. **数据绑定** - 通过 `${变量名}` 在模板中访问数据
4. **空值安全** - 使用 `!` 运算符处理空值

