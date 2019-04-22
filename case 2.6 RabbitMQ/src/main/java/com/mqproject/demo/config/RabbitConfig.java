package com.mqproject.demo.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String DEFAULT_BOOK_QUEUE = "dev.book.register.default.queue";
    public static final String MANUAL_BOOK_QUEUE = "dev.book.register.manual.queue";

//    @Bean
//    public Queue defaultBookQueue() {
//        // 第一个是 QUEUE 的名字,第二个是消息是否需要持久化处理
//        return new Queue(DEFAULT_BOOK_QUEUE, true);
//    }

    @Bean
    public Queue manualBookQueue() {
        // 第一个是 QUEUE 的名字,第二个是消息是否需要持久化处理
        return new Queue(MANUAL_BOOK_QUEUE, true);
    }

    public static final String QUEUE_A = "QUEUE_A";
    public static final String EXCHANGE_A = "my-mq-exchange_A";
    public static final String ROUTINGKEY_A = "my-mq-routingKey_A";

    /**
     *
     * @return
     */
    @Bean
    public Queue queueA() {
        return new Queue(QUEUE_A, true); //队列持久
    }
    /**
     * 创建 exchange 对象
     * @return
     */
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE_A);
    }

    /**
     * 将queue 指定的queue 进行板顶
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queueA()).to(defaultExchange()).with(RabbitConfig.ROUTINGKEY_A);
    }
}
