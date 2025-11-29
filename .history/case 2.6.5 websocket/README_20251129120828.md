# WebSocket Demo

## 项目说明

Spring Boot WebSocket 示例，演示实时双向通信。

## 技术栈

- Java 11
- Spring Boot 2.7.18
- WebSocket (JSR 356)
- Fastjson2

## WebSocket 简介

WebSocket 是一种在单个 TCP 连接上进行全双工通信的协议：

| 特性 | HTTP | WebSocket |
|------|------|-----------|
| 连接 | 短连接 | 长连接 |
| 方向 | 客户端发起 | 双向 |
| 开销 | 每次请求头 | 初次握手后极低 |
| 实时性 | 轮询/长轮询 | 原生支持 |

## 项目结构

```
src/main/java/com/gang/study/websocket/
├── WebsocketApplication.java     # 启动类
├── config/
│   └── WebSocketConfig.java      # WebSocket 配置
├── controller/
│   └── CheckCenterController.java # 控制器
├── module/
│   ├── MsgError.java             # 错误码枚举
│   └── PushMessage.java          # 消息对象
└── service/
    └── WebSocketService.java     # WebSocket 服务端点

src/main/resources/
├── static/
│   ├── index.html                # 测试页面
│   └── socket.js                 # WebSocket 客户端 JS
└── application.properties
```

## 快速开始

### 启动应用

```bash
mvn spring-boot:run
```

### 访问测试页面

http://localhost:8080/index.html

### WebSocket 连接地址

```
ws://localhost:8080/push/msg
```

## 消息协议

### 消息格式

```json
{
    "gid": "group-001",
    "event": "REG|UNREG|TRANS|SYS",
    "content": "消息内容",
    "resultCode": 0
}
```

### 事件类型

| 事件 | 说明 |
|------|------|
| REG | 注册到分组 |
| UNREG | 从分组注销 |
| TRANS | 转发消息到分组 |
| SYS | 获取系统信息 |

## 客户端示例

### JavaScript

```javascript
// 建立连接
const socket = new WebSocket('ws://localhost:8080/push/msg');

// 连接成功
socket.onopen = function() {
    console.log('Connected');
    // 注册到分组
    socket.send(JSON.stringify({
        gid: 'group-001',
        event: 'REG'
    }));
};

// 收到消息
socket.onmessage = function(event) {
    console.log('Received:', event.data);
};

// 发送消息
function sendMessage(content) {
    socket.send(JSON.stringify({
        gid: 'group-001',
        event: 'TRANS',
        content: content
    }));
}

// 关闭连接
socket.onclose = function() {
    console.log('Disconnected');
};
```

## 核心注解

| 注解 | 说明 |
|------|------|
| @ServerEndpoint | 定义 WebSocket 端点路径 |
| @OnOpen | 连接建立时调用 |
| @OnMessage | 收到消息时调用 |
| @OnClose | 连接关闭时调用 |
| @OnError | 发生错误时调用 |

## 运行测试

```bash
mvn test
```

## 注意事项

- 使用外部容器部署时不需要 ServerEndpointExporter
- Session 不是线程安全的，并发发送需要同步
- 长时间无消息可能被代理服务器关闭，需要心跳机制
- 生产环境建议使用 STOMP 协议

