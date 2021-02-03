package com.gang.oauth.pkce.demo.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname ServerController
 * @Description TODO
 * @Date 2021/1/29 22:11
 * @Created by zengzg
 */
@RestController
public class ServerController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/")
    public String authorize() {
        return "";
    }

    @GetMapping("authorization")
    public void authorization(@RequestParam("client_id") String client_id,
                              @RequestParam("response_type") String response_type,
                              @RequestParam("redirect_uri") String redirect_uri) {

    }

}
