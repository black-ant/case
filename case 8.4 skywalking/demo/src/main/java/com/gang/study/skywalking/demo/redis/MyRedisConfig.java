package com.gang.study.skywalking.demo.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Classname MyRedisConfig
 * @Description TODO
 * @Date 2020/11/21 14:10
 * @Created by zengzg
 */
@Configuration
public class MyRedisConfig {

    @Bean
    public RedisTemplate getRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate redisTemplate = new RedisTemplate();

        redisTemplate.setConnectionFactory(factory);

        final RedisSerializer<String> string = new StringRedisSerializer();
        final JdkSerializationRedisSerializer jdk = new JdkSerializationRedisSerializer();
        redisTemplate.setKeySerializer(string);
        redisTemplate.setValueSerializer(jdk);
        redisTemplate.setHashKeySerializer(string);
        redisTemplate.setHashValueSerializer(jdk);

        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

}
