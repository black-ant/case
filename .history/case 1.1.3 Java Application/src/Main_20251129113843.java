/**
 * 纯 Java 应用入口类
 * <p>
 * 这是一个不依赖任何框架的纯 Java 应用示例，演示：
 * <ul>
 *     <li>原生 JDBC 数据库操作</li>
 *     <li>Java 应用程序基本结构</li>
 * </ul>
 * </p>
 * 
 * <h3>运行方式：</h3>
 * <pre>
 * # 编译
 * javac -cp mysql-connector-java.jar Main.java DBHelper.java
 * 
 * # 运行
 * java -cp .:mysql-connector-java.jar Main
 * </pre>
 * 
 * <h3>前置条件：</h3>
 * <ul>
 *     <li>MySQL 数据库运行在 localhost:3306</li>
 *     <li>存在名为 'gang' 的数据库</li>
 *     <li>数据库中存在 'user' 表</li>
 * </ul>
 *
 * @author zengzg
 * @since 1.0
 */
public class Main {

    /**
     * 应用程序入口
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("  Java Application Demo");
        System.out.println("=================================");
        System.out.println();
        
        System.out.println("Connecting to database...");
        DBHelper.conn();
        
        System.out.println();
        System.out.println("Application finished.");
    }
}
