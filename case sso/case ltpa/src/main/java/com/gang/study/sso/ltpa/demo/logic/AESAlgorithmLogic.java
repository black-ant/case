package com.gang.study.sso.ltpa.demo.logic;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.alibaba.fastjson.JSONObject;
import com.gang.study.sso.ltpa.demo.api.IAlgorithmLogic;
import com.gang.study.sso.ltpa.demo.entity.UserInfo;
import com.gang.study.sso.ltpa.demo.to.UserMetadata;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname AESAlgorithmLogic
 * @Description TODO
 * @Date 2020/7/1 16:33
 * @Created by zengzg
 */
public class AESAlgorithmLogic implements IAlgorithmLogic {
    @Override
    public String encode(UserInfo userInfo, Map<String, String> properties) {
        AES aes = SecureUtil.aes(properties.get("privateKey").getBytes());
        String token = aes.encryptBase64(JSONObject.toJSONString(userInfo));
        return token;
    }

    @Override
    public UserMetadata decode(String token, Map<String, String> properties) {
        AES aes = SecureUtil.aes(properties.get("privateKey").getBytes());
        String userStr = aes.decryptStr(token);
        UserInfo userInfo = JSONObject.parseObject(userStr, UserInfo.class);
        return new UserMetadata(userInfo.getUserName(), userStr);
    }

    @Override
    public Map<String, String> getLtpaProperties() {
        Map<String, String> aesMap = new HashMap<>();
        aesMap.put("privateKey", "s//C6cV3veYW9AgLSK6Y2g==");
        return aesMap;
    }
}
