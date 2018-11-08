package com.chapterJPA.demo.controller;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class MvcController {

    public Logger log = LoggerFactory.getLogger("HtmlObject.class");
    @RequestMapping(value= "/index")
    public String index(){
        log.debug("调试debug信息");
        log.info("日志测试 log info");
        log.warn("日志测试 log warn");
        log.error("日志测试 log error");
        log.debug("日志测试 log debug");
        return "index";
    }

    @RequestMapping(value="/obj",method = RequestMethod.POST)
    public @ResponseBody JSONObject obTest(String data){
        JSONObject node = new JSONObject();
        Map<String,String> map = new LinkedHashMap<>();
        map.put("11","第1个");
        map.put("13","第2个");
        map.put("12","第3个");
        map.put("21","第4个");
        map.put("23","第5个");
        map.put("22","第6个");
        for (String key : map.keySet()){
            log.debug("key is :{}--value is :{}",key,map.get(key));
        }
        node.put("map",map);
        return node;
    }

}