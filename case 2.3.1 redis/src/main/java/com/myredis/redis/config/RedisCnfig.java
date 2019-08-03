package com.myredis.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/27 19:17
 * @Version 1.0
 **/
@Configuration
public class RedisCnfig {

    /**
     * retemplate相关配置
     * RedisConnection 接收发送和返回二进制值的底层方法
     * template : 复杂对其序列化 和 连接管路
     * <p>
     * 序列化程序包,其中提供了常见的实现 -> org.springframework.data.redis.serializer
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(factory);
        //此配置用于指定是否使用默认实现 ，对原始字节数组使用 RedisTemplate ，默认为true
//        template.setEnableDefaultSerializer(false);


        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值,此时转换为JSON格式（默认使用JDK的序列化方式）
        //常见的还有 GenericJackson2JsonRedisSerializer ，JdkSerializationRedisSerializer ，GenericToStringSerializer ，OxmSerializer
        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSeial.setObjectMapper(om);

        // 值采用json序列化
        template.setValueSerializer(jacksonSeial);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());

        // 设置hash key 和value序列化模式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jacksonSeial);
        template.afterPropertiesSet();

        return template;
    }

    /**
     *
     * 以下是对特定的数据和特定的值进行操作
     * redisTemplate 提供以下的特定操作
     * GeoOperations : 地理空间操作 -> 指定的地理空间项目（纬度，经度，名称）
     * HashOperations : Hash 散列的操作
     * HyperLogLogOperations -> 受多个元素作为输入，并给出输入元素的基数估算值 : 对 HyperLogLogOperations 操作设定
     * ListOperations : List 集合
     * SetOperations : Set 集合
     * ValueOperations : 键值对 ，官方定义为string (or value) ，所以通常是字符串
     * ZSetOperations :sorted set 操作
     *
     * Key Bound Operations -> 以及键绑定的一些操作
     *
     * BoundGeoOperations : 绑定地理空间
     * BoundHashOperations : hash , 以下同理
     * BoundKeyOperations
     * BoundListOperations
     * BoundSetOperations
     * BoundValueOperations
     * BoundZSetOperations
     */


    /**
     * 对hash类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    /**
     * 对redis字符串类型数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    /**
     * 对链表类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    /**
     * 对无序集合类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    /**
     * 对有序集合类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

}
