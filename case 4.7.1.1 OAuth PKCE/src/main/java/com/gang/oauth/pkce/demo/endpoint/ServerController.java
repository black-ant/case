package com.gang.oauth.pkce.demo.endpoint;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname ServerController
 * @Description TODO
 * @Date 2021/1/29 22:11
 * @Created by zengzg
 */
@RestController
public class ServerController {

    @RequestMapping("/")
    public String authorize() {
        return "";
    }
}
