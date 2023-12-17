package com.example.comggangelastic;

import lombok.Data;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

import static cn.hutool.core.util.DesensitizedUtil.DesensitizedType.PASSWORD;
import static org.elasticsearch.client.security.GetUsersResponse.USERNAME;

/**
 * @Classname ElasticConfig
 * @Description TODO
 * @Date 2023/2/2
 * @Created by zengzg
 */
@ConfigurationProperties(prefix = "elasticsearch")
@Configuration
@Data
public class ElasticConfig extends AbstractElasticsearchConfiguration {
    private String host;
    private Integer port;
    private String username;
    private String password;

    //重写父类方法
    @Override
    public RestHighLevelClient elasticsearchClient() {

        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, "https"));

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        builder.setHttpClientConfigCallback(f -> f.setDefaultCredentialsProvider(credentialsProvider));

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(builder);
        return restHighLevelClient;
    }
}
