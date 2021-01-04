package com.chapterJPA.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.chapterJPA.demo.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonIoController {
    //直接注入数据
    @Value("ant-black")
    public String name;
    //
    @Value("${myResource.url}")
    public String url;

    @Value("#{mainService.work}")
    public String work;

    @Value("#{1+1}")
    public String year;


    @Autowired
    public MainService mainService;


    @RequestMapping("/ioc")
    public JSONObject ioController(){
        JSONObject node = new JSONObject();
        node.put("name",name);
        node.put("url",url);
        node.put("work",work);
        node.put("year",year);
        return node;
    }
}
