package com.gang.web.demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义过滤器
 * <p>
 * 演示 Spring Boot 中 Filter 的用法，可用于：
 * <ul>
 *     <li>请求日志记录</li>
 *     <li>请求参数预处理</li>
 *     <li>跨域处理（CORS）</li>
 *     <li>字符编码设置</li>
 *     <li>权限验证</li>
 * </ul>
 * </p>
 * 
 * <h3>Filter vs Interceptor：</h3>
 * <table>
 *     <tr><th>特性</th><th>Filter</th><th>Interceptor</th></tr>
 *     <tr><td>规范</td><td>Servlet 规范</td><td>Spring MVC 规范</td></tr>
 *     <tr><td>执行顺序</td><td>先于 Interceptor</td><td>后于 Filter</td></tr>
 *     <tr><td>依赖注入</td><td>需要特殊处理</td><td>原生支持</td></tr>
 * </table>
 *
 * @author zengzg
 * @since 2021/5/11
 */
@Component
public class CustomerFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CustomerFilter.class);

    /**
     * 过滤器初始化
     * <p>
     * 在过滤器实例创建后调用一次，可用于初始化资源。
     * </p>
     *
     * @param filterConfig 过滤器配置
     * @throws ServletException 初始化异常
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("CustomerFilter initialized");
    }

    /**
     * 执行过滤逻辑
     * <p>
     * 每个请求都会经过此方法：
     * <ol>
     *     <li>前置处理（请求到达 Controller 之前）</li>
     *     <li>调用 filterChain.doFilter() 继续处理</li>
     *     <li>后置处理（响应返回客户端之前）</li>
     * </ol>
     * </p>
     *
     * @param servletRequest  请求对象
     * @param servletResponse 响应对象
     * @param filterChain     过滤器链
     * @throws IOException      IO 异常
     * @throws ServletException Servlet 异常
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, 
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        long startTime = System.currentTimeMillis();
        String requestUrl = request.getRequestURL().toString();
        String method = request.getMethod();
        
        logger.info(">>> Request: {} {}", method, requestUrl);
        
        // 继续过滤器链
        filterChain.doFilter(servletRequest, servletResponse);
        
        long duration = System.currentTimeMillis() - startTime;
        logger.info("<<< Response: {} {} - {} ms", method, requestUrl, duration);
    }

    /**
     * 过滤器销毁
     * <p>
     * 在过滤器实例销毁前调用，可用于释放资源。
     * </p>
     */
    @Override
    public void destroy() {
        logger.info("CustomerFilter destroyed");
    }
}
