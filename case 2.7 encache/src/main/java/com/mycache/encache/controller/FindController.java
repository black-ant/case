package com.mycache.encache.controller;

import com.mycache.encache.entity.Departments;
import com.mycache.encache.service.FindService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/3/7 22:36
 * @Version 1.0
 **/
@RestController
public class FindController {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private FindService findService;

    @GetMapping("getAllByCache")
    public Map findByCache() {
        Map<String, Object> map = new LinkedHashMap<>();
        long startTime = System.currentTimeMillis();   //获取开始时间
        map.put("list", findService.findDepartments());
        long endTime = System.currentTimeMillis(); //获取结束时间
        map.put("Time", endTime - startTime);
        return map;
    }

    @GetMapping("getByDepName")
    public Map findByCacheName() {
        Map<String, Object> map = new LinkedHashMap<>();
        long startTime = System.currentTimeMillis();   //获取开始时间
        map.put("list", findService.findDepartmentsByFilter("Sales"));
        long endTime = System.currentTimeMillis(); //获取结束时间
        map.put("Time", endTime - startTime);
        return map;
    }

    @GetMapping("createone")
    public Departments createOne() {
        Departments departments = new Departments("D110","gang");
        findService.createOne(departments);
        return departments;
    }

    @GetMapping("findbydepno")
    public Map findByDepNo(@RequestParam("depno") String depno) {
        Map<String, Object> map = new LinkedHashMap<>();
        long startTime = System.currentTimeMillis();   //获取开始时间
        map.put("list", findService.findByDepno(depno));
        long endTime = System.currentTimeMillis(); //获取结束时间
        map.put("Time", endTime - startTime);
        return map;
    }

    @GetMapping("clear")
    public String clearCache() {
        findService.clearCache();
        return "clear ok";
    }

}
