package com.mymap.baiduapi.entity;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/28 20:18
 * @Version 1.0
 **/
public enum PushError {
    SUCCESS(200, "SUCCESS"),
    ERROR_PSUHTYPE(400, "PUSH类型错误"),
    ERROR_MISS(401, "参数缺失"),
    ERROR_NOGID(404,"GroupdID错误");

    private int psuhCode;
    private String msg;

    PushError(int psuhCode, String msg) {
        this.psuhCode = psuhCode;
        this.msg = msg;
    }

    public int getPsuhCode() {
        return psuhCode;
    }

    public void setPsuhCode(int psuhCode) {
        this.psuhCode = psuhCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
