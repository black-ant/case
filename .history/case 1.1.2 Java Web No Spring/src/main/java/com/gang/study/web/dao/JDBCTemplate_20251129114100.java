package com.gang.study.web.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC 数据库操作模板类
 * <p>
 * 演示原生 JDBC 数据库操作的基本流程：
 * <ol>
 *     <li>加载数据库驱动</li>
 *     <li>获取数据库连接</li>
 *     <li>创建 Statement</li>
 *     <li>执行 SQL</li>
 *     <li>处理结果集</li>
 *     <li>关闭资源</li>
 * </ol>
 * </p>
 * 
 * <h3>注意事项：</h3>
 * <ul>
 *     <li>需要本地安装 MySQL 数据库</li>
 *     <li>需要创建名为 'jdbctest' 的数据库</li>
 *     <li>生产环境应使用连接池（如 HikariCP、Druid）</li>
 *     <li>密码应从配置文件读取，不应硬编码</li>
 * </ul>
 *
 * @author zengzg
 * @since 2020/7/9
 */
public class JDBCTemplate {

    private static final Logger logger = LoggerFactory.getLogger(JDBCTemplate.class);

    /** 数据库驱动类名 */
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    /** 数据库连接 URL */
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/jdbctest?useSSL=false&serverTimezone=UTC";
    
    /** 数据库用户名 */
    private static final String USERNAME = "root";
    
    /** 数据库密码 */
    private static final String PASSWORD = "mysqladmin";

    /**
     * 执行查询 SQL
     * <p>
     * 注意：此方法存在资源泄漏问题，ResultSet 在 finally 块中被关闭后返回，
     * 调用者将无法使用。实际使用时应改为回调模式或返回处理后的结果。
     * </p>
     *
     * @param sql 要执行的 SQL 语句
     * @return ResultSet 结果集（注意：可能为空或已关闭）
     * @deprecated 此方法有资源管理问题，仅作演示用途
     */
    @Deprecated
    public ResultSet doExecute(String sql) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            // 1. 加载驱动
            Class.forName(DRIVER);
            logger.info("Database driver loaded: {}", DRIVER);
            
            // 2. 获取连接
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            logger.info("Database connection established");
            
            // 3. 创建 Statement
            stmt = conn.createStatement();
            
            // 4. 执行查询
            rs = stmt.executeQuery(sql);
            logger.info("SQL executed: {}", sql);
            
        } catch (ClassNotFoundException e) {
            logger.error("Database driver not found", e);
        } catch (SQLException e) {
            logger.error("SQL execution error", e);
        } finally {
            // 5. 关闭资源（注意：这里关闭了 rs，返回值将不可用）
            closeQuietly(rs);
            closeQuietly(stmt);
            closeQuietly(conn);
        }
        
        return rs;
    }

    /**
     * 静默关闭 ResultSet
     *
     * @param rs 要关闭的 ResultSet
     */
    private void closeQuietly(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.warn("Failed to close ResultSet", e);
            }
        }
    }

    /**
     * 静默关闭 Statement
     *
     * @param stmt 要关闭的 Statement
     */
    private void closeQuietly(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.warn("Failed to close Statement", e);
            }
        }
    }

    /**
     * 静默关闭 Connection
     *
     * @param conn 要关闭的 Connection
     */
    private void closeQuietly(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.warn("Failed to close Connection", e);
            }
        }
    }
}
