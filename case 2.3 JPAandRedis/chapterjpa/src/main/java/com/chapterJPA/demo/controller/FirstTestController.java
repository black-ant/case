package com.chapterJPA.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.chapterJPA.demo.entity.Departments;
import com.chapterJPA.demo.entity.repository.DepartmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FirstTestController {

    @Autowired
    private DepartmentsRepository userRepository;



    @RequestMapping("test")
    public String test(){
        return "test ok";
    }

    @RequestMapping("findAllDep")
    public String findAllDep(){
        JSONObject node = new JSONObject();
        List<Departments> list =userRepository.findAll();
        for (Departments x : list){
            System.out.print(x.getDeptno());
        }
        node.put("list",list);
        return node.toJSONString();
    }



}
