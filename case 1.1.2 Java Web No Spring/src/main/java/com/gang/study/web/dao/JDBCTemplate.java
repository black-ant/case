package com.gang.study.web.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Classname BaseJdbcDAO
 * @Description TODO
 * @Date 2020/7/9 16:42
 * @Created by zengzg
 */
public class JDBCTemplate {

    public ResultSet doExcuter(String sql) {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/jdbctest";
        String username = "root";
        String password = "mysqladmin";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 7���رն��󣬻������ݿ���Դ
            if (rs != null) { //�رս��������
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) { // �ر����ݿ��������
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) { // �ر����ݿ����Ӷ���
                try {
                    if (!conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return rs;
    }
}
