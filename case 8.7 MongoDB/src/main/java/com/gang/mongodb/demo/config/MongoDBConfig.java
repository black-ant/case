package com.gang.mongodb.demo.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

/**
 * @Classname MongoDBConfig
 * @Description TODO
 * @Date 2023/8/26
 * @Created by zengzg
 */
@Configuration
public class MongoDBConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "testDB";
    }

    @Override
    public MongoClient mongoClient() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://root:r123oot@182.92.129.97:7017/?authSource=testDB"))
                .build();

        return MongoClients.create(settings);
    }

}
