package com.gang.study.websocket.module;

/**
 * @Classname MsgError
 * @Description TODO
 * @Date 2019/12/3 10:40
 * @Created by zengzg
 */
public enum MsgError {

    SUCCESS(0, "SUCCESS"),
    PARAMETERS_MISSING(1, "参数不全"),
    UNSUPPORTED_MSG_EVENT(2, "不支持的消息事件类型"),
    UNREGISTERED_GID(3, "未注册过的GID");

    private int errorCode;
    private String desc;

    MsgError(int errorCode, String desc) {
        this.errorCode = errorCode;
        this.desc = desc;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
