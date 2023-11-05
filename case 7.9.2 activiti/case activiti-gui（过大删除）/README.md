# Spring-boot-activiti
  在常用的ERP系统、OA系统的开发中，工作流引擎是一个必不可少的工具。本项目旨在基于Spring boot这一平台，整合业界流行的工作流引擎Activiti，并建立了两个完整的工作流进行演示：请假OA和采购流程。

其中包含的内容如下：

1.不采用activiti自带的用户、角色功能，因为过于简单，转而自行实现一个用户、角色、权限的三级结构，用户到角色，角色到权限均为多对多映射，持久层框架使用mybatis的collection和association标签嵌套实现；

2.使用默认的用户登录后（用户名xiaomi，密码1234），可看到已部署好的两个流程，请假OA和采购流程，其中，请假OA包含了用户任务、排他网关、起始结束事件，较为简单；采购流程除此之外，还使用了异常结束事件、子流程和边界事件的使用；

3.两个流程均包含了待办任务签收、运行流程进度追踪、已运行完流程历史记录查看的功能，运行流程进度在流程图中以红色标注；

4.使用时，将流程数据和业务数据相分离，使用业务号（businessKey）建立关联流程数据和业务数据的桥梁，使其相互可以访问,业务数据的主键即为业务号；

5.本系统所有表单均使用普通表单，而不是activiti的动态表单和外置表单，这样做是为了分表存放业务数据和流程数据;

6.系统前端采用基于Bootstrap的模板devoops建立。https://github.com/shenzhanwang/devoops

