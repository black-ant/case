package com.gang.study.pac4j.demo.oauth;

import org.pac4j.core.context.J2EContext;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.oauth.client.CasOAuthWrapperClient;
import org.pac4j.oauth.client.OAuth20Client;
import org.pac4j.oauth.credentials.OAuth20Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public void authoriza(HttpServletRequest request, HttpServletResponse response) {
        logger.info("------> [调用 pac4j OAuth 逻辑] <-------");
        oAuthService.doOAuthRequest(request, response);
    }

    @GetMapping("callback")
    public void callBack(final HttpServletRequest request, final HttpServletResponse response) throws IOException {

        logger.info("------> [SSO 回调 pac4j OAuth 逻辑] <-------");

        // 从 Session 中获取缓存的对象
        OAuth20Client client = (OAuth20Client) request.getSession().getAttribute("client");
        J2EContext context = new J2EContext(request, response);

        // 获取 AccessToken 对应的Credentials
        final Credentials credentials = client.getCredentials(context);

        // 通过 Profile 获取 Profile
        final CommonProfile profile = client.getUserProfile(credentials, context);

        // Web 返回数据信息
        logger.info("------> Pac4j Demo 获取用户信息 :[{}] <-------", profile.toString());
        response.getWriter().println(profile.toString());
    }
}
