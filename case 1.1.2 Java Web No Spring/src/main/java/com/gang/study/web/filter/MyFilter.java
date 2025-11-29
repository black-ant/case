package com.gang.study.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 自定义过滤器示例
 * <p>
 * 演示 Servlet Filter 的基本用法，实现请求预处理功能：
 * <ul>
 *     <li>设置请求字符编码</li>
 *     <li>设置响应内容类型</li>
 *     <li>记录请求日志</li>
 * </ul>
 * </p>
 * 
 * <h3>过滤器生命周期：</h3>
 * <ol>
 *     <li>init() - 容器启动时初始化</li>
 *     <li>doFilter() - 每次请求时执行</li>
 *     <li>destroy() - 容器关闭时销毁</li>
 * </ol>
 *
 * @author zengzg
 * @since 2020/7/9
 */
@WebFilter(filterName = "MyFilter", urlPatterns = {"/*"})
public class MyFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(MyFilter.class);

    /**
     * 过滤器销毁时调用
     * <p>
     * 可在此方法中释放资源
     * </p>
     */
    @Override
    public void destroy() {
        logger.info("MyFilter destroyed");
    }

    /**
     * 执行过滤逻辑
     * <p>
     * 每个请求都会经过此方法，可在此进行：
     * <ul>
     *     <li>请求预处理（如编码设置）</li>
     *     <li>权限验证</li>
     *     <li>日志记录</li>
     *     <li>响应后处理</li>
     * </ul>
     * </p>
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param chain    过滤器链
     * @throws IOException      IO 异常
     * @throws ServletException Servlet 异常
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("MyFilter processing request");
        
        // 设置请求编码
        request.setCharacterEncoding("UTF-8");
        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");
        
        // 继续过滤器链
        chain.doFilter(request, response);
        
        logger.info("MyFilter finished processing");
    }

    /**
     * 过滤器初始化时调用
     * <p>
     * 可在此方法中读取配置参数
     * </p>
     *
     * @param filterConfig 过滤器配置
     * @throws ServletException Servlet 异常
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("MyFilter initialized, filter name: {}", filterConfig.getFilterName());
    }
}
