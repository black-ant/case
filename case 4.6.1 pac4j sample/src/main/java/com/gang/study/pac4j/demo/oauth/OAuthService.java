package com.gang.study.pac4j.demo.oauth;

import com.gang.study.pac4j.demo.api.DefaultOAuthAPI;
import com.gang.study.pac4j.demo.base.BasePac4jService;
import com.github.scribejava.core.model.OAuth2AccessToken;
import org.checkerframework.checker.units.qual.C;
import org.pac4j.core.client.IndirectClient;
import org.pac4j.core.context.JEEContext;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.exception.http.FoundAction;
import org.pac4j.core.exception.http.RedirectionAction;
import org.pac4j.oauth.client.OAuth20Client;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.credentials.OAuth20Credentials;
import org.pac4j.oauth.profile.casoauthwrapper.CasOAuthWrapperProfileDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @Classname OAuthService
 * @Description TODO
 * @Date 2021/4/13
 * @Created by zengzg
 */
@Component
public class OAuthService extends BasePac4jService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String CLIENT_ID = "b7a8cc2a-5dec-4a78";
    private final static String CLIENT_SECRET = "b7a8cc2a-5dec-4a64-9331-7b467893ab78";
    private final static String SSO_URL = "";
    private final static String CALLBACK_URL = "http://127.0.0.1:9081/mfa-client/oauth/callback";

    /**
     * 执行 Authorization 请求
     *
     * @return
     */
    public String doOAuthRequest(HttpServletRequest request, HttpServletResponse response) {

        // Step 1 :构建请求 Client
        OAuth20Configuration config = new OAuth20Configuration();
        config.setApi(new DefaultOAuthAPI());
        config.setProfileDefinition(new CasOAuthWrapperProfileDefinition());
        config.setScope("user");
        config.setKey(CLIENT_ID);
        config.setSecret(CLIENT_SECRET);

        OAuth20Client client = new OAuth20Client();

        // 补充完善属性
        client.setConfiguration(config);
        client.setCallbackUrl(CALLBACK_URL);

        // 构建请求
        JEEContext context = new JEEContext(request, response);
        // 异步获取请求结果
        final String url = ((FoundAction) client.getRedirectionAction(context).get()).getLocation();
        logger.info("------> 请求 url 为 :{} <-------", url);

        Optional<OAuth20Credentials> credentials = ((IndirectClient) client).getCredentials(context);
        logger.info("------> 请求  credentials 为 :{} <-------", credentials.get());

        return "success";

    }
}
