# MyCat 分库分表

------

[TOC]



## 前言

```
> 完整文档
http://www.mycat.org.cn/document/mycat-definitive-guide.pdf

> 海量数据的处理方式 :
	- 联机数据处理 (OLP) : 面向交易的系统 ,原始数据传输到计算中心快速处理,并且提供结果
	- 联机分析处理 (OLAP) : 多维方式对数据进行分析 ,查询 报表,配合挖掘工具 ,统计分析工具
	
> 数据切分
	: 纵向切分 : 将不同的表切分到不同的数据库之上
		- 优点 : 
            - 业务清晰 , 规则明确
            - 易于整合和扩展
            - 数据维护简单
         - 缺点 :    
            : 业务无法连表
            : 性能不易据哦组
            : 事务不易处理
	: 横向切分 : 将相同表中的不同数据切分到不同的表上
		- 常见规则 : 用户ID取模 . 日期处理 , 指定字段取模
		- 优点 : 可以join , 性能易扩展 , 应用端不用改造 , 负载均衡能力提升
		- 缺点 : 拆分规则不易抽象 , 不易解决一致性 ,跨库join 不易处理

> 切分思路 :
	- 能不切分即不切分
	- 选择合适的切分规则
	- 数据冗余和表分组降低join 难度
	- 避免使用 join
		
		
```

## 一 . MyCat  简介

### 1.1 简介

```
> MyCat 基于 Cobar 实现 , Cobar 基于Java 开发 , 实现了 Msql 公开的二进制传输协议 , 伪装为一个Mysql Server .Cobar 的特性是分库分表 , 尽管一个Table 会分在多个物理机上面的Mysql 存储 ,但是对于使用者而言就是一个物理表(当执行一条语句的时候 ,也是发送到多个表中执行)

> Cobra 常见的问题:
	- cobra 会假死 : Cobra 前端NIO ,后端阻塞
	- 不支持读写分离
	- 不可控的主从切换
	- 无奈的热加载
	- 
	
	
> MyCat :
	- 开源的分布式数据库系统 ,实现了 MySql 协议的server , 前端用户可以堪为一个数据库代理 , 后端可以用Mysql 原生协议与多个Mysql 服务器通信
	
	Mycat 就是 MySQL Server，而 Mycat 后面连接的 MySQL Server，就好象是 MySQL 的存储引擎,如InnoDB，MyISAM 等，因此，Mycat 本身并不存储数据，数据是在后端的 MySQL 上存储的，因此数据可靠性以及事务等都是 MySQL 保证的
	Mycat 就是一个近似等于 MySQL 的数据库服务器，你可以用连接 MySQL 的方式去连接 Mycat（除了端口不同，默认的 Mycat 端口是 8066 而非 MySQL 的 3306，因此需要在连接字符串上增加端口信息），大多数情况下，可以用你熟悉的对象映射框架使用 Mycat，但建议对于分片表，尽量使用基础的 SQL 语句，因为这样能达到最佳性能，特别是几千万甚至几百亿条记录的情况下


> MyCat 的几个核心操作 :
	- 拦截 : 拦截用户发送过来的多个SQL语句 ,然后进行特定分析 : 分片分析 , 路由分析 , 读写分离分析 , 缓存分析 ,然后发送到后端的真实数据库 , 并且将返回结果做适当处理,返回给用户
	: 查看涉及的表 , 及表的定义
	: 判断是否有分片规则 , 获取SQL里分片字段的值 ,匹配分片函数 ,得到SQL对应的分片列表
	
```

### 1.2 MyCat 中的概念

```
> 数据中间件 : 介于数据库和应用之间 ,进行数据处理和交互的中间服务
> 逻辑库 : 
> 逻辑表 : 对于应用而言 ,读写数据的表就是逻辑表
	- 分片表 : 原有的很大数据的表 ,切分到多个数据库
	- 非分片表 :
	- ER表 : 关系模型表
	- 全局表 : 字典表
	
> 分片节点 : 当一个表被分在不同的分片数据库上面 , 每个表分片所在的数据库就是分片节点
> 节点主机 : 一个或者多个分片节点分布的主机
> 分片规则 : 
> 全局序列号 : 之前的主键策略会失效 ,需要全局序列号解决
> 多租户 : 多用户的环境下使用相同的系统 ,并且保证隔离性
	- 独立数据库 : 一个用户一个数据库
		- 简化数据库模型的扩展设计 , 便于恢复数据  ,但是 运维更加复杂
	- 共享数据库 : 共享DataBaase , 每个租户一个Schema
    	- 每个数据库可以正常多个租户
    	- 故障不易回复
    - 共享数据库 ,共享数据架构 ,通过 TenantID 七分租户的数据
    	- 易于实现 , 隔离级别低 ,安全性低
    	
```

