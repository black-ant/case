package com.gang.study.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @Classname MyServlet
 * @Description TODO
 * @Date 2020/9/14 22:54
 * @Created by zengzg
 */
@WebListener
public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContext destroyed");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ServletContext initialized");
    }

}
