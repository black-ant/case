package com.gang.study.sso.ltpa.demo.logic;

import com.gang.study.sso.ltpa.demo.entity.UserInfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname UserService
 * @Description TODO
 * @Date 2020/6/30 16:02
 * @Created by zengzg
 */
@Component
public class UserService {


    private static Map<String, UserInfo> map = new HashMap<>();

    static {
        UserInfo userInfoA = new UserInfo("admin", "Admin User", "1");
        UserInfo userInfoB = new UserInfo("root", "Root User", "1");
        map.put("admin", userInfoA);
        map.put("root", userInfoB);
    }

    /**
     * simulation user database find user
     *
     * @param userid
     * @return
     */
    public UserInfo getUserInfo(String userid) {
        return map.get(userid);
    }

}
