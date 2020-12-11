package com.gang.exception.json.controller;

import com.gang.exception.json.entity.TestTO;
import com.gang.exception.json.service.JSONService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2020/12/11 17:04
 * @Created by zengzg
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private JSONService jsonService;

    @GetMapping("user")
    public TestTO getUser() {
        return jsonService.getExtendFieldObjFromJson("gang");
    }
}
