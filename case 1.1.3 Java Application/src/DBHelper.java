import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库帮助类
 * <p>
 * 演示 Java 中使用 JDBC 连接数据库的完整流程：
 * <ol>
 *     <li>加载数据库驱动</li>
 *     <li>建立数据库连接</li>
 *     <li>创建 SQL 执行对象</li>
 *     <li>执行 SQL 语句</li>
 *     <li>处理执行结果</li>
 *     <li>释放资源</li>
 * </ol>
 * </p>
 * 
 * <h3>Statement vs PreparedStatement：</h3>
 * <ul>
 *     <li>PreparedStatement 继承自 Statement，都是接口</li>
 *     <li>PreparedStatement 可以使用占位符，支持预编译</li>
 *     <li>PreparedStatement 效率更高，且可防止 SQL 注入</li>
 * </ul>
 *
 * @author zengzg
 * @since 2021/1/2
 */
public class DBHelper {

    /** 数据库连接 URL */
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/gang?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
    
    /** 数据库用户名 */
    private static final String USER = "root";
    
    /** 数据库密码 */
    private static final String PASSWORD = "123456";

    /**
     * 连接数据库并查询用户信息
     * <p>
     * 演示使用 PreparedStatement 进行参数化查询，
     * 这种方式可以有效防止 SQL 注入攻击。
     * </p>
     */
    public static void conn() {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        try {
            // 1. 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Database driver loaded successfully.");
            
            // 2. 建立数据库连接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established.");
            
            // 3. 通过数据库连接操作数据库（使用 PreparedStatement）
            String name = "gang";
            String sql = "SELECT * FROM user WHERE username = ?";
            
            // 预编译 SQL
            statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            
            // 4. 执行查询
            rs = statement.executeQuery();
            System.out.println("Query executed: " + sql);
            System.out.println("Parameter: username = " + name);
            System.out.println();
            
            // 5. 处理结果集
            System.out.println("Query Results:");
            System.out.println("----------------------------------------");
            int count = 0;
            while (rs.next()) {
                String username = rs.getString("username");
                String userOrg = rs.getString("user_org");
                System.out.println("Username: " + username + ", Organization: " + userOrg);
                count++;
            }
            System.out.println("----------------------------------------");
            System.out.println("Total records: " + count);
            
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Database driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error: SQL execution failed!");
            e.printStackTrace();
        } finally {
            // 6. 释放资源（注意关闭顺序：ResultSet -> Statement -> Connection）
            closeQuietly(rs);
            closeQuietly(statement);
            closeQuietly(conn);
        }
    }

    /**
     * 静默关闭 ResultSet
     *
     * @param rs 要关闭的 ResultSet
     */
    private static void closeQuietly(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Warning: Failed to close ResultSet");
            }
        }
    }

    /**
     * 静默关闭 PreparedStatement
     *
     * @param statement 要关闭的 PreparedStatement
     */
    private static void closeQuietly(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println("Warning: Failed to close PreparedStatement");
            }
        }
    }

    /**
     * 静默关闭 Connection
     *
     * @param conn 要关闭的 Connection
     */
    private static void closeQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Warning: Failed to close Connection");
            }
        }
    }
}
