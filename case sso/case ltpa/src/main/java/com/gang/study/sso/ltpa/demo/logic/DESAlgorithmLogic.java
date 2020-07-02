package com.gang.study.sso.ltpa.demo.logic;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import com.alibaba.fastjson.JSONObject;
import com.gang.study.sso.ltpa.demo.api.IAlgorithmLogic;
import com.gang.study.sso.ltpa.demo.entity.UserInfo;
import com.gang.study.sso.ltpa.demo.to.UserMetadata;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname DESAlgorithmLogic
 * @Description TODO
 * @Date 2020/7/1 16:33
 * @Created by zengzg
 */
public class DESAlgorithmLogic implements IAlgorithmLogic {

    @Override
    public String encode(UserInfo userInfo, Map<String, String> properties) {
        DES des = SecureUtil.des(properties.get("privateKey").getBytes());
        String token = des.encryptBase64(JSONObject.toJSONString(userInfo));
        return token;
    }

    @Override
    public UserMetadata decode(String token, Map<String, String> properties) {
        DES des = SecureUtil.des(properties.get("privateKey").getBytes());
        String userStr = des.decryptStr(token);
        UserInfo userInfo = JSONObject.parseObject(userStr, UserInfo.class);
        return new UserMetadata(userInfo.getUserName(), userStr);
    }

    @Override
    public Map<String, String> getLtpaProperties() {
        Map<String, String> aesMap = new HashMap<>();
        aesMap.put("privateKey", "bZhDHKRFinY=");
        return aesMap;
    }
}
