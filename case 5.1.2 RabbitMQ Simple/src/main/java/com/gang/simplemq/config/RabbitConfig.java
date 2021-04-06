package com.gang.simplemq.config;

import com.gang.simplemq.callbakc.MySelfConfirmCallback;
import com.gang.simplemq.callbakc.SelfReturnsCallback;
import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

/**
 * @Classname RabbitConfig
 * @Description TODO
 * @Date 2021/3/29
 * @Created by zengzg
 */
public class RabbitConfig {


    public RabbitTemplate getRabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate();
        template.setMessageConverter(new Jackson2JsonMessageConverter());

        // 事务
        template.setChannelTransacted(true);

        // 单独连接
        template.setUsePublisherConnection(true);

        // 设置回调
        template.setConfirmCallback(new MySelfConfirmCallback());

        // 设置返回
        template.setReturnsCallback(new SelfReturnsCallback());

        return template;
    }

    public RabbitTemplate getRabbitTemplate1() {
        RabbitTemplate template = new RabbitTemplate();
        template.setAfterReceivePostProcessors();
        template.setBeforePublishPostProcessors();
        return template;
    }


    public CachingConnectionFactory ccf() {
        CachingConnectionFactory ccf = new CachingConnectionFactory();
        ccf.setAddresses("host1:5672,host2:5672,host3:5672");
        ccf.setAddressShuffleMode(AbstractConnectionFactory.AddressShuffleMode.RANDOM);
        return ccf;
    }
}
