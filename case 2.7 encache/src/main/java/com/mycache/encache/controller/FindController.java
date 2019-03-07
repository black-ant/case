package com.mycache.encache.controller;

import com.mycache.encache.service.FindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/3/7 22:36
 * @Version 1.0
 **/
@RestController
public class FindController {

    @Autowired
    private FindService findService;

    @GetMapping("getAllByCache")
    public ModelAndView findByCache() {
        ModelAndView modelAndView = new ModelAndView();
        long startTime = System.currentTimeMillis();   //获取开始时间
        modelAndView.addObject("list", findService.findDepartments());
        long endTime = System.currentTimeMillis(); //获取结束时间
        modelAndView.addObject("start", startTime);
        modelAndView.addObject("endTime", endTime);
        return modelAndView;
    }

    @GetMapping("getByDepName")
    public ModelAndView findByCacheName() {
        ModelAndView modelAndView = new ModelAndView();
        long startTime = System.currentTimeMillis();   //获取开始时间
        modelAndView.addObject("list", findService.findDepartmentsByFilter("Sales"));
        long endTime = System.currentTimeMillis(); //获取结束时间
        modelAndView.addObject("start", startTime);
        modelAndView.addObject("endTime", endTime);
        modelAndView.setViewName("index");
        return modelAndView;
    }
    @GetMapping("test")
    public String test() {
        return "index";
    }
}
