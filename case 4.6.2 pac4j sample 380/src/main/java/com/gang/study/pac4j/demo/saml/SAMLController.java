package com.gang.study.pac4j.demo.saml;

import org.pac4j.core.context.J2EContext;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.saml.client.SAML2Client;
import org.pac4j.saml.credentials.SAML2Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("saml")
public class SAMLController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SAMLService samlService;

    @GetMapping("authoriza")
    public void authoriza(HttpServletRequest request, HttpServletResponse response) {
        logger.info("------> [调用 pac4j OAuth 逻辑] <-------");
        samlService.doOAuthRequest(request, response);
    }

    @GetMapping("callback")
    public void samlCallBackGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        samlCallBack(request, response);
    }


    @PostMapping("callback")
    public void samlCallBack(final HttpServletRequest request, final HttpServletResponse response) throws IOException {

        logger.info("------> [SSO 回调 pac4j SAML 逻辑] <-------");

        // Step 1 : 准备对象
        final SAML2Client client = (SAML2Client) request.getSession().getAttribute("client");
        J2EContext context = new J2EContext(request, response);

        // Step 2 : 获取票据
        final SAML2Credentials credentials = client.getCredentials(context);

        // Step 3 : 获取 Profile
        final CommonProfile profile = client.getUserProfile(credentials, context);
        response.getWriter().println(profile.toString());

        logger.info("------> Pac4j Demo 获取用户信息 :[{}] <-------", profile.toString());
    }
}
