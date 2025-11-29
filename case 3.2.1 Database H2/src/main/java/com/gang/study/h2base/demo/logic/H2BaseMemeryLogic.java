package com.gang.study.h2base.demo.logic;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * H2 数据库内存模式示例
 * <p>
 * 演示 H2 内存数据库的基本操作：
 * <ul>
 *     <li>启动/停止 H2 服务器</li>
 *     <li>建表、插入、查询操作</li>
 *     <li>内存模式数据管理</li>
 * </ul>
 * </p>
 * 
 * <h3>内存模式特点：</h3>
 * <ul>
 *     <li>无需安装客户端</li>
 *     <li>数据存储在内存中</li>
 *     <li>应用关闭后数据丢失</li>
 *     <li>适合单元测试和临时数据存储</li>
 * </ul>
 * 
 * <h3>注意事项：</h3>
 * <p>
 * 内存模式下，只有在同一连接中才能访问创建的表。
 * 如需多连接共享，请使用 jdbc:h2:mem:dbname;DB_CLOSE_DELAY=-1
 * </p>
 *
 * @author zengzg
 * @since 2020/4/5
 */
@Component
public class H2BaseMemeryLogic {

    private static final Logger logger = LoggerFactory.getLogger(H2BaseMemeryLogic.class);

    private Server server;
    
    /** H2 服务器端口 */
    private final String port = "8082";
    
    /** 内存模式连接URL（DB_CLOSE_DELAY=-1 保持数据库不关闭） */
    private static final String SOURCE_URL = "jdbc:h2:mem:h2db;DB_CLOSE_DELAY=-1";
    
    /** 数据库用户名 */
    private final String user = "sa";
    
    /** 数据库密码 */
    private final String password = "";

    /**
     * 演示完整的数据库操作流程
     * 
     * @throws Exception 数据库操作异常
     */
    public void runDemo() throws Exception {
        // Step 1: 启动 H2 服务器
        startServer();

        // Step 2: 执行数据操作
        insertOperation();

        // Step 3: 校验数据
        checkH2Logic();

        // Step 4: 关闭服务器
        stopServer();
    }

    /**
     * 启动 H2 TCP 服务器
     * <p>
     * 启动后可通过 JDBC 连接：jdbc:h2:tcp://localhost:8082/mem:h2db
     * </p>
     */
    public void startServer() {
        try {
            logger.info("Starting H2 server on port {}...", port);
            server = Server.createTcpServer("-tcpPort", port).start();
            logger.info("H2 server started successfully");
        } catch (SQLException e) {
            logger.error("Failed to start H2 server", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭 H2 服务器
     */
    public void stopServer() {
        if (server != null) {
            logger.info("Stopping H2 server...");
            server.stop();
            logger.info("H2 server stopped");
        }
    }

    /**
     * 演示数据插入操作
     * <p>
     * 创建内存表并插入测试数据
     * </p>
     */
    public void insertOperation() {
        logger.info("Executing insert operation...");
        
        try (Connection conn = DriverManager.getConnection(SOURCE_URL, user, password);
             Statement stmt = conn.createStatement()) {
            
            // 创建内存表
            stmt.execute("CREATE TABLE IF NOT EXISTS TEST(ID INT PRIMARY KEY, NAME VARCHAR(50))");
            
            // 插入数据
            stmt.execute("INSERT INTO TEST VALUES(1, 'Hello World')");
            stmt.execute("INSERT INTO TEST VALUES(2, 'H2 Database')");
            
            logger.info("Data inserted successfully");
            
            // 查询验证
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM TEST")) {
                while (rs.next()) {
                    logger.info("Row: id={}, name={}", rs.getInt("ID"), rs.getString("NAME"));
                }
            }
            
        } catch (SQLException e) {
            logger.error("Insert operation failed", e);
        }
    }

    /**
     * 验证数据库数据
     * <p>
     * 使用新连接查询数据，验证内存模式下的数据持久性
     * </p>
     */
    public void checkH2Logic() {
        logger.info("Checking H2 data...");
        
        try (Connection conn = DriverManager.getConnection(SOURCE_URL, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS cnt FROM TEST")) {
            
            if (rs.next()) {
                logger.info("Total rows in TEST table: {}", rs.getInt("cnt"));
            }
            
        } catch (SQLException e) {
            logger.error("Check operation failed", e);
        }
    }
    
    /**
     * 获取服务器状态
     * 
     * @return 服务器是否运行中
     */
    public boolean isRunning() {
        return server != null && server.isRunning(true);
    }
}