### 1.3 MyCat 基础知识点

```
conf -> 目录下存放配置文件
	- server.xml 是 Mycat 服务器参数调整和用户授权的配置文件
	- schema.xml 是逻辑库定义和表以及分片定义的配置文件
	- rule.xml 是分片规则的配置文件，分片规则的具体一些参数信息单独存放为文件，也在这个目录下，配置文件修改，需要重启 Mycat 或者通过 9066 端口 reload。
```



## 二 . MyCat 使用

### 2.1 简易安装

```
> Mycat 防火墙配置
在 server.xml 中配置 , 详见文档

> schema.xml
	: 该文件管理着MyCat的逻辑库 , 表 ,分片规则
	
> 启动

> 连接
mysql -h127.0.0.1 -uroot -p123456 -P9066 -dssc

// Step 1 : 学习 , 下载源码学习
https://github.com/MyCATApache/Mycat-Server

// Step 2 : 使用镜像创建
http://dl.mycat.org.cn/1.6.7.4/Mycat-server-1.6.7.4-release/


// Step 3 : 配置
-> schema.xml : 管理着 MyCat 的逻辑库、表、分片规则、DataNode 以及 DataSource

// Step 4 : 启动
-> Windows 版本
	-/bin/startup_nowrap.bat
```

### 2.2 Mycat-schema.xml

```java
// Schema : 
<schema name="TESTDB" checkSQLschema="false" sqlMaxLimit="100">
	<table name="travelrecord" dataNode="dn1,dn2,dn3" rule="auto-sharding-long" ></table>
</schema>
> checkSQLschema : 格式化 SQL , 去掉 mycat 相关
	select * from TESTDB.travelrecord; -> select * from travelrecord;
> sqlMaxLimit : 自动设置limit
    
// table 
<table name="travelrecord" dataNode="dn1,dn2,dn3" rule="auto-sharding-long" ></table>    
> name  : 定义逻辑表的表名
> dataNode :  定义这个逻辑表所属的 dataNode
> rule : 用于指定逻辑表要使用的规则名字，规则名字在 rule.xml 中定义
> ruleRequired : 用于指定表是否绑定分片规则
> primaryKey : 该逻辑表对应真实表的主键
> type : 该属性定义了逻辑表的类型，目前逻辑表只有“全局表”和”普通表”两种类型
> autoIncrement :  指定这个表有使用自增长主键
> subTables : 
> needAddLimit : 指定表是否需要自动的在每个语句后面加上 limit 限制
    
// childTable : childTable 标签用于定义 E-R 分片的子表。通过标签上的属性与父表进行关联
> name : 定义子表的表名
> joinKey : 插入子表的时候会使用这个列的值查找父表存储的数据节点。
> parentKey : 属性指定的值一般为与父表建立关联关系的列名。
> primaryKey :  该逻辑表对应真实表的主键
> needAddLimit : 指定表是否需要自动的在每个语句后面加上 limit 限制 

// datanode : dataNode 标签定义了 MyCat 中的数据节点，也就是我们通常说所的数据分片
<dataNode name="dn1" dataHost="localhost1" database="db1" />
> name : 定义数据节点的名字，这个名字需要是唯一的
> dataHost : 该属性用于定义该分片属于哪个数据库实例的
> database : 该属性用于定义该分片属性哪个具体数据库实例上的具体库，因为这里使用两个纬度来定义分片，
    
// dataHost : 该标签在 mycat 逻辑库中也是作为最底层的标签存在，直接定义了具体的数据库实例、读写分离配置和心跳语句。
<dataHost name="localhost1" maxCon="1000" minCon="10" balance="0" writeType="0" dbType="mysql" dbDriver="native">
	<heartbeat>select user()</heartbeat>
	<!-- can have multi write hosts -->
	<writeHost host="hostM1" url="localhost:3306" user="root" password="123456">
		<!-- can have multi read hosts -->
		<!-- <readHost host="hostS1" url="localhost:3306" user="root" password="123456"/> -->
	</writeHost>
	<!-- <writeHost host="hostM2" url="localhost:3316" user="root" password="123456"/> -->
</dataHost>
> name
> minCon : 指定每个读写实例连接池的最小连接，初始化连接池的大小。
> balance : 负载均衡类型，目前的取值有 3 种：
    - balance="0", 不开启读写分离机制，所有读操作都发送到当前可用的 writeHost 上
    - balance="1"，全部的 readHost 与 stand by writeHost 参与 select 语句的负载均衡，
    - balance="2"，所有读操作都随机的在 writeHost、readhost 上分发
    - balance="3"，所有读请求随机的分发到 wiriterHost 对应的 readhost 执行，writerHost 不负担读压力，
> writeType : 负载均衡类型
    - 
> dbType : 指定后端连接的数据库类型，目前支持二进制的 mysql 协议，还有其他使用 JDBC 连接的数据库
> dbDriver :  指定连接后端数据库使用的 Driver，目前可选的值有 native 和 JDBC。
> switchType : 切换类型
	-1 表示不自动切换
	1 默认值，自动切换
	2 基于 MySQL 主从同步的状态决定是否切换
	3 基于 MySQL galary cluster 的切换机制（适合集群）

// heartbeat : 这个标签内指明用于和后端数据库进行心跳检查的语句。例
    
```

