package com.mybatistest.demo.plugins;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Classname PluginsConfig
 * @Description TODO
 * @Date 2021/5/23
 * @Created by zengzg
 */
@Configuration
public class PluginsConfig {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @PostConstruct
    public void addPageInterceptor() {
        DefaultInterceptor interceptor = new DefaultInterceptor();
        // 此处往 SqlSessionFactory 中添加
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
        }
    }
}
