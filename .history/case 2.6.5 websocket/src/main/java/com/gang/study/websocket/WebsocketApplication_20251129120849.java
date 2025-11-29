package com.gang.study.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * WebSocket 示例应用
 * <p>
 * 本项目演示 Spring Boot 中的 WebSocket 实时通信：
 * <ul>
 *     <li>WebSocket 服务端配置</li>
 *     <li>消息推送</li>
 *     <li>会话管理</li>
 *     <li>分组消息</li>
 * </ul>
 * </p>
 * 
 * <h3>WebSocket 特点：</h3>
 * <ul>
 *     <li>全双工通信 - 服务器可主动推送消息</li>
 *     <li>持久连接 - 建立后保持长连接</li>
 *     <li>低延迟 - 无需轮询</li>
 * </ul>
 * 
 * <h3>使用场景：</h3>
 * <ul>
 *     <li>实时聊天</li>
 *     <li>实时通知</li>
 *     <li>股票行情</li>
 *     <li>在线游戏</li>
 * </ul>
 *
 * @author zengzg
 * @since 1.0
 */
@SpringBootApplication
public class WebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketApplication.class, args);
    }
}
