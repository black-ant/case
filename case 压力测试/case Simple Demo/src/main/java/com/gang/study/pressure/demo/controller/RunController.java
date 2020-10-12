package com.gang.study.pressure.demo.controller;

import com.gang.study.pressure.demo.logic.RunLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname RunController
 * @Description TODO
 * @Date 2020/10/10 9:45
 * @Created by zengzg
 */
@RequestMapping("/run")
@RestController
public class RunController {

    @Autowired
    private RunLogic runLogic;

    @GetMapping("/start")
    public String success() {
        runLogic.run();
        return "success";
    }

    @GetMapping("removeAll")
    public String removeAll() {
        runLogic.remove();
        return "success";
    }

    @GetMapping("show")
    public Integer show() {
        return runLogic.getUserTOS().size();
    }
}
