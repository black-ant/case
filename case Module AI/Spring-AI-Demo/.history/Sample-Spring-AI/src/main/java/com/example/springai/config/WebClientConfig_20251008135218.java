package com.example.springai.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * WebClient 配置 - 支持代理、自定义 DNS 和超时设置
 * 
 * 解决 DNS 解析超时问题：
 * 1. 使用 JVM 默认的 DNS 解析器（绕过 Netty 的异步 DNS）
 * 2. 支持配置 HTTP 代理
 * 3. 增加超时时间
 */
@Configuration
public class WebClientConfig {

    @Value("${http.proxy.enabled:false}")
    private boolean proxyEnabled;

    @Value("${http.proxy.host:127.0.0.1}")
    private String proxyHost;

    @Value("${http.proxy.port:7890}")
    private int proxyPort;

    @Value("${http.timeout.connect:30000}")
    private int connectTimeout;

    @Value("${http.timeout.read:60000}")
    private int readTimeout;

    @Value("${http.dns.use-jvm-resolver:true}")
    private boolean useJvmDnsResolver;

    @Bean
    public WebClient.Builder webClientBuilder() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
                .responseTimeout(Duration.ofMillis(readTimeout))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS)));

        // 使用 JVM 默认 DNS 解析器，避免 Netty DNS 超时问题
        if (useJvmDnsResolver) {
            httpClient = httpClient.resolver(DefaultAddressResolverGroup.INSTANCE);
        }

        // 如果启用代理，配置代理
        if (proxyEnabled) {
            httpClient = httpClient.proxy(proxy ->
                    proxy.type(ProxyProvider.Proxy.HTTP)
                            .host(proxyHost)
                            .port(proxyPort));
        }

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient));
    }
}
