package com.gang.azure.bundles.gangazurebundles.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @Classname UrlTemplateUtils
 * @Description TODO
 * @Date 2020/3/31 21:02
 * @Created by zengzg
 */
public class UrlTemplateUtils {

    public static String getUrl(String path, JSONObject jsonObject) {
        Map<String, Object> requestMao = JSONObject.parseObject(jsonObject.toJSONString(), Map.class);
        return JexlUtils.evaluate(path, requestMao);
    }
}
