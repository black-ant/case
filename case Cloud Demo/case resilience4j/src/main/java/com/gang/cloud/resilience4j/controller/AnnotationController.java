package com.gang.cloud.resilience4j.controller;

import com.gang.cloud.resilience4j.service.SimpleCircuitBreakerAnnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname AnnotationController
 * @Description TODO
 * @Date 2021/6/11
 * @Created by zengzg
 */
@RestController
@RequestMapping("anno")
public class AnnotationController {

    @Autowired
    private SimpleCircuitBreakerAnnoService annoService;

    @GetMapping("get")
    public String getByAnno() {
        return annoService.get();
    }
}
