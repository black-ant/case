package com.gang.study.h2base.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @Classname H2BaseClientLogic
 * @Description TODO
 * @Date 2020/4/5 20:07
 * @Created by zengzg
 */
@Component
public class H2BaseClientLogic implements ApplicationRunner {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String JDBC_URL = "jdbc:h2:D:\\database\\h2\\data";

    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    private static final String DRIVER_CLASS = "org.h2.Driver";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in run <-------");
        start();
    }

    public void start() throws Exception {
        Class.forName(DRIVER_CLASS);
        Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        Statement statement = conn.createStatement();
        statement.execute("DROP TABLE IF EXISTS USER_INF");
        statement.execute("CREATE TABLE USER_INF(id INTEGER PRIMARY KEY ,name VARCHAR(100), sex VARCHAR(2))");

        statement.executeUpdate("INSERT INTO USER_INF VALUES(1, 'tom', '男') ");
        statement.executeUpdate("INSERT INTO USER_INF VALUES(2, 'jack', '女') ");
        statement.executeUpdate("INSERT INTO USER_INF VALUES(3, 'marry', '男') ");
        statement.executeUpdate("INSERT INTO USER_INF VALUES(4, 'lucy', '男') ");

        ResultSet resultSet = statement.executeQuery("select * from USER_INF");

        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("name") +
                    ", " + resultSet.getString("sex"));
        }

        statement.close();
        conn.close();
    }
}
