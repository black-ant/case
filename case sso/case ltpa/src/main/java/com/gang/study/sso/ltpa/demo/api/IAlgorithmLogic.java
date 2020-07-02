package com.gang.study.sso.ltpa.demo.api;

import com.gang.study.sso.ltpa.demo.entity.UserInfo;
import com.gang.study.sso.ltpa.demo.to.UserMetadata;

import java.util.Map;

/**
 * @Classname IAlgorithmLogic
 * @Description TODO
 * @Date 2020/7/1 16:26
 * @Created by zengzg
 */
public interface IAlgorithmLogic {

    String encode(UserInfo userInfo, Map<String, String> properties);

    UserMetadata decode(String token, Map<String, String> properties);

    Map<String, String> getLtpaProperties();
}
