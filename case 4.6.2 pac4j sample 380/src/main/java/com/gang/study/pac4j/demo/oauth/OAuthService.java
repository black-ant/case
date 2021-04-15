package com.gang.study.pac4j.demo.oauth;

import com.gang.study.pac4j.demo.api.DefaultOAuthAPI;
import com.gang.study.pac4j.demo.base.BasePac4jService;
import com.gang.study.pac4j.demo.definition.DefaultOAuthDefinition;
import org.pac4j.core.context.J2EContext;
import org.pac4j.oauth.client.OAuth20Client;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.profile.casoauthwrapper.CasOAuthWrapperProfileDefinition;
import org.pac4j.oauth.profile.definition.OAuth20ProfileDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname OAuthService
 * @Description TODO
 * @Date 2021/4/13
 * @Created by zengzg
 */
@Component
public class OAuthService extends BasePac4jService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String CLIENT_ID = "b45e4-41c0-demo";
    private final static String CLIENT_SECRET = "0407581-ef15-f773-demo";
    private final static String CALLBACK_URL = "http://127.0.0.1:8088/oauth/callback";

    /**
     * 执行 Authorization 请求
     *
     * @return
     */
    public void doOAuthRequest(HttpServletRequest request, HttpServletResponse response) {

        // Step 1 :构建请求 Client
        OAuth20Configuration config = new OAuth20Configuration();
        config.setApi(new DefaultOAuthAPI());
        config.setProfileDefinition(new DefaultOAuthDefinition());
        config.setScope("user");
        config.setKey(CLIENT_ID);
        config.setSecret(CLIENT_SECRET);

        // Step 2 : 构建一个 Client
        OAuth20Client client = new OAuth20Client();

        // 补充完善属性
        client.setConfiguration(config);
        client.setCallbackUrl(CALLBACK_URL);

        // Step 3 : 构建请求 , 这里通过 302 重定向
        J2EContext context = new J2EContext(request, response);
        client.redirect(context);

        // Step 4 : 缓存数据
        request.getSession().setAttribute("client", client);

    }
}
