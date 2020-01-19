package com.gang.study.dingtalk.demo.type;

/**
 * @Classname DingTalkAPIEnum
 * @Description TODO
 * @Date 2020/1/19 10:43
 * @Created by zengzg
 */
public enum DingTalkAPIEnum {

    /**
     * TOKEN 管理
     */
    TOKEN_API("token", "https://oapi.dingtalk.com/gettoken"),

    /**
     * USER 用户管理
     */
    USER_GET_API("USER_GET", "https://oapi.dingtalk.com/user/get"),
    USER_SEARCH("ORG_SEARCH", "https://oapi.dingtalk.com/user/simplelist"),
    USER_CREATE_API("USER_CREATE", "https://oapi.dingtalk.com/user/create"),
    USER_UPDATE_API("USER_UPDATE", "https://oapi.dingtalk.com/user/update"),
    USER_DELETE_API("USER_DELETE", "https://oapi.dingtalk.com/user/delete"),

    /**
     * ORG 组织管理
     */
    ORG_GET_API("ORG_GET", "https://oapi.dingtalk.com/department/get"),
    ORG_SEARCH("ORG_SEARCH", "https://oapi.dingtalk.com/department/list"),
    ORG_CREATE_API("ORG_CREATE", "https://oapi.dingtalk.com/department/create"),
    ORG_UPDATE_API("ORG_UPDATE", "https://oapi.dingtalk.com/department/update"),
    ORG_DELETE_API("ORG_DELETE", "https://oapi.dingtalk.com/department/delete");


    private String code;
    private String url;

    DingTalkAPIEnum(String code, String url) {
        this.code = code;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getCode() {
        return code;
    }
}
