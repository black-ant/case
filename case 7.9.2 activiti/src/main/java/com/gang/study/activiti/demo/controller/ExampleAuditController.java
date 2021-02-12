package com.gang.study.activiti.demo.controller;

import com.gang.study.activiti.demo.logic.ExampleAuditService;
import com.gang.study.activiti.demo.to.VacTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Classname ExampleAuditController
 * @Description TODO
 * @Date 2021/2/11 15:43
 * @Created by zengzg
 */
@RequestMapping("/audit")
@RestController
public class ExampleAuditController {

    @Autowired
    private ExampleAuditService auditService;

    @GetMapping("/get")
    public Object myAudit(HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        return auditService.myAudit(userName);
    }

    @PostMapping("/pass")
    public Object passAudit(HttpSession session, @RequestBody VacTask vacTask) {
        String userName = (String) session.getAttribute("userName");
        return auditService.passAudit(userName, vacTask);
    }


    @GetMapping("/list")
    public Object myAuditRecord(HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        return auditService.myAuditRecord(userName);
    }
}
