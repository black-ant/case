package com.gang.cloud.consumer.demo.service;

import com.gang.cloud.dubbo.service.IUserService;
import com.gang.cloud.dubbo.to.UserTO;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
