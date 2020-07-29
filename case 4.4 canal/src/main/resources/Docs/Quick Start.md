# Canal 文档

## 原理

```
canal 模拟 MySQL slave 的交互协议，伪装自己为 MySQL slave ，向 MySQL master 发送 dump 协议
MySQL master 收到 dump 请求，开始推送 binary log 给 slave (即 canal )
canal 解析 binary log 对象(原始为 byte 流)

Canal是基于MySQL二进制日志的高性能数据同步系统 ,提供可靠的低延迟增量数据管道

Mysql 主从复制 :
	|- 主服务器将更改记录到binlog中（这些记录称为binlog事件，可以通过来查看show binary events）
	|- 从服务器将主服务器的二进制日志事件复制到其中继日志。
	|- 中继日志中的从服务器重做事件将随后更新其旧数据。

原理:
1 Canal模拟MySQL从站的交互协议，伪装成MySQL从站，然后将转储协议发送到MySQL主服务器。
2 MySQL Master接收到转储请求，并开始将二进制日志推送到slave（即运河）。
3 运河将二进制日志对象解析为其自己的数据类型（最初为字节流）
```



## 使用方式 :

```
> MySQL 配置
[mysqld]
log-bin=mysql-bin # 开启 binlog
binlog-format=ROW # 选择 ROW 模式
server_id=1 # 配置 MySQL replaction 需要定义，不要和 canal 的 slaveId 重复

> 授权
CREATE USER canal IDENTIFIED BY 'canal';  
GRANT SELECT, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'canal'@'%';
-- GRANT ALL PRIVILEGES ON *.* TO 'canal'@'%' ;
FLUSH PRIVILEGES;

> 启动 Canal
wget https://github.com/alibaba/canal/releases/download/canal-1.0.17/canal.deployer-1.0.17.tar.gz

> 解压缩
mkdir /tmp/canal
tar zxvf canal.deployer-$version.tar.gz  -C /tmp/canal

> 修改配置
vi conf/example/instance.properties

## mysql serverId
canal.instance.mysql.slaveId = 1234
#position info，需要改成自己的数据库信息
canal.instance.master.address = 127.0.0.1:3306 
canal.instance.master.journal.name = 
canal.instance.master.position = 
canal.instance.master.timestamp = 
#canal.instance.standby.address = 
#canal.instance.standby.journal.name =
#canal.instance.standby.position = 
#canal.instance.standby.timestamp = 
#username/password，需要改成自己的数据库信息
canal.instance.dbUsername = canal  
canal.instance.dbPassword = canal
canal.instance.defaultDatabaseName =
canal.instance.connectionCharset = UTF-8
#table regex
canal.instance.filter.regex = .\*\\\\..\*

> 启动
sh bin/startup.sh

> 查看server 日志
vi logs/canal/canal.log</pre>

> 查看 instance 日志
vi logs/example/example.log

> 关闭
sh bin/stop.sh
```

### docker 拉取

```
docker pull canal/canal-server:latest

# 本地编译
git clone git@github.com:alibaba/canal.git
cd canal/docker && sh build.sh

# 下载脚本
wget https://raw.githubusercontent.com/alibaba/canal/master/docker/run.sh 

# 构建一个destination name为test的队列
sh run.sh -e canal.auto.scan=false \
		  -e canal.destinations=test \
		  -e canal.instance.master.address=127.0.0.1:3306  \
		  -e canal.instance.dbUsername=canal  \
		  -e canal.instance.dbPassword=canal  \
		  -e canal.instance.connectionCharset=UTF-8 \
		  -e canal.instance.tsdb.enable=true \
		  -e canal.instance.gtidon=false  \
```

