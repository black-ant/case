package com.gang.azure.bundles.gangazurebundles.logic;

import com.alibaba.fastjson.JSONObject;
import com.gang.azure.bundles.gangazurebundles.entity.AzureClientTokenTO;
import com.gang.azure.bundles.gangazurebundles.utils.HttpClientUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname TokenLogic
 * @Description TODO
 * @Date 2020/3/31 19:58
 * @Created by zengzg
 */
@Component
public class TokenLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static String tokenCache = null;

    public Map<String, String> commonHeader(AzureClientTokenTO clientTokenTO) {
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        map.put("Authorization", getToken(clientTokenTO));
        return map;
    }

    public String getToken(AzureClientTokenTO clientTokenTO) {
        String oauthUrl = "https://login.microsoftonline.com/303370752qq.onmicrosoft.com/oauth2/v2.0/token";

        Map<String, String> requestMao = JSONObject.parseObject(JSONObject.toJSONString(clientTokenTO), Map.class);
        if (tokenCache != null) {
            return tokenCache;
        }
        String backInfo = null;
        try {
            backInfo = HttpClientUtils.doPost(oauthUrl, requestMao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String token = null;
        if (StringUtils.isNotBlank(backInfo)) {
            JSONObject backJSON = JSONObject.parseObject(backInfo);
            token = backJSON.getString("access_token");
        }
        setTokenCache(token);
        return token;
    }

    public static String getTokenCache() {
        return tokenCache;
    }

    public static void setTokenCache(String tokenCache) {
        TokenLogic.tokenCache = tokenCache;
    }
}
