package com.gang.mongodb.demo.controller;

import com.gang.mongodb.demo.service.ChangeStreamServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @Classname ChangeController
 * @Description TODO
 * @Date 2023/8/26
 * @Created by zengzg
 */
@RestController
@RequestMapping("/change")
public class ChangeController {

    @Resource
    private ChangeStreamServiceImpl changeStreamService;

    @GetMapping
    public void change() {
        changeStreamService.doChange();
    }
}
