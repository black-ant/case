package com.search.elastic.controller;

import com.alibaba.fastjson.JSONObject;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/13 17:30
 * @Version 1.0
 **/
public abstract class BaseController {

    protected <T> String apiResponse(T result) {
        return JSONObject.toJSONString(result);
    }
}
