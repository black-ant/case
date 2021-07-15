package com.search.elastic.config;

import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.TransportClientFactoryBean;

import java.util.Properties;

/**
 * @Classname ElasticConfig
 * @Description TODO
 * @Date 2021/7/15
 * @Created by zengzg
 */
@Configuration
public class ElasticConfig {


    @Autowired
    private ElasticProperties elasticProperties;

    @Autowired
    private ElasticsearchProperties defaultElasticProperties;

    /**
     * 为了了解涉及对象 , 此处选择手动创建
     *
     * @return
     * @throws Exception
     */
    @Bean
    public TransportClient elasticsearchClient() throws Exception {
        TransportClientFactoryBean factory = new TransportClientFactoryBean();
        factory.setClusterNodes(elasticProperties.getRemoteUrl());
        factory.setProperties(createProperties());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    private Properties createProperties() {
        Properties properties = new Properties();
        properties.put("cluster.name", defaultElasticProperties.getClusterName());
        properties.putAll(defaultElasticProperties.getProperties());
        return properties;
    }
}
