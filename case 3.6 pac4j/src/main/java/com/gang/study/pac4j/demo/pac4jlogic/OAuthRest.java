package com.gang.study.pac4j.demo.pac4jlogic;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname OAuthRest
 * @Description TODO
 * @Date 2020/5/26 23:20
 * @Created by zengzg
 */
@RestController
@RequestMapping("/oauth")
public class OAuthRest {

    @GetMapping("/accessToken")
    public String getAccessToken() {
        return "";
    }
}
