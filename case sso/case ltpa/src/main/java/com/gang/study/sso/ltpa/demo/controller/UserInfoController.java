package com.gang.study.sso.ltpa.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.sso.ltpa.demo.logic.LtpaAuthLogic;
import com.gang.study.sso.ltpa.demo.to.UserMetadata;
import com.gang.study.sso.ltpa.demo.type.Algorithm;
import com.gang.study.sso.ltpa.demo.utils.CookieUtils;
import com.gang.study.sso.ltpa.demo.utils.LtpaTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname UserInfoController
 * @Description TODO
 * @Date 2020/7/1 16:15
 * @Created by zengzg
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LtpaAuthLogic ltpaAuthLogic;

    @GetMapping(value = "/getUserInfo", produces = "application/json")
    public ResponseEntity<UserMetadata> getUser(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity(ltpaAuthLogic.doCheck(request), HttpStatus.OK);
    }


    @GetMapping(value = "/profile", produces = "application/json")
    public ResponseEntity<String> handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserMetadata userMetadata = null;
        try {
            userMetadata = getTokenFromRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
        }

        JSONObject userResponse = new JSONObject();
        if (userMetadata == null) {
            logger.info("------> this user metadata is empty <-------");
        } else {
            userResponse.put("username", userMetadata.getUsername());
        }

        return new ResponseEntity(userResponse.toJSONString(), HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param request
     * @return
     */
    public static UserMetadata getTokenFromRequest(final HttpServletRequest request) throws Exception {
        Cookie tokenCookie = CookieUtils.getCookieByName(request, "ssoLoginToken");
        if (tokenCookie != null) {
            String tokenValue = tokenCookie.getValue();
            UserMetadata meta = LtpaTokenUtils.decode(tokenValue, LtpaTokenUtils.getLtpaProperties());
            return meta;
        }
        return null;
    }
}
