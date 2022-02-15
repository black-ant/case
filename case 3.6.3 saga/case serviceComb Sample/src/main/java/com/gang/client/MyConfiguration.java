package com.gang.client;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

import javax.sql.DataSource;

/**
 * @author <a href="http://youngitman.tech">青年IT男</a>
 * @version v1.0.0
 * @className Config
 * @description
 * @date 2019-12-16 16:46
 * @JunitTest: {@link  }
 **/
@Configuration
@EnableJdbcRepositories
public class MyConfiguration {

    @Bean
    DataSource dataSource() {
        DruidDataSource source = new DruidDataSource();
        source.setUrl("jdbc:mysql://localhost:3306/client");
        source.setUsername("root");
        source.setPassword("123456");


        return source;
    }

}
