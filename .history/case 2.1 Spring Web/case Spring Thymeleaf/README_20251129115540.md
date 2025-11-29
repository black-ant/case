# Spring Thymeleaf Demo

## 项目说明

这是一个 Spring Boot + Thymeleaf 模板引擎的示例项目，演示如何使用 Thymeleaf 进行服务端页面渲染。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- Thymeleaf 模板引擎
- Fastjson2
- Lombok

## 什么是 Thymeleaf？

Thymeleaf 是一个现代化的服务端 Java 模板引擎，具有以下特点：

- **自然模板** - HTML 文件可以直接在浏览器中打开预览
- **Spring 集成** - 与 Spring MVC 完美集成
- **丰富的表达式** - 支持变量、选择、消息、URL 等表达式

## 项目结构

```
src/main/java/com/mythymeleaf/demo/
├── DemoApplication.java          # 启动类
├── config/
│   └── MyWebMvcConfig.java       # MVC 配置
├── controller/
│   ├── DemoController.java       # 演示控制器
│   ├── LoginController.java      # 登录控制器
│   └── UserController.java       # 用户控制器
└── to/
    └── ContentTO.java            # 内容传输对象

src/main/resources/
├── application.properties        # 应用配置
├── static/                       # 静态资源 (CSS, JS)
└── templates/                    # Thymeleaf 模板
    ├── common/                   # 公共组件
    ├── fragments/                # 页面片段
    ├── pages/                    # 业务页面
    ├── index.html                # 首页
    └── login.html                # 登录页
```

## 快速开始

### 启动应用

```bash
mvn spring-boot:run
```

### 访问页面

- 首页：http://localhost:8080/
- 登录页：http://localhost:8080/login

### 测试账号

- 用户名：`ant`
- 密码：`123456`

## Thymeleaf 语法示例

### 1. 变量输出

```html
<p th:text="${user.username}">默认用户名</p>
```

### 2. 条件判断

```html
<div th:if="${user.age >= 18}">
    <p>成年用户</p>
</div>
<div th:unless="${user.age >= 18}">
    <p>未成年用户</p>
</div>
```

### 3. 循环遍历

```html
<ul>
    <li th:each="user : ${users}" th:text="${user.username}">用户名</li>
</ul>
```

### 4. 表单绑定

```html
<form th:action="@{/doLogin}" method="post">
    <input type="text" name="username" th:value="${username}"/>
    <input type="password" name="password"/>
    <button type="submit">登录</button>
</form>
```

### 5. URL 表达式

```html
<a th:href="@{/user/{id}(id=${user.id})}">用户详情</a>
<link th:href="@{/css/style.css}" rel="stylesheet"/>
```

### 6. 片段引用

```html
<!-- 定义片段 -->
<div th:fragment="header">
    <h1>网站头部</h1>
</div>

<!-- 引用片段 -->
<div th:replace="~{common/header :: header}"></div>
```

## 配置说明

在 `application.properties` 中配置 Thymeleaf：

```properties
# 模板文件位置
spring.thymeleaf.prefix=classpath:/templates/
# 模板文件后缀
spring.thymeleaf.suffix=.html
# 编码
spring.thymeleaf.encoding=UTF-8
# 开发时关闭缓存
spring.thymeleaf.cache=false
# 模板模式
spring.thymeleaf.mode=HTML
```

## 运行测试

```bash
mvn test
```

## 学习要点

1. **th:text vs th:utext** - text 转义 HTML，utext 不转义
2. **th:if vs th:unless** - 条件渲染
3. **th:each** - 循环遍历
4. **@{} 表达式** - 生成 URL
5. **th:fragment + th:replace** - 模板复用

