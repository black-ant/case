package com.mythymeleaf.demo.controller;

import com.mythymeleaf.demo.to.ContentTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2021/1/10 18:46
 * @Created by zengzg
 */
@Controller
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/user")
    public ModelAndView getUser() {
        logger.info("------> this is in user <-------");
        ModelAndView modelAndView = new ModelAndView();
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
        modelAndView.setViewName("user");
        return modelAndView;
    }
}
