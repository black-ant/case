package com.gang.study.pac4j.demo.oauth;

import com.gang.study.pac4j.demo.api.BasePac4jService;
import com.github.scribejava.apis.GitHubApi;
import org.pac4j.core.context.J2EContext;
import org.pac4j.oauth.client.CasOAuthWrapperClient;
import org.pac4j.oauth.client.OAuth10Client;
import org.pac4j.oauth.client.OAuth20Client;
import org.pac4j.oauth.config.OAuth10Configuration;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.profile.bitbucket.BitbucketProfileDefinition;
import org.pac4j.oauth.profile.casoauthwrapper.CasOAuthWrapperProfileDefinition;
import org.pac4j.oauth.profile.github.GitHubProfileDefinition;
import org.pac4j.scribe.builder.api.BitBucketApi;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname OAuthService
 * @Description TODO
 * @Date 2021/4/13
 * @Created by zengzg
 */
@Component
public class OAuthService extends BasePac4jService {

    private final static String CLIENT_ID = "";
    private final static String CLIENT_SECRET = "";
    private final static String SSO_URL = "";
    private final static String CALLBACK_URL = "";

    /**
     * 执行 Authorization 请求
     *
     * @return
     */
    public String doOAuthRequest() {

        // Step 1 :构建请求 Client
        OAuth20Configuration config = new OAuth20Configuration();
        config.setApi(GitHubApi.instance());
        config.setProfileDefinition(new CasOAuthWrapperProfileDefinition());
        config.setScope("user");
        config.setKey(CLIENT_ID);
        config.setSecret(CLIENT_SECRET);

        OAuth20Client client = new OAuth20Client();

        // 补充完善属性
        client.setConfiguration(config);
        client.setCallbackUrl(CALLBACK_URL);


        client.redirect()


    }
}
