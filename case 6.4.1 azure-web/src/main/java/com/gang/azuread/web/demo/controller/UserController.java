package com.gang.azuread.web.demo.controller;

import com.gang.azuread.web.demo.entity.User;
import com.gang.azuread.web.demo.logic.AuthHelper;
import com.gang.azuread.web.demo.utils.HttpClientHelper;
import com.gang.azuread.web.demo.utils.JSONHelper;
import com.microsoft.aad.adal4j.AuthenticationResult;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2020/3/31 17:35
 * @Created by zengzg
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping("get")
    public String get(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        AuthenticationResult result = (AuthenticationResult) session.getAttribute(AuthHelper.PRINCIPAL_SESSION_NAME);
        String backInfo = "no";
        if (result != null) {
            try {
                String tenant = session.getServletContext().getInitParameter("tenant");
                String token = result.getAccessToken();
                logger.info("------> this token is :{} <-------", token);
                backInfo = getUsernamesFromGraph(token, tenant);
            } catch (Exception e) {
                logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            }
        }
        return backInfo;
    }

    public String getUsernamesFromGraph(String accessToken, String tenant) throws Exception {
        URL url = new URL("https://graph.windows.net/303370752qq.onmicrosoft.com/users/TEST@303370752qq.onmicrosoft" +
                ".com?api-version=1.6");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Accept", "application/json");

        String goodRespStr = HttpClientHelper.getResponseStringFromConn(conn, true);
        logger.info("goodRespStr ->" + goodRespStr);
        int responseCode = conn.getResponseCode();
        JSONObject response = HttpClientHelper.processGoodRespStr(responseCode, goodRespStr);

        return response.toString();
    }
}
