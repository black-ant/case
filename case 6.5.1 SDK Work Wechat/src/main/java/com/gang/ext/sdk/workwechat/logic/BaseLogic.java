package com.gang.ext.sdk.workwechat.logic;


import com.gang.ext.sdk.workwechat.type.WorkWechatAPI;
import com.gang.ext.sdk.workwechat.utils.HttpClientUtils;
import com.gang.ext.sdk.workwechat.utils.JSONUtils;
import com.gang.ext.sdk.workwechat.utils.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @Classname BaseLogic
 * @Description TODO
 * @Date 2019/12/25 22:43
 * @Created by zengzg
 */
@Component
public class BaseLogic {

    public String getToken(String config) {
        String backInfo =
                HttpClientUtils.doGet(StringUtils.replaceTemplateString(WorkWechatAPI.API_TOKEN.getRestAPI(), config));
        return JSONUtils.getJSONParam("access_token", backInfo);
    }


}
