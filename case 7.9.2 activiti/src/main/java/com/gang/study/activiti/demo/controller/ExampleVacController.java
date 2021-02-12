package com.gang.study.activiti.demo.controller;

import com.gang.study.activiti.demo.logic.ExampleVacService;
import com.gang.study.activiti.demo.to.Vacation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Classname ExampleVacController
 * @Description TODO
 * @Date 2021/2/11 15:39
 * @Created by zengzg
 */
@RequestMapping("/vac")
@RestController
public class ExampleVacController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExampleVacService vacService;

    @PostMapping("/start")
    public Object startVac(@RequestBody Vacation vac, @RequestParam("userName") String userName) {
        logger.info("------> this is in vac start <-------");
        return vacService.startVac(userName, vac);
    }

    @GetMapping("/get")
    public Object myVac(HttpSession session) {
        logger.info("------> this is in vac get <-------");
        String userName = (String) session.getAttribute("userName");
        return vacService.myVac(userName);
    }

    @GetMapping("/list")
    public Object myVacRecord(@RequestParam("userName") String userName) {
        logger.info("------> this is in vac list <-------");
        return vacService.myVacRecord(userName);
    }
}
