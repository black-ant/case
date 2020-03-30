package com.gang.adzure.comgangcaseadzure.service;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Classname TokenService
 * @Description TODO
 * @Date 2020/3/30 14:35
 * @Created by zengzg
 */
@Service
public class TokenService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String AUTHORITY = "https://login.microsoftonline.com/common/";

    @Value("${azure.activedirectory.client-id}")
    private String CLIENT_ID;

    /**
     * @param username
     * @param password
     * @return
     */
    public String getToken(String username, String password) throws Exception {

        logger.info("------> this username :{} -- password :{} -- clientid :{} <-------", username, password,
                CLIENT_ID);

        AuthenticationResult result = getAccessTokenFromUserCredentials(
                username, password);
        String userInfo = TokenService.getUserInfoFromGraph(result.getAccessToken());

        logger.info("------> this is userinfo :{} <-------", userInfo);
        return userInfo;
    }

    private AuthenticationResult getAccessTokenFromUserCredentials(
            String username, String password) throws Exception {
        AuthenticationContext context;
        AuthenticationResult result;
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(1);
            context = new AuthenticationContext(AUTHORITY, false, service);
            Future<AuthenticationResult> future = context.acquireToken(
                    "https://graph.microsoft.com", CLIENT_ID, username, password,
                    null);
            result = future.get();
        } finally {
            service.shutdown();
        }

        if (result == null) {
            throw new ServiceUnavailableException(
                    "authentication result was null");
        }
        return result;
    }

    private static String getUserInfoFromGraph(String accessToken) throws IOException {

        URL url = new URL("https://graph.microsoft.com/v1.0/me");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Accept", "application/json");

        int httpResponseCode = conn.getResponseCode();
        if (httpResponseCode == 200) {
            BufferedReader in = null;
            StringBuilder response;
            try {
                in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            } finally {
                in.close();
            }
            return response.toString();
        } else {
            return String.format("Connection returned HTTP code: %s with message: %s",
                    httpResponseCode, conn.getResponseMessage());
        }
    }
}
