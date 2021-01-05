package com.gang.ext.sdk.workwechat.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @Classname StringUtils
 * @Description TODO
 * @Date 2019/12/28 14:10
 * @Created by zengzg
 */
public final class StringUtils {

    private StringUtils() {
    }

    /**
     * @param templateBody
     * @param jsonObject
     * @return
     */
    public static String replaceTemplateString(String templateBody, String jsonObject) {
        return replaceTemplate(templateBody, JSONObject.parseObject(jsonObject));
    }

    /**
     * @param templateBody
     * @param jsonObject
     * @return
     */
    public static String replaceTemplate(String templateBody, JSONObject jsonObject) {
        return JexlUtils.evaluate(templateBody, jsonObject.getInnerMap());
    }


    /**
     * desc : 替换为template
     *
     * @param templateBody
     * @param map
     * @return
     */
    public String replaceTemplate(String templateBody, Map<String, Object> map) {
        return JexlUtils.evaluate(templateBody, map);
    }

    /**
     * replace json Type string to new String
     *
     * @param url
     * @param jsonString
     * @return
     */
    public static String replaceJSONObjectString(String url, String jsonString) {
        return replaceJSONObject(url, JSONObject.parseObject(jsonString));
    }

    /**
     * desc : 替换 JSONObject 中所有的属性
     *
     * @param url
     * @param params
     * @return
     */
    public static String replaceJSONObject(String url, JSONObject params) {

        String newUrl = url;
        for (String key : params.keySet()) {
            newUrl = org.apache.commons.lang3.StringUtils.replace(newUrl, key, params.getString(key));
        }
        return newUrl;
    }
}