7.起始页面入口：http://localhost:8888/login
使用前，编译(可直接在myeclipse完成)：
```
mvn clean install
```
8. swagger入口：http://localhost:8888/swagger-ui.html
9. 新增流程图设计器，将activiti-explorer.war放入Tomcat8.5的webapps目录下，启动Tomcat，访问http://localhost:8080/activiti-explorer ，即可进入登录页面，用户名和密码都是kermit：
![输入图片说明](https://images.gitee.com/uploads/images/2021/0419/164940_5fb4fee1_1110335.png "1618821418(1).png")
点击流程->流程设计工作区->新建模型，填入模型名称，点击创建按钮即可开始流程图设计：
![输入图片说明](https://images.gitee.com/uploads/images/2021/0419/165212_01f3e2f0_1110335.png "1618821830(1).png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0419/165219_e3328ba1_1110335.png "1618821895(1).png")
设计完后，可以导出源文件：
![输入图片说明](https://images.gitee.com/uploads/images/2021/0419/165618_55252965_1110335.png "1618822550(1).png")
10. 新增动态菜单权限，通过给不同用户分配不同的角色，使他们看到不同的菜单。
11. 请假流程直接分配到具体审批人，只有指定的审批人才能审批。采购流程按照候选者组进行任务签收，只要能拥有相关菜单的权限即可审批。

12.效果图：

![输入图片说明](https://images.gitee.com/uploads/images/2021/0422/104928_60dfdd31_1110335.gif "1.gif")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0422/105155_8e981bdd_1110335.gif "2.gif")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0422/103749_35d4a34f_1110335.png "1.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0422/103800_372a4343_1110335.png "2.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0422/103807_35440eec_1110335.png "3.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0422/103817_aac7ae0a_1110335.png "4.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0422/103824_c97e9519_1110335.png "5.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0422/103832_651d1585_1110335.png "6.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0422/103840_47bc8cef_1110335.png "7.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0422/103849_03b1f290_1110335.png "8.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0422/103856_8b35cdd8_1110335.png "9.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0422/103904_8e5819ad_1110335.png "10.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0422/103911_751e6e83_1110335.png "11.png")
### 附录：中央技术储备仓库（Central Technique Reserve Repository）

#### 基础篇:职业化，从做好OA系统开始
1. [Spring boot整合Mybatis实现增删改查（支持多数据源）](https://gitee.com/shenzhanwang/SSM)![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")
2. [Struts2,Hibernate,Spring三大框架的整合实现增删改查](https://gitee.com/shenzhanwang/S2SH)
3. [Spring,SpringMVC和Hibernate的整合实现增删改查](https://gitee.com/shenzhanwang/SSH)
4. [Spring boot整合activiti工作流引擎实现OA开发](https://gitee.com/shenzhanwang/Spring-activiti)![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")
5. [Spring发布与调用REST风格的WebService](https://gitee.com/shenzhanwang/Spring-REST)
6. [Spring boot整合Axis调用SOAP风格的web服务](https://gitee.com/shenzhanwang/Spring-axis)
7. [Spring boot整合Apache Shiro实现RBAC权限控制](https://gitee.com/shenzhanwang/Spring-shiro)
8. [使用Spring security实现RBAC权限控制](https://gitee.com/shenzhanwang/spring-security-demo)
9. [Spring整合Jasig CAS框架实现单点登录](https://gitee.com/shenzhanwang/Spring-cas-sso)

#### 中级篇：中间件的各种姿势
10. [Spring boot整合mongoDB文档数据库实现增删改查](https://gitee.com/shenzhanwang/Spring-mongoDB)
11. [Spring连接Redis实现缓存](https://gitee.com/shenzhanwang/Spring-redis)
12. [Spring连接图存数据库Neo4j实现增删改查](https://gitee.com/shenzhanwang/Spring-neo4j)
13. Spring boot整合列存数据库hbase实现增删改查
14. [Spring平台整合消息队列ActiveMQ实现发布订阅、生产者消费者模型（JMS）](https://gitee.com/shenzhanwang/Spring-activeMQ)
15. [Spring boot整合消息队列RabbitMQ实现四种消息模式（AMQP）](https://gitee.com/shenzhanwang/Spring-rabbitMQ)
16. Spring boot整合kafka 2.1.0实现大数据消息管道
17. [Spring boot整合websocket实现即时通讯](https://gitee.com/shenzhanwang/Spring-websocket)![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")
18. [Spring security整合oauth2实现token认证](https://gitee.com/shenzhanwang/Spring-security-oauth2)
19. [Spring MVC整合FastDFS客户端实现文件上传](https://gitee.com/shenzhanwang/Spring-fastdfs)
20. 23种设计模式，源码、注释、使用场景 
21. [使用ETL工具Kettle的实例](https://gitee.com/shenzhanwang/Kettle-demo)
22. Git指南和分支管理策略 
23. 使用数据仓库进行OLAP数据分析（Mysql+Kettle+Zeppelin）
#### 高级篇：架构之美
24. [zookeeper原理、架构、使用场景和可视化](https://gitee.com/shenzhanwang/zookeeper-practice)
25. Spring boot整合Apache dubbo v2.7.5实现分布式服务治理（SOA架构） ![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题") 
>  包含组件Spring boot v2.2.2+Dubbo v2.7.5+Nacos v1.1.1
<a href="https://images.gitee.com/uploads/images/2020/0114/084731_fd0b7a82_1110335.gif" target="_blank">效果图</a>
26. 使用Spring Cloud Alibaba v2.1.0实现微服务架构（MSA架构）![输入图片说明](https://img.shields.io/badge/-%E6%8B%9B%E7%89%8C-yellow.svg)   
>  包含组件Nacos+Feign+Gateway+Ribbon+Sentinel+Zipkin
<a href="https://images.gitee.com/uploads/images/2020/0106/201827_ac61db63_1110335.gif" target="_blank">效果图</a>
27. 使用jenkins+centos+git+maven搭建持续集成环境自动化部署分布式服务 
28. 使用docker+compose+jenkins+gitlab+spring cloud实现微服务的编排、持续集成和动态扩容 
29. 使用FastDFS搭建分布式文件系统（高可用、负载均衡）
30. 搭建高可用nginx集群和Tomcat负载均衡 
31. 使用mycat实现Mysql数据库的主从复制、读写分离、分表分库、负载均衡和高可用 
32. [Spring boot整合Elastic search实现全文检索和大数据分析](https://gitee.com/shenzhanwang/Spring-elastic_search) ![输入图片说明](https://img.shields.io/badge/-%E6%8B%9B%E7%89%8C-yellow.svg "在这里输入图片标题")
#### 特别篇：分布式事务和并发控制
33. 基于可靠消息最终一致性实现分布式事务（activeMQ）
34. Spring boot dubbo整合seata实现分布式事务![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")
> 包含组件nacos v1.1.0 + seata v0.7.1 +spring boot dubbo v2.7.5
<a href="https://images.gitee.com/uploads/images/2020/0119/112233_62a33a77_1110335.gif" target="_blank">效果图</a>
35. Spring cloud alibaba v2.1.0整合seata实现分布式事务 ![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")
> 包含组件nacos v1.1.0 + seata v0.7.1 +spring cloud alibaba v2.1.0
<a href="https://images.gitee.com/uploads/images/2020/0119/134408_ee14a016_1110335.gif" target="_blank">效果图</a>
36. 并发控制：数据库锁机制和事务隔离级别的实现![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题") 
37. 并发控制：使用redis实现分布式锁  ![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")
38. 并发控制：使用zookeeper实现分布式锁 
39. 并发控制：Java多线程编程实例
40. 并发控制：使用netty实现高性能NIO通信 
### 视频教程
![输入图片说明](https://images.gitee.com/uploads/images/2021/0115/082443_6e3b60c8_1110335.png "封面.png")

<a href="https://www.roncoo.com/view/1303618857169457154" target="_blank">在线观看（含源码和PPT）</a>