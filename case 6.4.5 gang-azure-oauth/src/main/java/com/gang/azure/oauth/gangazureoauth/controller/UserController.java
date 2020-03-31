package com.gang.azure.oauth.gangazureoauth.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.azure.oauth.gangazureoauth.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2020/3/31 14:55
 * @Created by zengzg
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CodeOauthController codeOauthController;

    @GetMapping("getUser/{key}")
    public String userinfo(@PathVariable("key") String userkey) {
        logger.info("------> this is  getUser :{}<-------", userkey);
        //        String code = codeOauthController.clientSecret();
        //        JSONObject jsonObject = JSONObject.parseObject(code);
        //        String token = jsonObject.getString("access_token");
        String token =
                "eyJ0eXAiOiJKV1QiLCJub25jZSI6IjFZUUQ4Q2I4X1M4Y3BlVDJSNGE0ZVpsbkhTdmZlNmkxd1ozQzl4bW5Ic00iLCJhbGciOiJS" +
                        "UzI1NiIsIng1dCI6IllNRUxIVDBndmIwbXhvU0RvWWZvbWpxZmpZVSIsImtpZCI6IllNRUxIVDBndmIwbXhvU0RvWWZvbW" +
                        "pxZmpZVSJ9" +
                        ".eyJhdWQiOiJodHRwczovL2dyYXBoLm1pY3Jvc29mdC5jb20iLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3" +
                        "dzLm5ldC8xODRlNThmOC03YTUyLTQyMWUtODAxZC00OTQ3YmI0NjExYTgvIiwiaWF0IjoxNTg1NjQxMzgyLCJuYmYiOjE1" +
                        "ODU2NDEzODIsImV4cCI6MTU4NTY0NTI4MiwiYWNjdCI6MCwiYWNyIjoiMSIsImFpbyI6IkFUUUF5LzhQQUFBQVZaSGlCbE" +
                        "tZYTE4U0pZcjlraGgxOWRUai9rUGdNRVZ4TzlPM0xaS2FkYlN6T2hBT1R1b013VG1VL1dxcFRmZVgiLCJhbHRzZWNpZCI6" +
                        "IjE6bGl2ZS5jb206MDAwMzQwMDEwNjkxRDBEMyIsImFtciI6WyJwd2QiXSwiYXBwX2Rpc3BsYXluYW1lIjoiVEVTVC1XRU" +
                        "IiLCJhcHBpZCI6ImJkZmNkYzAwLTAxNzctNDQ5OS1iZDhkLTk4OTg0ZDI1MDZmNyIsImFwcGlkYWNyIjoiMSIsImVtYWls" +
                        "IjoiMzAzMzcwNzUyQHFxLmNvbSIsImZhbWlseV9uYW1lIjoiemhpZ2FuZyIsImdpdmVuX25hbWUiOiJ6ZW5nIiwiaWRwIj" +
                        "oibGl2ZS5jb20iLCJpcGFkZHIiOiIxODMuOTUuNjYuMTE3IiwibmFtZSI6InpoaWdhbmcgemVuZyIsIm9pZCI6IjJmZjZm" +
                        "OTc3LTMzMjAtNDZkZC04MmQzLTE0MThjZTIwY2I2YyIsInBsYXRmIjoiMyIsInB1aWQiOiIxMDAzMjAwMDg5MkZGNTg0Ii" +
                        "wic2NwIjoiRGlyZWN0b3J5LkFjY2Vzc0FzVXNlci5BbGwgRGlyZWN0b3J5LlJlYWQuQWxsIERpcmVjdG9yeS5SZWFkV3J" +
                        "pdGUuQWxsIEdyb3VwLlJlYWQuQWxsIEdyb3VwLlJlYWRXcml0ZS5BbGwgTWVtYmVyLlJlYWQuSGlkZGVuIG9wZW5pZCBQ" +
                        "b2xpY3kuUmVhZC5BbGwgVXNlci5SZWFkIFVzZXIuUmVhZC5BbGwgVXNlci5SZWFkQmFzaWMuQWxsIiwic3ViIjoiM1lra" +
                        "nJhLWo1UVJMaUE0ekM4amw1RHRMYmdjU0JaMUxIbXQ0bnVkOGQ5MCIsInRpZCI6IjE4NGU1OGY4LTdhNTItNDIxZS04MD" +
                        "FkLTQ5NDdiYjQ2MTFhOCIsInVuaXF1ZV9uYW1lIjoibGl2ZS5jb20jMzAzMzcwNzUyQHFxLmNvbSIsInV0aSI6IjJwdWZ" +
                        "1WWZ4M0VpWnUwWU5Fb2tLQUEiLCJ2ZXIiOiIxLjAiLCJ3aWRzIjpbIjYyZTkwMzk0LTY5ZjUtNDIzNy05MTkwLTAxMjE3" +
                        "NzE0NWUxMCJdLCJ4bXNfdGNkdCI6MTU3NDI0MDc0NH0.iYGhRy9hcygi41s0SQyrtSxtk1Pr8he_1BXMpgG4Hx28bqXc" +
                        "QBk2p_1SEDBF6QTRQHCVTNULNknD1dUPMDh2fBGLFBH77V2OwPo6cILWT3ZngV4ssL0UWitR5j1ZUzOZIg2B5RpZ6BW" +
                        "t_Xu5zv4q4DX8qIuoo-UE-9HEU4_nmPNE30jxH24L2ESkreGZdFTu7bsqIDxjrn2HdqnvMXhI_gXXi0gZTKTU-Zw6pW" +
                        "IJbuFp_qZg0q5k5oczw0xivoIvGk9xSAoj4idhyX2JrEI8cL8wE7ZYdxkUELpIdUI1BPoAnaYKCSvEomHhk-lRc1zxV" +
                        "u0EqQetiwv6Jqtnx0LFIg";


        logger.info("------> this token is :{} <-------", token);
        String getPath = "https://graph.windows.net/303370752qq.onmicrosoft.com/users?api-version=1.6";

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer " + token);

        String response = HttpClientUtils.doGet(getPath, headerMap);
        return response;

    }
}
