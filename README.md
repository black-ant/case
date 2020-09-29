# Case Project 

[TOC]



## 前言
	关于一些框架的测试和梳理 , 以及源码解读文档 



## 目录

| 名称                             | 描述 | 状态 |
| -------------------------------- | ---- | ---- |
| case 0 utils                     | Case 常用工具 | 未完成 |
| case 1.1 base                    | 基础类 , 包含Case 公共工具 | 完成 |
| case 1.1.1 test                  | 简单测试案例 , 测试环境 | 完成 |
| case 1.1.2 Java Web No Spring    | 祖传 Java Web 项目 | 待优化 |
| case 1.2 thymeleaf               | Thymeleaf 案例 | 完成 |
| case 1.2.1 Freemarker            | Freemarket 案例 | 完成 |
| case 1.3 cxf                     | CXF 案例 |      |
| case 1.3.1 easyrest              | EasyRest 框架 | 完成 |
| case 1.3.2 Jersey Web            | Jersey Web 框架 | 完成 |
| case 1.3.2.1 Jersey-springboot   | Spring Boot 整合 Jersey 框架 | 完成 |
| case 1.3.2.2 Jersey config       | Jersey 特殊配置 | 完成 |
| case 1.4.1 fileUpload            | 文件上传案例 |      |
| case 1.4.2 okhttp                | OKHTTP 框架 |      |
| case 1.5 swagger                 | Swagger 使用 |      |
| case 1.5.1 swaggerv3             | Swagger V3 使用 |      |
| case 1.8 websocket               | WebSocket 简单案例 |      |
| case 1.8.1 websocketjava         | WebSocket 纯Java 案例 |      |
| case 2.1 JPA example             | Spring JPA 案例 |      |
| case 2.1.1 JPAandRedis           | Spring JPA + Redis 案例 |      |
| case 2.2.1 H2                    | H2 数据库 |      |
| case 2.2.2 h2spring              | H2 Spring 案例 |      |
| case 2.2.3 oracle                | Oracle 数据库 |      |
| case 2.3.1 redis                 | Redis 缓存 |      |
| case 2.3.2 redis down            |      |      |
| case 2.3.3 redis cluster         | Redis Cluster 集群案例 |      |
| case 2.4 mybatis                 | Mybatis |      |
| case 2.4.1 mycat                 | Mycat 分布式 |      |
| case 2.4.2 mybatis-generator-gui | Mybatis 自动生成表 |      |
| case 2.4.3 PageHelper            | 分页 |      |
| case 2.4.4 mybatis-plus          | Mybatis-Plus 使用 |      |
| case 2.5 连接池性能对比          | 连接池性能对比 |      |
| case 2.5.1 Hikari                | Hikari 连接池 |      |
| case 2.5.2 Druid                 | Druid 连接池 |      |
| case 2.6 multiply-source         | 多数据源 |      |
| case 2.7 encache                 | Encache 缓存 |      |
| case 2.8.1 TCC-Transaction       |      |      |
| case 2.8.2 hmily                 |      |      |
| case 2.8.3 seata                 | Seate |      |
| case 2.9.1 Hibernate             |      |      |
| case 2.9.2 Hibernate NoSpring    | Hibernate No Spring 使用方式 |      |
| case 3.1 security                |      |      |
| case 3.2 securitybig             | Spring Security |      |
| case 3.3 shiro                   |      |      |
| case 3.4 security                |      |      |
| case 3.4.1 securitySaml          |      |      |
| case 3.5 Apereo                  |      |      |
| case 3.5.1 ApereoWar             |      |      |
| case 3.6 pac4j                   |      |      |
| case 3.7.1 saml                  |      |      |
| case 3.7.2 Spring SAML           |      |      |
| case 4.1 RabbitMQ                |      |      |
| case 4.1.1 simpleRabbitMQ        |      |      |
| case 4.2 kafka                   |      |      |
| case 4.3 ActiveMQ                |      |      |
| case 4.4 canal                   |      |      |
| case 4.5 cloud stream            |      |      |
| case 5.1 elasticsearch           |      |      |
| case 5.1.2 ElasticSearch BigData |      |      |
| case 5.2 elk logsystem           |      |      |
| case 5.3 docker                  |      |      |
| case 5.4 arthas                  |      |      |
| case 5.5 tablesaw                |      |      |
| case 6.1 ADBundles               | AD Bundle 推送 |      |
| case 6.1.1 ADBundlesSelf         | AD 个人Bundles |      |
| case 6.1.2 ADBundlesDemo         |      |      |
| case 6.1.3 ADPlugin              |      |      |
| case 6.1.4 adSource              |      |      |
| case 6.1.4 adSource.zip          |      |      |
| case 6.1.9 ADBundles 无用封存    |      |      |
| case 6.14 adSource               |      |      |
| case 6.2 LDAPBundles             |      |      |
| case 6.2.1 LDAPBundlesSelf       |      |      |
| case 6.2.2 LDAPBundlesDemo       |      |      |
| case 6.2.3 ldapPlugins           |      |      |
| case 6.2.4 Ldaptive              |      |      |
| case 6.3.1 exchangeSource        |      |      |
| case 6.4 Adzure                  |      |      |
| case 6.4.1 azure-web             |      |      |
| case 6.4.2 azure-bundles         |      |      |
| case 6.4.5 gang-azure-oauth      |      |      |
| case 6.5.1 SDK_work_wechat       |      |      |
| case 6.5.2 SDK_DingTalk          |      |      |
| case 7.1 syncope                 |      |      |
| case 7.2 sharding-jdbc           |      |      |
| case 7.3 quartz                  |      |      |
| case 7.3.1 quartz-database       |      |      |
| case 7.3.2 quartz-auto           | test |      |
| case 7.4 xxl-job                 |      |      |
| case 7.5 ibeetl                  |      |      |
| case 7.7 velocity                |      |      |
| case 7.8 spark                   |      |      |
| case 7.9 flowable                |      |      |
| case 7.9.1 flowable-java         |      |      |
| case 7.9.2 activiti              |      |      |
| case 7.9.2 flowable-ui           |      |      |
| case 7.9.3 flowable-ui           |      |      |
| case 7.9.4 web-flow              |      |      |
| case 8.1 ELK                     |      |      |
| case 8.2                         |      |      |
| case 8.2 sofaboot                |      |      |
| case 8.3 grafana                 |      |      |
| case 9.1 web baidu map           |      |      |
| case 9.2 VUE                     |      |      |
| case 9.3 selenium                |      |      |
| case 9.4 javafx                  |      |      |
| case antEngine                   |      |      |
| case antsso                      |      |      |
| case API                         |      |      |
| case cloud                       |      |      |
| case GangCommon                  |      |      |
| case gateway                     |      |      |
| case gradle                      |      |      |
| case Java Other                  |      |      |
| 子 -  case annotation               | 自定义注解 |      |
| 子 -  case collection               | 集合操作 |      |
| 子 -  case ding                     | 钉钉 |      |
| 子 -  case dingding                 | 钉钉 |      |
| 子 -  case email                    | 邮箱操作 |      |
| 子 -  case encode-decode            | 加密解密 |      |
| 子 -  case get resource file        |      |      |
| 子 -  case git-plugins              | Git 插件 |      |
| 子 -  case invoke                   | 代理 |      |
| 子 -  case Java IO                  |      |      |
| 子 -  case java-reflect             |      |      |
| 子 -  case request util             |      |      |
| 子 -  case sms                      |      |      |
| 子 -  case stream study             |      |      |
| 子 -  case utils                    |      |      |
| case maven                       |      |      |
| case netty                       |      |      |
| case Origin Source               | 源代码 |      |
| case sourceAnalysis              |      |      |
| case spring                      | Spring 系列集合 |      |
|  子 -  case admin                                  | Spring Admin |      |
|  子 -  case configration                           | Spring Configration |      |
|  子 -  case devtool                                | Spring devtoo; |      |
|  子 -  case fileupload                             | Sping 文件上传 |      |
|  子 -  case many application config test多配置文件 |      |      |
|  子 -  case resource                               | Spring resource 处理 |      |
|  子 -  case RestTemplate                           | Spring RestTemplate 工具 |      |
|  子 -  case Sping ExceptionHandle                  | Spring 自定义异常处理 |      |
|  子 -  case spring cache                           | Spring Cache 缓存操作 |      |
|  子 -  case Spring Credhub                         |      |      |
|  子 -  case Spring Filter                          |      |      |
|  子 -  case Spring LDAP                            | Spring LDAP 操作 |      |
|  子 -  case spring osgi                            |      |      |
|  子 -  case spring ssh                             | Spring SSH ? |      |
|  子 -  case Spring 序列化                          |      |      |
|  子 -  case SpringAdmin                            |      |      |
|  子 -  case springAutoConfiguration                |      |      |
|  子 -  case springAutowird                         |      |      |
|  子 -  case SpringBatch                            | Spring Batch 批处理 |      |
|  子 -  case SpringBatch Sample                     |      |      |
|  子 -  case SpringInterceptors                     |      |      |
|  子 -  case SpringModuleManage                     |      |      |
|  子 -  case springThreadLocal                      |      |      |
|  子 -  case springTogether                         |      |      |
|  子 -  case SpringWebFlux                          |      |      |
| case sso                         |      |      |
| case Unit Test                   |      |      |
| case utils_framework             |      |      |
| case utils_plugins               |      |      |
| case 前端                        |      |      |
| case 协议                        |      |      |
| case 压力测试                    |      |      |
| case 多线程                      |      |      |
|   子 -  case AQS                 | 多线程 AQS 操作 |      |
|   子 -  case Commonly            |      |      |
|   子 -  case consumer-produce    |      |      |
|   子 -  case container           | 多线程容器 |      |
|   子 -  case design              | 多线程设计 |      |
|   子 -  case future              | 多线程 Future 操作 |      |
|   子 -  case headPoolExecutor    | 高并发连接池 |      |
|   子 -  case high thread         | 高并发 |      |
|   子 -  case lock                | 线程 Lock |      |
|   子 -  case queue               | Queue 工具 |      |
|   子 -  case spring_thread       | Spring 线程 |      |
|   子 -  case thread_spring_cloud |      |      |
|   子 -  case thread_test         |      |      |
|   子 -  case thread_utils        |      |      |
|   子 -  case 线程池              |      |      |
|   子 -  doc                      |      |      |
| case 大数据                      |      |      |
| case 性能测试                    |      |      |
| case 算法                        |      |      |
| case 骚操作                      |      |      |


