package com.gang.study.elasticsearch.demo.controller;

import com.gang.study.elasticsearch.demo.entity.AOrder;
import com.gang.study.elasticsearch.demo.service.SpringService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname SpringController
 * @Description TODO
 * @Date 2022/3/19
 * @Created by zengzg
 */
@RestController
@RequestMapping("/spring")
public class SpringController {


    @Resource
    private SpringService springService;

    @GetMapping
    public List<AOrder> get() {
        return springService.run();
    }

}
