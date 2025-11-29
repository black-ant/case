package com.gang.web.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring MVC 动态 API 注册示例应用
 * <p>
 * 本项目演示如何在运行时动态注册 RequestMapping，实现：
 * <ul>
 *     <li>动态创建 REST API 端点</li>
 *     <li>程序化配置 RequestMappingHandlerMapping</li>
 *     <li>无需 @Controller 注解即可添加 API</li>
 * </ul>
 * </p>
 * 
 * <h3>应用场景：</h3>
 * <ul>
 *     <li>插件化架构 - 动态加载插件并注册 API</li>
 *     <li>配置驱动 - 根据配置文件动态生成 API</li>
 *     <li>API 网关 - 动态路由配置</li>
 * </ul>
 *
 * @author zengzg
 * @since 1.0
 */
@SpringBootApplication
public class AutoRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoRestApiApplication.class, args);
    }
}