### 2.3 mycat server.xml

```

```



## 三 . 分布式事务的用法

### 3.1  事务简介

```

```

### 3.2 核心文件 rule.xml

```java
// tableRule : 签定义表规则
<tableRule name="rule1">
	<rule>
        <columns>id</columns>
        <algorithm>func1</algorithm>
	</rule>
</tableRule>
- name 属性指定唯一的名字，用于标识不同的表规则。
- columns 内指定要拆分的列名字。  
- algorithm 使用 function 标签中的 name 属性。
    
// function
<function name="hash-int" class="io.mycat.route.function.PartitionByFileMap">
	<property name="mapFile">partition-hash-int.txt</property>
</function>
- name 指定算法的名字。
- class 制定路由算法具体的类名字。
- property 为具体算法需要用到的一些属性。    
```

### 3.3 全局序列号

```java
> 全局序列号包括三种方式 : 
// 方式一 : 本地文件方式
在 sequence_conf.properties 文件中做如下配置：
GLOBAL_SEQ.HISIDS=
GLOBAL_SEQ.MINID=1001
GLOBAL_SEQ.MAXID=1000000000
GLOBAL_SEQ.CURID=1000
// server.xml 中配置    
<system><property name="sequnceHandlerType">0</property></system>
insert into table1(id,name) values(next value for MYCATSEQ_GLOBAL,‘test’);


// 方式二 : 数据库方式
在数据库中建立一张表，存放 sequence 名称(name)，sequence 当前值(current_value)，步长(increment
int 类型每次读取多少个 sequence，假设为 K)等信息；
<system><property name="sequnceHandlerType">1</property></system>

DROP TABLE IF EXISTS MYCAT_SEQUENCE;
CREATE TABLE MYCAT_SEQUENCE (name VARCHAR(50) NOT NULL,current_value INT NOT NULL,increment INT NOT NULL DEFAULT 100, PRIMARY KEY(name)) ENGINE=InnoDB;
INSERT INTO MYCAT_SEQUENCE(name,current_value,increment) VALUES (‘GLOBAL’, 100000,100);

// 创建 function 
http://www.mycat.org.cn/document/mycat-definitive-guide.pdf : 106

// 方式三 :  本地时间戳方式
<property name="sequnceHandlerType">2</property>
sequence_time_conf.properties
	WORKID=0-31 任意整数
	DATAACENTERID=0-31 任意整数
    
// 方式四 : 分布式 ZK ID 生成器
// 方式五 : Zk 递增方式
// 其他 :     
    
```

## 四 . Mycat 分片



## 五 . MyCat 实操记录

### 5.1 操作记录

```
// 安装参考 : 2.1 简易安装手册

```

