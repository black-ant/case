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
        return "ANT_TEST";
    }

    @Override
    public MongoClient mongoClient() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://root:zzg!%4019950824@dds-uf60001a5575c6a41526-pub.mongodb.rds.aliyuncs.com:3717,dds-uf60001a5575c6a42136-pub.mongodb.rds.aliyuncs" +
                        ".com:3717/admin?replicaSet=mgset-70763253"))
                .build();

        return MongoClients.create(settings);
    }

}
