package com.gang.study.pac4j.demo.saml;

import com.gang.study.pac4j.demo.base.BasePac4jService;
import org.opensaml.saml.common.xml.SAMLConstants;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.http.callback.NoParameterCallbackUrlResolver;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.redirect.RedirectAction;
import org.pac4j.saml.client.SAML2Client;
import org.pac4j.saml.config.SAML2Configuration;
import org.pac4j.saml.credentials.SAML2Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname OAuthService
 * @Description TODO
 * @Date 2021/4/13
 * @Created by zengzg
 */
@Component
public class SAMLService extends BasePac4jService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String SAML_KEYSTORE_PWD = "saml-passwd";
    private final static String SAML_KEYSTORE_PATH = "saml-passwd.jks";
    private final static String SAML_SP_PATH = "sp-metadata-default.xml";
    private final static String CALLBACK_URL = "http://127.0.0.1:8088/saml/callback";

    /**
     * 执行 Authorization 请求
     *
     * @return
     */
    public void doOAuthRequest(HttpServletRequest request, HttpServletResponse response) {

        // Step 1 : 准备配置文件
        final SAML2Configuration configuration = new SAML2Configuration();
        configuration.setForceKeystoreGeneration(true);
        configuration.setKeystorePassword(SAML_KEYSTORE_PWD);
        configuration.setPrivateKeyPassword(SAML_KEYSTORE_PWD);
        configuration.setKeystorePath(SAML_KEYSTORE_PATH);

        // 加载 IDP Metadata
        configuration.setIdentityProviderMetadataResourceUrl("http://127.0.0.1:8088/idp-metadata-default.xml");

        // 加载 SP Metadata
        configuration.setServiceProviderMetadataResource(new FileSystemResource(SAML_SP_PATH));
        configuration.setForceServiceProviderMetadataGeneration(true);
        configuration.setMaximumAuthenticationLifetime(3600);

        // 配置 Bind 类型
        configuration.setAuthnRequestBindingType(SAMLConstants.SAML2_REDIRECT_BINDING_URI);

        // 配置初始化
        configuration.init();

        // Step 2 : 构建 Client
        final SAML2Client client = new SAML2Client(configuration);
        client.setCallbackUrl(CALLBACK_URL);
        client.setCallbackUrlResolver(new NoParameterCallbackUrlResolver());
        client.init();

        // Step 3 : 发起请求
        J2EContext context = new J2EContext(request, response);
        RedirectAction action = client.getRedirectAction(context);
        action.perform(context);

        // Step 4 : 补充操作 ,存入session
        request.getSession().setAttribute("client", client);
        request.getSession().setAttribute("context", context);
    }


}
