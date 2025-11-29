Spring AI Common - 审计日志组件
================================

这是一个轻量级的审计日志组件，只包含 API 审计功能。

功能
----
✅ API 审计日志 - 自动记录 Controller 的请求和响应

目录结构
--------
src/main/java/com/example/common/audit/
├── ApiAuditLog.java              # 审计日志注解
├── ApiAuditAspect.java           # AOP 切面
├── AuditAutoConfiguration.java   # 自动配置
└── AuditLogInfo.java             # 日志信息模型

依赖说明
--------
所有依赖都是 provided 或 optional，不会传递到使用方：

核心依赖 (provided):
- AspectJ (aspectjweaver) - AOP 核心
- Spring Context - 用于配置和 Bean 管理
- Jackson - JSON 序列化日志
- SLF4J - 日志接口

可选依赖 (optional):
- Spring Boot AutoConfigure - 条件注解
- Spring Web - 获取 HTTP 请求信息
- Servlet API - HTTP 请求支持
- Reactor Core - 响应式支持
- Lombok - 代码简化

使用方法
--------
1. 添加依赖：
   <dependency>
       <groupId>com.example</groupId>
       <artifactId>spring-ai-common</artifactId>
       <version>1.0.0</version>
   </dependency>

2. 在 Controller 上添加注解：
   @RestController
   @ApiAuditLog
   public class MyController {
       // ...
   }

3. 自动记录请求和响应日志

特点
----
- 轻量级 - 只包含审计功能
- 无侵入 - 使用 AOP 切面
- 可选的 - 所有依赖都不会传递
- 灵活的 - 支持同步和响应式
