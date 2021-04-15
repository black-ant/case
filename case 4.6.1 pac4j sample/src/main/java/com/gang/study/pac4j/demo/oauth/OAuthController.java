package com.gang.study.pac4j.demo.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname OAuthController
 * @Description TODO
 * @Date 2021/4/13
 * @Created by zengzg
 */
@RestController
@RequestMapping("oauth")
public class OAuthController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OAuthService oAuthService;

    @GetMapping("authoriza")
    public String authoriza(HttpServletRequest request, HttpServletResponse response) {
        logger.info("------> [调用 pac4j OAuth 逻辑] <-------");
        return oAuthService.doOAuthRequest(request, response);
    }
}
