/**
 * @Classname DBHelper
 * @Description TODO
 * @Date 2021/1/2 17:01
 * @Created by zengzg
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Java��ʹ��JDBC�������ݿ�
 * 1�� �������� 2�� �������ݿ�����
 * 3�� ����ִ��sql����� 4�� ִ����� 5�� ����ִ�н�� 6�� �ͷ���Դ
 *
 * @author liu.hb
 */
public class DBHelper {

    /**
     * Statement �� PreparedStatement֮��Ĺ�ϵ������.
     * ��ϵ��PreparedStatement�̳���Statement,���ǽӿ�
     * ����PreparedStatement����ʹ��ռλ������Ԥ����ģ��������StatementЧ�ʸ�
     */
    public static void conn() {
        String URL = "jdbc:mysql://127.0.0.1:3306/gang?characterEncoding=utf-8";
        String USER = "root";
        String PASSWORD = "123456";
        // 1.������������
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // 2.������ݿ�����
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // 3.ͨ�����ݿ�����Ӳ������ݿ⣬ʵ����ɾ�Ĳ飨ʹ��Statement�ࣩ
            String name = "gang";
            //Ԥ����
            String sql = "select * from user where username=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
//			String sql="select * from userinfo where UserName='"+name+"'";
//			Statement statement = conn.createStatement();
//			ResultSet rs = statement.executeQuery(sql);
            // 4.�������ݿ�ķ��ؽ��(ʹ��ResultSet��)
            while (rs.next()) {
                System.out.println(rs.getString("username") + " " + rs.getString("user_org"));
            }

            // �ر���Դ
            conn.close();
            rs.close();
            statement.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
