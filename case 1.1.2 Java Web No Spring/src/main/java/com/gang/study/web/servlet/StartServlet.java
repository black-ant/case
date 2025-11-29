package com.gang.study.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 启动 Servlet 示例
 * <p>
 * 演示原生 Servlet 的基本用法，包括：
 * <ul>
 *     <li>处理 GET 和 POST 请求</li>
 *     <li>获取请求参数</li>
 *     <li>输出响应内容</li>
 * </ul>
 * </p>
 * 
 * <h3>访问方式：</h3>
 * <pre>
 * GET/POST http://localhost:8080/context-path/start
 * </pre>
 *
 * @author zengzg
 * @since 2020/7/9
 */
@WebServlet(name = "StartServlet", urlPatterns = {"/start"})
public class StartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(StartServlet.class);

    /**
     * 处理 GET 请求
     * <p>
     * 默认转发到 doPost 方法统一处理
     * </p>
     *
     * @param request  HTTP 请求对象
     * @param response HTTP 响应对象
     * @throws ServletException Servlet 异常
     * @throws IOException      IO 异常
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Received GET request to /start");
        doPost(request, response);
    }

    /**
     * 处理 POST 请求
     *
     * @param request  HTTP 请求对象
     * @param response HTTP 响应对象
     * @throws ServletException Servlet 异常
     * @throws IOException      IO 异常
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Processing request in doPost");
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>StartServlet</title></head>");
            out.println("<body>");
            out.println("<h1>Welcome to Java Web (No Spring)!</h1>");
            out.println("<p>This is a traditional Servlet example.</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
