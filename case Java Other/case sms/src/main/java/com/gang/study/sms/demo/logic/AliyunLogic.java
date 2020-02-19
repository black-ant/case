package com.gang.study.sms.demo.logic;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname AliyunLogic
 * @Description TODO
 * @Date 2020/2/16 18:45
 * @Created by zengzg
 */
@Component
public class AliyunLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JSONObject info = new JSONObject();

        // --> setting

        info.put("RegionId", "cn-shanghai");
        info.put("PhoneNumbers", "15927401369");
        info.put("accessToken", "1");
        info.put("accessSecret", "2");
        info.put("TemplateCode", "4");
        info.put("SignName", "3");


        // -> content
        JSONObject content = new JSONObject();
        content.put("code", "123456");

        info.put("TemplateParam", content);

        run(info);
    }


    public void run(JSONObject info) {


        DefaultProfile profile = DefaultProfile.getProfile(info.getString("RegionId"), info.getString("accessToken"),
                info.getString(
                        "accessSecret"));
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();

        request.setDomain("dysmsapi.aliyuncs.com");
        request.setAction("SendSms");
        request.setVersion("2017-05-25");
        request.putQueryParameter("PhoneNumbers", info.getString("PhoneNumbers"));
        request.putQueryParameter("RegionId", info.getString("RegionId"));
        request.putQueryParameter("SignName", info.getString("SignName"));
        request.putQueryParameter("TemplateCode", info.getString("TemplateCode"));
        request.putQueryParameter("TemplateParam", info.getJSONObject("TemplateParam").toJSONString());

        //        request.putQueryParameter("SignName", msgSetting.getAppId());
        //        request.putQueryParameter("TemplateCode", msgSetting.getTemplateKey());
        //        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(requestParameter));

        try {
            CommonResponse response = client.getCommonResponse(request);
            Map<?, ?> result = JSONObject.parseObject(response.getData(), Map.class);
            if (!"OK".equals(result.get("Code"))) {
                logger.info("------> :{} <-------", JSONObject.toJSONString(result));
            }
        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass() + e.getMessage(), e);
        }
    }


}
