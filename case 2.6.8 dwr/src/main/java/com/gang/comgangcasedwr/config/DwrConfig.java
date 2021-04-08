package com.gang.comgangcasedwr.config;

import com.gang.comgangcasedwr.service.SpringDwrServlet;
import org.directwebremoting.spring.DwrSpringServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname DwrConfig
 * @Description TODO
 * @Date 2020/9/18
 * @Created by zengzg
 */
@Configuration
@ComponentScan(value = {"com.gang.comgangcasedwr.config"})
public class DwrConfig {


    /**
     * @param springDwrServlet SpringDwrServlet
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean registDwrServlet(SpringDwrServlet springDwrServlet) {
        ServletRegistrationBean servletRegister = new ServletRegistrationBean(springDwrServlet, "/dwr/*");
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("debug", "true");
        initParameters.put("activeReverseAjaxEnabled", "true");
        initParameters.put("pollAndCometEnabled", "true");
        servletRegister.setInitParameters(initParameters);
        return servletRegister;
    }

}
