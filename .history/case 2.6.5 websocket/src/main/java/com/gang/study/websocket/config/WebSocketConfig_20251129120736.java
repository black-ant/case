package com.gang.study.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket 配置类
 * <p>
 * 配置 WebSocket 支持，注册 ServerEndpointExporter。
 * </p>
 * 
 * <h3>工作原理：</h3>
 * <p>
 * ServerEndpointExporter 会扫描带有 @ServerEndpoint 注解的 Bean，
 * 并将它们注册为 WebSocket 端点。
 * </p>
 * 
 * <h3>注意事项：</h3>
 * <ul>
 *     <li>使用外部容器（如 Tomcat）部署时不需要此 Bean</li>
 *     <li>使用 Spring Boot 内嵌容器时必须配置</li>
 * </ul>
 *
 * @author zengzg
 * @since 2019/12/2
 */
@Configuration
public class WebSocketConfig {

    /**
     * 注册 ServerEndpointExporter
     * <p>
     * 该 Bean 会自动注册使用 @ServerEndpoint 注解的 WebSocket 端点。
     * </p>
     *
     * @return ServerEndpointExporter 实例
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
