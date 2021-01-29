package com.gang.oauth.pkce.demo.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname ClientController
 * @Description TODO
 * @Date 2021/1/29 22:09
 * @Created by zengzg
 */
@RestController
@RequestMapping
public class ClientController {

    @GetMapping
    public void test() {
    }

}
