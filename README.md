# Case 案例合集

此项目定位是一个开箱即用的百宝箱项目 , 现阶段正在完善完善文档结构 , 项目中的 demo 多来源于热门项目或者生产中常用的技术 .
整个项目的版本多数是基于SpringBootV2 , 为了兼容普遍常用的常用版本 , 未使用最新版.

:stuck_out_tongue_closed_eyes: 有些Demo 只是建了一个项目 , 预计在2021年5月前全部完善 :punch: :punch: :punch::muscle:

## 一 . 项目整体结构 
该项目为了避免编译的困扰和易用性 , 最终未采用聚合形式 , 整体使用的是Maven结构 ,  

项目总共分为以下几大模块 , 其中大部分已经完成 , 后续部分也会尽快完成以及相关文档

```JAVA
.
|-- README.md
|-- case 0 All Parent
|-- case 1 Basic Module
|-- case 1.1 Base Application
|-- case 1.1.2 Java Web No Spring
|-- case 1.1.3 Java Application
|-- case 1.2 Base Utils
|-- case 2 Web Module
|-- case 2.1 Spring Web
|-- case 2.1.1 Webflux
|-- case 2.2 Freemarker
|-- case 2.3. easyrest
|-- case 2.4 Thymeleaf
|-- case 2.5 Jersey Web
|-- case 2.5.1 Jersey-springboot
|-- case 2.5.2 Jersey config
|-- case 2.6.1 fileUpload
|-- case 2.6.2 okhttp
|-- case 2.6.3 swagger
|-- case 2.6.4 swaggerv3
|-- case 2.6.5 websocket
|-- case 2.6.6 websocketjava
|-- case 2.6.7 HttpClient And RestTemplate
|-- case 2.6.8 dwr
|-- case 2.7.1 cxf
|-- case 2.7.2 HTTPS
|-- case 2.8 rmi
|-- case 3 Data Module
|-- case 3.1.1 JPA example
|-- case 3.1.2 JPA And Redis
|-- case 3.2.1 Database H2
|-- case 3.2.2 Database H2 By Spring
|-- case 3.2.3 Database oracle
|-- case 3.2.4 Database DM8
|-- case 3.3.1 redis
|-- case 3.3.2 redis down
|-- case 3.3.3 redis cluster
|-- case 3.3.4 Redis Lua
|-- case 3.3.9 encache
|-- case 3.4.1 Mybatis
|-- case 3.4.2 mybatis-generator-gui
|-- case 3.4.3 PageHelper
|-- case 3.4.4 mybatis-plus
|-- case 3.6.1 Hibernate
|-- case 3.6.1 Mycat
|-- case 3.6.2 Hibernate NoSpring
|-- case 3.6.2 TCC-Transaction
|   |-- default-springboot-feign-eureka
|   |-- doc.txt
|   |-- sample-cloud
|   |-- sample-default-springboot
|   |-- sample-demo
|   |-- sample-dubbo
|   |-- sample-dubbo-author
|   |-- sample-dubbo-author-springboot
|   `-- sample-http-author-springboot
|-- case 3.6.4 seata
|   |-- Seate Demo
|   |-- case seata-demo-dubbo-springboot
|   `-- case seata-demo-nacos-self
|-- case 3.6.5 sharding-jdbc
|   |-- case Depots table
|   |-- case sample-demo
|   |-- case sample-demo-jpa
|   |-- case sample-demo-jpa-boot
|   |-- case sample-distributed-transaction
|   `-- case sample-read-write-demo-jpa-boot
|-- case 3.6.6 easyTransaction
|-- case 3.7 \301\254\275\323\263\330\320\324\304\334\266\324\261\310
|-- case 3.7.1 Hikari
|-- case 3.7.2 Druid
|-- case 3.8 multiply-source
|-- case 4 Security Module
|-- case 4.1.1 How do Security
|-- case 4.2.1 Spring Security Demo
|-- case 4.2.1 Spring Security Demo Filter
|-- case 4.2.1 Spring Security OAuth
|-- case 4.2.2 Spring Security Sample
|-- case 4.2.3 Spring Security SAML
|-- case 4.2.4 Spring Security Medium Demo
|-- case 4.3.1 Shiro Demo
|-- case 4.4.1 Apereo CAS
|-- case 4.4.2 Apereo War
|-- case 4.5.1 keycloak doc
|-- case 4.6.1 pac4j sample
|-- case 4.6.2 pac4j sample 380
|-- case 4.7.1 OAuth
|-- case 4.7.1.1 OAuth PKCE
|-- case 4.7.2 Azure OAuth
|-- case 4.7.3 Internet OAuth
|-- case 4.7.4 Spring SAML
|-- case 4.7.4.1 SAML Sample
|-- case 4.7.7.1 OTP Sample
|-- case 4.7.7.2 Google Authentication
|-- case 4.7.8.1 radius
|-- case 4.7.8.2 Alibaba Biology
|-- case 4.7.8.3 Alibaba Biology
|-- case 5 MQ Module
|-- case 5.1.1 RabbitMQ
|-- case 5.1.2 RabbitMQ Simple
|-- case 5.2 kafka
|-- case 5.3 ActiveMQ
|-- case 5.4 canal
|-- case 5.5 Spring Cloud Stream
|-- case 5.5.1 Rabbit Cloud Stream
|-- case 5.6 IBM MQ
|-- case 6 DataSync Module
|-- case 6.1.1 ADBundlesSelf
|-- case 6.1.2 ADBundlesDemo
|-- case 6.1.3 ADPlugin
|-- case 6.1.4 adSource
|-- case 6.1.5 ADBundles
|-- case 6.1.6 AD exchangeSource
|-- case 6.1.9 ADBundles \316\336\323\303\267\342\264\346
|-- case 6.2.1 LDAP Bundles
|-- case 6.2.2 LDAP Bundles Demo
|-- case 6.2.3 LDAP Plugins
|-- case 6.2.4 Ldaptive
|-- case 6.2.5 LDAP Auth
|-- case 6.2.6 LdapActive JDK 11
|-- case 6.2.7 SpringLdap
|-- case 6.2.8 LDAPBundlesSelf
|-- case 6.4.1 Azure Web
|-- case 6.4.2 Azure Bundles
|-- case 6.4.3 Adzure
|-- case 6.4.5 gang-azure-oauth
|-- case 6.5.1 SDK Work Wechat
|-- case 6.5.2 SDK DingTalk
|-- case 7 Template And Flow Module
|-- case 7.1 syncope
|-- case 7.3 quartz
|-- case 7.3.1 quartz-database
|-- case 7.3.2 quartz-auto
|-- case 7.4 xxl-job
|-- case 7.5 ibeetl
|-- case 7.7 velocity
|-- case 7.8 spark
|-- case 7.9 flowable
|-- case 7.9.1 flowable-java
|-- case 7.9.2 activiti
|   |-- case DefaultActiviti
|   |-- case SimpleActiviti7
|   |-- case SimpleActiviti7ByWebPage
|   |-- case template-demo
|-- case 7.9.2 flowable-ui
|-- case 7.9.3 flowable-ui
|-- case 7.9.4 SpringWebFlow Demo
|   |-- case SpringWebFlow
|   |-- case SpringWebFlowBusiness
|   |-- case SpringWebFlowCustomize
|   |-- case SpringWebFlowOfficial
|   `-- case SpringWebFlowSimple
|-- case 8 Other
|-- case 8.1.1 ELK Sample
|-- case 8.1.1.1 Elasticsearch
|-- case 8.1.2 ElasticSearch BigData
|-- case 8.1.3 elk logsystem
|-- case 8.2 sofaboot
|-- case 8.3 grafana
|-- case 8.4 skywalking
|-- case 8.5 tablesaw
|-- case 8.6 arthas
|-- case 9 Web
|-- case 9.1 web baidu map
|-- case 9.2 VUE
|-- case 9.3 selenium
|-- case 9.4 javafx
|-- case 9.6 JQuery
|-- case 9.8 Web Plugin
|-- case 9.9 Page Demo
|-- case Algorithms
|-- case Ant Common
|-- case Ant Engine
|-- case Ant SSO
|-- case Cloud Demo
|   |-- case CopyTemplate Dubbo \273\371\264\241\317\356\304\277
|   |-- case CopyTemplate Eureka JPA \273\371\264\241\317\356\304\277
|   |-- case CopyTemplate Eureka \273\371\264\241\317\356\304\277
|   |-- case CopyTemplate Nacos \273\371\264\241\317\356\304\277
|   |-- case SpringAlibabaCloud
|   |-- case SpringCloudCircuitBreaker
|   |-- case SpringCloudHystrix
|   |-- case apollo
|   |-- case base enviroment
|   |-- case cloud eureka
|   |-- case cloudBucket
|   |-- case eureka
|   |-- case feign
|   |-- case nacos SpringBoot
|   |-- case nacos client
|   |-- case nacos cloud
|   |-- case nacos simple
|   |-- case nacos \270\337\277\311\323\303
|   |-- case nacosCluster
|   |-- case resilience4j
|   |-- case resilience4j-spring
|   |-- case restTemplate
|   |-- case security
|   |-- case sentinel
|   |-- case sentinelCloud
|   |-- case stream
|   `-- case websocket
|-- case Cloud Frame
|   |-- case Apollo Demo
|   |-- case Apollo Sample
|   |-- case Dubbo Complete
|   |-- case Dubbo Nacos
|   |-- case Dubbo Nacos Complex
|   |-- case Dubbo Sample
|   |-- case Dubbo Zookeeper
|   |-- case Dubbo3 Sample Template
|   `-- case Dubbo30 Annotation Zookeeper
|-- case Compile gradle
|   |-- case many
|   |-- case sample
|   `-- case spring
|-- case Compile maven
|   |-- case add lib
|   |-- case bootdependency
|   |-- case buildJar
|   |-- case buildOrder
|   |-- case default_JAR
|   |-- case parentAndChild
|   `-- case polymerization
|-- case Design Business
|   |-- \277\311\315\317\266\257\304\277\302\274\311\350\274\306
|   `-- \326\270\266\250\267\275\267\250\314\355\274\323\307\260\326\303\272\363\326\303\264\246\300\355
|-- case Design Pattern
|   |-- HELP.md
|   |-- case design.iml
|   |-- mvnw
|   |-- mvnw.cmd
|   |-- pom.xml
|   `-- src
|-- case Exception
|   `-- case json exchange
|-- case Module API
|   |-- case github
|   `-- case githubAPI
|-- case Module Android
|-- case Module DevOps
|   |-- case docker
|   |-- case jetty
|   |-- case k8s
|   |-- case license
|   |-- case nginx
|   |-- case skywalking
|   `-- case tomcat
|-- case Module Gateway
|   |-- case Linkerd
|   |-- case Nginx
|   |-- case OpenResty
|   |-- case gateway
|   |-- case kong
|   |-- case lua
|   |-- case nodejs
|   |-- case soul
|   |-- case tyk
|   `-- case zuul
|-- case Module IOS
|-- case Module IntelliJ Plugin
|-- case Module JVM
|   `-- case jol
|-- case Module Netty
|   |-- case nettySimple
|   |-- netty-example
|   `-- netty-server
|-- case Module Thread
|   |-- case AQS
|   |-- case Common Thread Demo
|   |-- case ForkJoin
|   |-- case Highly Concurrent
|   |-- case Thread Collection And Object
|   |-- case consumer-produce
|   |-- case container
|   |-- case design
|   |-- case future
|   |-- case headPoolExecutor
|   |-- case queue
|   |-- case spring_thread
|   |-- case thread_spring_cloud
|   |-- case \317\337\263\314\263\330
|   `-- doc
|-- case Module Unit Test
|   |-- case PowerMock
|   |-- case Simple
|   |-- case h2 jpa
|   `-- case junit
|-- case Origin Source
|   |-- case Apareo CAS
|   |-- case Java
|   |-- case Java Thread
|   |-- case Mybatis
|   |-- case Spring Security
|   |-- case Spring Source
|   |-- case SpringBoot
|   |-- case SpringBoot1.5
|   |-- case SpringBootAOP
|   |-- case SpringBootIOC
|   |-- case SpringMVC
|   `-- case pac4j
|-- case Other Java
|   |-- case Java IO
|   |-- case Kryo
|   |-- case Single
|   |-- case annotation
|   |-- case collection
|   |-- case ding
|   |-- case dingding
|   |-- case email
|   |-- case encode-decode
|   |-- case get resource file
|   |-- case git-plugins
|   |-- case guava
|   |-- case https
|   |-- case interface default
|   |-- case invoke
|   |-- case java-reflect
|   |-- case jcommander \303\374\301\356\320\320\264\246\300\355
|   |-- case mapStruct
|   |-- case request util
|   |-- case simple-demo
|   |-- case sms
|   |-- case stream study
|   |-- case systemInfo
|   |-- case utils
|   |-- case \312\375\276\335\300\340\320\315\327\252\273\273
|   |-- case \320\241\326\252\312\266\265\343
|   `-- engine-server
|-- case Other Plugin
|   |-- case OSS fileSystem
|   |-- case database excel
|   |-- case easyexcel
|   |-- case gecco
|   |-- case ip find address
|   `-- case otp_java
|-- case Other Python
|-- case Other Spring
|   |-- case Bean Manger
|   |-- case RestTemplate 
|   |-- case Sping ExceptionHandle
|   |-- case Spring Credhub
|   |-- case Spring Devtools
|   |-- case Spring Filter
|   |-- case Spring LDAP
|   |-- case Spring Web
|   |-- case Spring flow
|   |-- case Spring \320\362\301\320\273\257
|   |-- case SpringAdmin
|   |-- case SpringBatch
|   |-- case SpringBatch Sample
|   |-- case SpringBoot SourceCode
|   |-- case SpringConfiguration
|   |-- case SpringInterceptors
|   |-- case SpringModuleManage
|   |-- case SpringWebFlux 
|   |-- case admin
|   |-- case devtool
|   |-- case fileupload
|   |-- case hateoas
|   |-- case many application config test\266\340\305\344\326\303\316\304\274\376
|   |-- case reload
|   |-- case remote
|   |-- case resource
|   |-- case spring cache
|   |-- case spring osgi
|   |-- case spring ssh
|   |-- case spring thread local \323\260\317\354\262\342\312\324
|   |-- case spring-boot-devtools-demo
|   |-- case spring-boot-devtools-source
|   |-- case springAutowird
|   |-- case springLog
|   |-- case springThreadLocal
|   `-- case springTogether
|-- case sso
|   |-- case dingtalk
|   |-- case ltpa
|   |-- case saml2
|   `-- case sso common
|-- case \264\363\312\375\276\335
|   `-- case Hbase
|-- case \306\344\313\373\262\331\327\367
|   |-- case Mysql
|   |-- case Oracle
|   |-- case git \302\337\274\255
|   |-- case linux
|   |-- case stream
|   |-- case \305\372\264\246\300\355\275\305\261\276
|   `-- case \325\375\324\362
|-- case \320\324\304\334\262\342\312\324
|   |-- case String \317\265\301\320
|   |-- case cloud-eureka
|   `-- case orm
|-- case \321\271\301\246\262\342\312\324
|   |-- case JMeter
|   |-- case Simple Demo
|   `-- case miaosha
|-- case_3.6.3_tcc_hmily
|   |-- hmily-demo-brpc
|   |-- hmily-demo-common
|   |-- hmily-demo-dubbo
|   |-- hmily-demo-grpc
|   |-- hmily-demo-motan
|   |-- hmily-demo-sofa
|   |-- hmily-demo-springcloud
|   |-- hmily-demo-tars
|   |-- logs
|   |-- pom.xml
|   `-- sql
|-- database
|   |-- gang2020.sql
|   `-- gangV2.sql
|-- doc
|   `-- pdf
|-- group sso oauth
|   |-- case passhst
|   |-- case uauth
|   |-- case ucloud
|   `-- case yunzijia
|-- source
|   `-- taobao
|-- test head.txt
|-- test.bat
`-- tree.txt

```



## 二 . 文档地址

 :point_right:  http://www.antblack.xyz/blog-view.html?type=Case%20Project

CSDN :cloud: ​ https://blog.csdn.net/zzg19950824

掘金 :point_right:https://juejin.cn/user/3790771822007822 

