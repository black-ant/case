package com.gang.study.jersey.comgangjersey.config;

import com.gang.study.jersey.comgangjersey.controller.StartController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

/**
 * Jersey 配置类
 * <p>
 * 配置 Jersey 应用，包括：
 * <ul>
 *     <li>应用路径前缀</li>
 *     <li>资源类注册</li>
 *     <li>过滤器和拦截器</li>
 * </ul>
 * </p>
 * 
 * <h3>注册资源的方式：</h3>
 * <pre>
 * // 方式1：按包扫描
 * packages("com.gang.controller");
 * 
 * // 方式2：注册单个类
 * register(StartController.class);
 * 
 * // 方式3：注册实例
 * register(new MyFilter());
 * </pre>
 *
 * @author zengzg
 * @since 2020/1/17
 */
@Component
@ApplicationPath("/rest")
public class JerseyConfig extends ResourceConfig {

    /**
     * 构造函数，初始化 Jersey 配置
     */
    public JerseyConfig() {
        // 方式1：扫描指定包下的所有资源类
        packages("com.gang.study.jersey.comgangjersey.controller");
        
        // 方式2：也可以单独注册资源类
        // register(StartController.class);
        
        // 注册 Jackson JSON 提供者（如果需要）
        // register(JacksonFeature.class);
    }
}
