package com.gang.study.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Servlet 上下文监听器示例
 * <p>
 * 演示 ServletContextListener 的用法，用于监听 Web 应用的启动和关闭事件：
 * <ul>
 *     <li>应用启动时初始化资源（如数据库连接池）</li>
 *     <li>应用关闭时释放资源</li>
 * </ul>
 * </p>
 * 
 * <h3>生命周期：</h3>
 * <ol>
 *     <li>contextInitialized() - Web 应用启动时调用</li>
 *     <li>contextDestroyed() - Web 应用关闭时调用</li>
 * </ol>
 *
 * @author zengzg
 * @since 2020/9/14
 */
@WebListener
public class MyServletContextListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(MyServletContextListener.class);

    /**
     * Web 应用关闭时调用
     * <p>
     * 在此方法中释放应用级别的资源，如：
     * <ul>
     *     <li>关闭数据库连接池</li>
     *     <li>停止后台线程</li>
     *     <li>释放缓存</li>
     * </ul>
     * </p>
     *
     * @param sce Servlet 上下文事件
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("ServletContext destroyed - Application shutting down");
        logger.info("Context path: {}", sce.getServletContext().getContextPath());
    }

    /**
     * Web 应用启动时调用
     * <p>
     * 在此方法中初始化应用级别的资源，如：
     * <ul>
     *     <li>初始化数据库连接池</li>
     *     <li>加载配置文件</li>
     *     <li>启动后台任务</li>
     * </ul>
     * </p>
     *
     * @param sce Servlet 上下文事件
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("ServletContext initialized - Application starting up");
        logger.info("Server info: {}", sce.getServletContext().getServerInfo());
    }
}
