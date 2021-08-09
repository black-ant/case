package com.gang.study.freemarker.demo.controller;

import com.gang.study.freemarker.demo.to.ContentTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2020/1/20 13:54
 * @Created by zengzg
 */
@RequestMapping(value = "/start")
@Controller
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/login")
    public ModelAndView loginFtl() {
        logger.info("------> this in in login <-------");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping(value = "/doLogin")
    public ModelAndView doLogin(String username, String password) {
        logger.info("------> this in in login :{} <-------", username);
        ModelAndView modelAndView = new ModelAndView();
        if ("ant".equals(username) && "123456".equals(password)) {
            modelAndView.setViewName("redirect:/user");
            List<ContentTO> datalist = new LinkedList<>();
            ContentTO contentTO = new ContentTO();
            contentTO.setContentTitle("title 1 ");
            contentTO.setCreateDate(new Date());
            datalist.add(contentTO);

            ContentTO contentTO2 = new ContentTO();
            contentTO2.setContentTitle("title 2 ");
            contentTO2.setCreateDate(new Date());
            datalist.add(contentTO2);

            modelAndView.addObject("datalist", datalist);
        } else {
            modelAndView.setViewName("login");
            modelAndView.addObject("errorMsg", "账号密码错误");
        }
        return modelAndView;
    }

}
