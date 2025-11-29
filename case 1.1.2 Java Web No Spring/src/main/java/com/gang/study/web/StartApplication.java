package com.gang.study.web;

/**
 * 传统 Java Web 应用入口类
 * <p>
 * 这个类作为项目的标识入口，实际上传统 Web 应用通过 web.xml 或注解配置启动。
 * </p>
 * <p>
 * 本项目演示了不使用 Spring 框架的原生 Java Web 开发方式，包括：
 * <ul>
 *     <li>Servlet - 处理 HTTP 请求</li>
 *     <li>Filter - 请求过滤和预处理</li>
 *     <li>Listener - 应用生命周期监听</li>
 *     <li>JDBC - 原生数据库操作</li>
 * </ul>
 * </p>
 *
 * @author zengzg
 * @since 2020/6/16
 */
public class StartApplication {
    
    /**
     * 应用版本号
     */
    public static final String VERSION = "1.0.0";
    
    /**
     * 获取应用信息
     *
     * @return 应用描述信息
     */
    public static String getAppInfo() {
        return "Java Web No Spring Demo v" + VERSION;
    }
}
