package com.gang.study.h2base.demo.logic;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Classname H2BaseLogic
 * @Description h2 数据库内存模式使用 : 内存模式的 H2 数据库无需下载客户端 , 可以直接使用
 * @Date 2020/4/5 19:26
 * @Created by zengzg
 */
@Component
public class H2BaseMemeryLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    private String port = "8082";
    private static String sourceURL1 = "jdbc:h2:mem:h2db";
    private String user = "shorturl";
    private String password = "123456";

    public void run(ApplicationArguments args) throws Exception {

        // Step 1 : 基础准备工作
        startServer();

        insertOperation();

        // Step 2 : 校验工作
        checkH2Logic();

        // Step 3 : 关闭 Server
        stopServer();
    }

    /**
     * 启动 H2 数据库
     */
    public void startServer() {
        try {
            System.out.println("正在启动h2...");
            server = Server.createTcpServer(
                    new String[]{"-tcpPort", port}).start();
        } catch (SQLException e) {
            System.out.println("启动h2出错：" + e.toString());
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭 H2 数据库
     */
    public void stopServer() {
        if (server != null) {
            System.out.println("正在关闭h2...");
            server.stop();
            System.out.println("关闭成功.");
        }
    }

    /**
     * 操作 ,添加数据
     */
    public void insertOperation() {

        logger.info("------> insert a msg info <-------");
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(sourceURL1, user, password);
            Statement stat = conn.createStatement();
            // create a memory table and insert data
            stat.execute("CREATE MEMORY Table TEST(NAME VARCHAR)");
            stat.execute("INSERT INTO TEST VALUES('Hello World')");
            logger.info("------> insert over , select in one connect <-------");
            ResultSet result = stat.executeQuery("select NAME from TEST ");
            int i = 1;
            while (result.next()) {
                logger.info("------> select response : index [{}] : {} <-------", i, result.getString("name"));
            }
            result.close();

            stat.close();
            conn.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info("------> insert info end <-------");
    }


    /**
     * 校验数据库情况
     */
    public void checkH2Logic() {
        logger.info("------> checkH2Logic start <-------");
        try {
            Class.forName("org.h2.Driver");
            Connection connTest = DriverManager.getConnection(sourceURL1, user, password);
            Statement stat = connTest.createStatement();
            // use data
            ResultSet result = stat.executeQuery("select NAME from TEST ");
            int i = 1;
            while (result.next()) {
                System.out.println(i++ + ":" + result.getString("NAME"));
            }
            result.close();
            connTest.close();
            logger.info("------> this is result :{}<-------", result.toString());
        } catch (Exception e) {
            logger.error("E----> when you close connect onece : error :{} -- content :{}", e.getClass(),
                    e.getMessage());
        }
    }

}

