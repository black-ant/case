package com.gang.study.databaseexcel.demo.logic;

/**
 * @Classname DBInfo
 * @Description 数据库连接凭证
 * @Date 2020/5/13 11:33
 * @Created by zengzg
 */

public class DBInfo {

    //数据库地址,数据库端口号,数据库名称
    private String url;
    //用户名
    private String user;
    //密码
    private String password;
    //数据库驱动
    private String driver;
    public DBInfo() {}
    public DBInfo(String url, String user, String password, String driver) {
        this.setUrl(url);
        this.setUser(user);
        this.setPassword(password);
        this.setDriver(driver);
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getDriver() {
        return driver;
    }
    public void setDriver(String driver) {
        this.driver = driver;
    }
    /**
     * 执行生成Excel表格
     * @throws Exception
     */
    public void doIt() throws Exception{
        DBToTable.main(this);
    }

    public static void main(String[] args) throws Exception{
        DBInfo info = new DBInfo("jdbc:mysql://127.0.0.1:3306/cic", "root", "123456", "com.mysql.jdbc.Driver");
        info.doIt();
    }
}
