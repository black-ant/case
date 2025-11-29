# Netty Module

## 项目说明

Netty 高性能网络框架示例集合，演示 NIO 网络编程。

## 技术栈

- Java 11
- Netty 4.x
- Spring Boot 2.7.x

## 子项目列表

| 项目 | 说明 |
|------|------|
| case Netty Sample | Netty 基础示例 |
| case Netty Template | Netty 项目模板 |
| case Netty SpringBoot Demo | Spring Boot 集成 |
| case Netty Multiple Port | 多端口监听 |

## Netty 核心概念

| 概念 | 说明 |
|------|------|
| Channel | 网络连接通道 |
| EventLoop | 事件循环（处理 I/O） |
| ChannelHandler | 事件处理器 |
| ChannelPipeline | 处理器链 |
| ByteBuf | 字节缓冲区 |
| Bootstrap | 启动引导类 |

## Netty vs Java NIO

| 特性 | Netty | Java NIO |
|------|-------|----------|
| 易用性 | 高 | 低 |
| 性能 | 极高 | 高 |
| 零拷贝 | 支持 | 部分 |
| 协议支持 | 丰富 | 无 |
| 社区 | 活跃 | 无 |

## 项目结构

```
├── case Netty Sample/          # 基础示例
│   ├── com-gang-netty-client/  # 客户端
│   ├── com-gang-netty-server/  # 服务端
│   └── com-gang-netty-common/  # 公共模块
├── case Netty Template/        # 项目模板
│   ├── client/
│   └── server/
└── case Netty SpringBoot Demo/ # Spring Boot 集成
```

## 代码示例

### 服务端

```java
EventLoopGroup bossGroup = new NioEventLoopGroup(1);
EventLoopGroup workerGroup = new NioEventLoopGroup();

ServerBootstrap bootstrap = new ServerBootstrap();
bootstrap.group(bossGroup, workerGroup)
    .channel(NioServerSocketChannel.class)
    .childHandler(new ChannelInitializer<SocketChannel>() {
        @Override
        protected void initChannel(SocketChannel ch) {
            ch.pipeline()
                .addLast(new StringDecoder())
                .addLast(new StringEncoder())
                .addLast(new MyServerHandler());
        }
    });

ChannelFuture future = bootstrap.bind(8080).sync();
```

### 客户端

```java
EventLoopGroup group = new NioEventLoopGroup();

Bootstrap bootstrap = new Bootstrap();
bootstrap.group(group)
    .channel(NioSocketChannel.class)
    .handler(new ChannelInitializer<SocketChannel>() {
        @Override
        protected void initChannel(SocketChannel ch) {
            ch.pipeline()
                .addLast(new StringDecoder())
                .addLast(new StringEncoder())
                .addLast(new MyClientHandler());
        }
    });

ChannelFuture future = bootstrap.connect("localhost", 8080).sync();
future.channel().writeAndFlush("Hello Netty");
```

## 学习资源

- [Netty 官方文档](https://netty.io/wiki/index.html)
- [Netty 权威指南](https://book.douban.com/subject/26373138/)

