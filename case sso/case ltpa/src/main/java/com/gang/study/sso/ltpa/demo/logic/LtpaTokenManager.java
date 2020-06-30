package com.gang.study.sso.ltpa.demo.logic;

import com.gang.study.sso.ltpa.demo.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname LtpaCheckService
 * @Description TODO
 * @Date 2020/6/30 16:01
 * @Created by zengzg
 */
@Component
public class LtpaTokenManager {

    @Autowired
    private UserService userService;

    public UserInfo checkToken(String token) {
        //        String userid = LtpaTokenUtils.

        return userService.getUserInfo("");
    }

}
