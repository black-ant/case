package com.gang.study.freemarker.demo.controller;

import com.gang.study.freemarker.demo.to.UserTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户控制器
 * <p>
 * 演示 Freemarker 模板的数据绑定和渲染。
 * </p>
 *
 * @author zengzg
 * @since 2021/1/9
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 获取用户信息页面
     * <p>
     * GET /user
     * </p>
     * <p>
     * 演示如何将数据传递给 Freemarker 模板进行渲染。
     * 模板文件位于：resources/templates/user.ftl
     * </p>
     *
     * @return ModelAndView 包含视图名和模型数据
     */
    @GetMapping
    public ModelAndView getUser() {
        logger.info("Rendering user page");
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");

        // 创建用户数据
        UserTO userTO = new UserTO();
        userTO.setUsername("gang");
        
        // 将数据添加到模型，在模板中可通过 ${user.username} 访问
        modelAndView.addObject("user", userTO);

        return modelAndView;
    }
}
