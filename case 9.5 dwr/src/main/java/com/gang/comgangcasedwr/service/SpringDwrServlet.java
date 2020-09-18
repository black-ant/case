package com.gang.comgangcasedwr.service;

import org.directwebremoting.impl.StartupUtil;
import org.directwebremoting.spring.DwrSpringServlet;
import org.directwebremoting.spring.SpringContainer;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.ServletConfig;

/**
 * @Classname SpringDwrServlet
 * @Description TODO
 * @Date 2020/9/18 21:40
 * @Created by zengzg
 */

@Component
public class SpringDwrServlet extends DwrSpringServlet {

    /**  */
    private static final long serialVersionUID = 1L;

    @Override
    protected SpringContainer createContainer(ServletConfig servletConfig) {
        ApplicationContext appContext = getApplicationContext(servletConfig.getServletContext());

        SpringDwrContainer springContainer = new SpringDwrContainer();
        springContainer.setBeanFactory(appContext);
        StartupUtil.setupDefaultContainer(springContainer, servletConfig);
        return springContainer;
    }
}
