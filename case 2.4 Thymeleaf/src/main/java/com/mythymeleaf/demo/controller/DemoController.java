package com.mythymeleaf.demo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class DemoController {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @GetMapping("info")
    public String gerInfo(){
        return "my info";
    }

    @GetMapping("callback")
    public ModelAndView getCallBack(){
        ModelAndView modelAndView = new ModelAndView();
        LOG.info("is in callback");
        modelAndView.setViewName("pages/callback");
        return modelAndView;
    }

    @GetMapping("staticHtml")
    public ModelAndView staticHtml(){
        ModelAndView modelAndView = new ModelAndView();
        LOG.info("is in callback");
        modelAndView.setViewName("testStatic");
        return modelAndView;
    }

    @GetMapping("staticHtml1")
    public String staticHtml1(){
        ModelAndView modelAndView = new ModelAndView();
        LOG.info("is in callback staticHtml1");
        return "testStatic.html";
    }


}
