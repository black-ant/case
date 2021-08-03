# 操作手册



## 前言

这个案例中提供了2种 Sentinel 的使用方式 , 一种是本地实现 , 一种是远程实现



## 使用方式



### 本地实现 Sentinel

```java
// Maven 依赖
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-core</artifactId>
    <version>1.7.0</version>
</dependency>
    
// 
    
```



### 远程Sentinel 

```java
// Maven 依赖
<dependency>
	<groupId>com.alibaba.csp</groupId>
	<artifactId>sentinel-transport-simple-http</artifactId>
	<version>1.7.1</version>
</dependency>

// 启动命令
VM 后添加启动命令 : -Dcsp.sentinel.dashboard.server=127.0.0.1:8060



```

