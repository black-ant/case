package com.gang.mysql.demo.service;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @Classname SearchService
 * @Description TODO
 * @Date 2021/7/28
 * @Created by zengzg
 */
@Component
public class SearchService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        template();

        runProcess();
    }

    public void template() {
        logger.info("------> [Step 1 : 开启 JDBC Template 请求流程] <-------");
        //通过api操作执行sql
        jdbcTemplate.update("insert into user(username,user_org) values(?,?);", "gang", 1);
    }

    /**
     * CREATE DEFINER=`root`@`localhost` PROCEDURE `test_process`( )
     * BEGIN
     * DECLARE i INT DEFAULT 0;
     * WHILE i < 10 DO
     * INSERT INTO user ( username ) VALUES( UUID( ) );
     * SET i = i + 1;
     * END WHILE;
     * END
     */
    public void runProcess() {
        logger.info("------> [ 运行存储过程 test_process] <-------");
        //通过api操作执行sql
        jdbcTemplate.execute("call test_process()");
    }
}
