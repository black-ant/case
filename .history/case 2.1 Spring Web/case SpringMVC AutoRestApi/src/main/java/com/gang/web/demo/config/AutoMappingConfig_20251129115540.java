package com.gang.web.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

/**
 * 自动 API 映射配置
 * <p>
 * 演示如何在应用启动时动态注册 RequestMapping，无需使用 @Controller 和 @RequestMapping 注解。
 * </p>
 * 
 * <h3>核心 API：</h3>
 * <ul>
 *     <li>{@link RequestMappingHandlerMapping#registerMapping} - 注册映射</li>
 *     <li>{@link RequestMappingHandlerMapping#unregisterMapping} - 注销映射</li>
 * </ul>
 * 
 * <h3>工作原理：</h3>
 * <ol>
 *     <li>使用 @PostConstruct 在 Bean 初始化后执行</li>
 *     <li>通过反射获取处理方法</li>
 *     <li>构建 RequestMappingInfo（URL 模式、HTTP 方法等）</li>
 *     <li>调用 registerMapping 注册到 HandlerMapping</li>
 * </ol>
 *
 * @author zengzg
 * @since 2022/9/12
 */
@Configuration
public class AutoMappingConfig {

    private static final Logger logger = LoggerFactory.getLogger(AutoMappingConfig.class);

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    /**
     * 动态注册的 API 处理方法
     * <p>
     * 此方法将被动态映射到 /test/hello/get 路径。
     * 注意：该方法没有任何 Spring MVC 注解，但仍可作为 API 端点。
     * </p>
     *
     * @return 响应内容
     */
    public String helloWorld() {
        logger.info("Dynamic API called: helloWorld");
        return "Hello from dynamically registered API!";
    }

    /**
     * 应用启动时动态注册 RequestMapping
     * <p>
     * 在 Bean 初始化完成后执行，将 helloWorld 方法注册为 GET /test/hello/get 端点。
     * </p>
     *
     * @throws Exception 注册失败时抛出异常
     */
    @PostConstruct
    public void addRequestMapper() throws Exception {
        logger.info("Registering dynamic API mappings...");

        // 1. 获取要注册的方法
        Class<?> handlerClass = this.getClass();
        Method handlerMethod = ReflectionUtils.findMethod(handlerClass, "helloWorld");
        
        if (handlerMethod == null) {
            logger.error("Handler method not found: helloWorld");
            return;
        }

        // 2. 构建 URL 模式
        PatternsRequestCondition patterns = new PatternsRequestCondition("/test/hello/get");

        // 3. 构建 HTTP 方法条件
        RequestMethodsRequestCondition methods = new RequestMethodsRequestCondition(RequestMethod.GET);

        // 4. 创建 RequestMappingInfo
        RequestMappingInfo mappingInfo = new RequestMappingInfo(
                patterns,    // URL 模式
                methods,     // HTTP 方法
                null,        // 请求参数条件
                null,        // 请求头条件
                null,        // 消费类型条件
                null,        // 生产类型条件
                null         // 自定义条件
        );

        // 5. 注册映射
        // 注意：第二个参数是 handler 对象，不是类
        requestMappingHandlerMapping.registerMapping(mappingInfo, this, handlerMethod);
        
        logger.info("Dynamic API registered: GET /test/hello/get");
    }
}
