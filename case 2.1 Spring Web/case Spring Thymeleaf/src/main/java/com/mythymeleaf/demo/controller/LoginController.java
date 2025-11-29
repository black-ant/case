package com.mythymeleaf.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录控制器
 * <p>
 * 演示 Thymeleaf 的表单处理和页面重定向。
 * </p>
 *
 * @author zengzg
 * @since 2021/1/10
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 显示登录页面
     * <p>
     * GET /login
     * </p>
     *
     * @return 登录页视图
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    /**
     * 处理登录请求
     * <p>
     * POST /doLogin
     * </p>
     * <p>
     * 演示表单数据接收和条件跳转：
     * <ul>
     *     <li>登录成功 - 重定向到用户页</li>
     *     <li>登录失败 - 返回登录页并显示错误信息</li>
     * </ul>
     * </p>
     *
     * @param username 用户名
     * @param password 密码
     * @return ModelAndView
     */
    @PostMapping(value = "/doLogin")
    public ModelAndView doLogin(String username, String password) {
        logger.info("Login attempt: username={}", username);
        
        ModelAndView modelAndView = new ModelAndView();
        
        // 简单的登录验证（演示用，生产环境应使用 Spring Security）
        if ("ant".equals(username) && "123456".equals(password)) {
            logger.info("Login successful for user: {}", username);
            modelAndView.setViewName("redirect:/user");
        } else {
            logger.warn("Login failed for user: {}", username);
            modelAndView.setViewName("login");
            modelAndView.addObject("errorMsg", "账号或密码错误");
        }
        
        return modelAndView;
    }
}
