package com.gang.cloud.consumer.demo.service;

import com.gang.cloud.dubbo.service.IUserService;
import com.gang.cloud.dubbo.to.UserTO;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference(version = "${dubbo.service.version}")
    private IUserService userService;

    @GetMapping("/get")
    public String initOrder() {

        logger.info("------> this is before get <-------");
        UserTO userTO = userService.getUser();
        return userTO.getUsername();
    }

}
