package com.gang.azuread.web.demo.logic;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.ClientCredential;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import org.springframework.stereotype.Component;

import javax.naming.ServiceUnavailableException;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Classname TokenLogic
 * @Description TODO
 * @Date 2020/3/30 21:35
 * @Created by zengzg
 */
@Component
public class TokenLogic {

    private String clientId = "bdfcdc00-0177-4499-bd8d-98984d2506f7";
    private String clientSecret = "Cd_=2u9V2zV5/iQ?[bepTN57ze=hd[]?";
    private String tenant = "303370752qq.onmicrosoft.com";
    private String authority = "https://login.microsoftonline.com/";

    public AuthenticationResult getAccessTokenFromRefreshToken(
            String refreshToken) throws Throwable {
        AuthenticationContext context;
        AuthenticationResult result = null;
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(1);
            context = new AuthenticationContext(authority + tenant + "/", true,
                    service);
            Future<AuthenticationResult> future = context
                    .acquireTokenByRefreshToken(refreshToken, new ClientCredential(clientId, clientSecret), null, null);
            result = future.get();
        } catch (ExecutionException e) {
            throw e.getCause();
        } finally {
            service.shutdown();
        }

        if (result == null) {
            throw new ServiceUnavailableException("authentication result was null");
        }
        return result;
    }

    public AuthenticationResult getAccessToken(
            AuthorizationCode authorizationCode, String currentUri)
            throws Throwable {
        String authCode = authorizationCode.getValue();
        ClientCredential credential = new ClientCredential(clientId,
                clientSecret);
        AuthenticationContext context;
        AuthenticationResult result = null;
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(1);
            context = new AuthenticationContext(authority + tenant + "/", true,
                    service);
            Future<AuthenticationResult> future = context
                    .acquireTokenByAuthorizationCode(authCode, new URI(
                            currentUri), credential, null);
            result = future.get();
        } catch (ExecutionException e) {
            throw e.getCause();
        } finally {
            service.shutdown();
        }

        if (result == null) {
            throw new ServiceUnavailableException("authentication result was null");
        }
        return result;
    }

    private String getRedirectUrl(String currentUri, String state, String nonce)
            throws UnsupportedEncodingException {
        String redirectUrl = authority
                + this.tenant
                + "/oauth2/authorize?response_type=code&scope=directory.read.all&response_mode=form_post&redirect_uri="
                + URLEncoder.encode(currentUri, "UTF-8") + "&client_id="
                + clientId + "&resource=https%3a%2f%2fgraph.microsoft.com"
                + "&state=" + state
                + "&nonce=" + nonce;

        return redirectUrl;
    }

}
