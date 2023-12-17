package com.gang.web.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

/**
 * @Classname AutoMappingConfig
 * @Description TODO
 * @Date 2022/9/12
 * @Created by zengzg
 */
@Configuration
public class AutoMappingConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    public String helloWorld() {
        logger.info("------> this is in hello <-------");
        return "success";
    }

    @PostConstruct
    public String addRequestMapper() throws IllegalAccessException, InstantiationException {

        // 获取到指定的方法体
        Class<?> entry = this.getClass();
        Method methodName = ReflectionUtils.findMethod(entry, "helloWorld");

        // 构建请求的路径
        PatternsRequestCondition patterns = new PatternsRequestCondition("test/hello/get");

        // 构建复杂的Mapping 拦截方式
        RequestMethodsRequestCondition method = new RequestMethodsRequestCondition(RequestMethod.GET);
        RequestMappingInfo mappingInfo = new RequestMappingInfo(patterns, method, null, null, null, null, null);

        // 注册 Mapping
        requestMappingHandlerMapping.registerMapping(mappingInfo, entry.newInstance(), methodName);
        return "success";
    }
}
