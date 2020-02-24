package com.gang.study.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @Classname JSONUtils
 * @Description TODO
 * @Date 2019/10/31 22:59
 * @Created by ant-black 1016930479@qq.com
 */
public final class JSONUtils {

    private JSONUtils() {
    }


    public static String getJSONParam(String key, String json) {
        return String.valueOf(JSONObject.parseObject(json).get(key));
    }

}
