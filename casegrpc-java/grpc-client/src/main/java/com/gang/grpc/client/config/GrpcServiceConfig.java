package com.gang.grpc.client.config;

import com.example.grpc.HelloServiceGrpc;
import com.example.grpc.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname GrpcServiceConfig
 * @Description TODO
 * @Date 2023/4/9
 * @Created by zengzg
 */
@Configuration
public class GrpcServiceConfig {

    @Bean
    public ManagedChannel getChannel() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9091)
                .usePlaintext()
                .build();
        return channel;
    }

    @Bean
    public HelloServiceGrpc.HelloServiceBlockingStub getStub1(ManagedChannel channel) {
        return HelloServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public UserServiceGrpc.UserServiceBlockingStub getStub2(ManagedChannel channel) {
        return UserServiceGrpc.newBlockingStub(channel);
    }
}
