package com.gang.cloud.client.demo.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gang.cloud.dubbo.service.IUserService;
import com.gang.cloud.dubbo.to.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname OrderController
 * @Description TODO
 * @Date 2020/8/16 12:12
 * @Created by zengzg
 */
@RestController
public class UserController {

    @Reference(version = "${dubbo.service.version}")
    private IUserService userService;

    @GetMapping("/get")
    public String initOrder() {
        UserTO userTO = userService.getUser();
        return userTO.getUsername();
    }

}
