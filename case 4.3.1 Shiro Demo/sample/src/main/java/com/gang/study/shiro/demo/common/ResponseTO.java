package com.gang.study.shiro.demo.common;

import java.util.Map;

/**
 * @Classname ResponseTO
 * @Description TODO
 * @Date 2021/3/7 10:26
 * @Created by zengzg
 */
public class ResponseTO {

    private String code;

    private String msg;

    private Map<String, String> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
