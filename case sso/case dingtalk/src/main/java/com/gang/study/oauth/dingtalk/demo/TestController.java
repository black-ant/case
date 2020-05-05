package com.gang.study.oauth.dingtalk.demo;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import sun.net.www.http.HttpClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2020/5/1 20:05
 * @Created by zengzg
 */
@Component
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void run(String code) throws Exception {
        logger.info("------> run start <-------");

        // 根据timestamp, appSecret计算签名值
        String tiemstamp = String.valueOf(getTimestamp(new Date()));

        logger.info("------> time is :{} <-------", tiemstamp);
        String appSecret = "j5LbpQihEDnBQ7BS-wtGFmYtO26GKkW0UiNV1iS_SkBjfxXEMhu_ERFERXd2gpTi";

        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(appSecret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signatureBytes = mac.doFinal(tiemstamp.getBytes("UTF-8"));
        String signature = new String(Base64Utils.encode(signatureBytes));
        String urlEncodeSignature = urlEncode(signature, "UTF-8");
        logger.info("------> urlEncodeSignature is :{} <-------", urlEncodeSignature);

        String url = "https://oapi.dingtalk.com/sns/getuserinfo_bycode?accessKey=dingoams2oq4puhmcv6n6u&timestamp" +
                "=" + tiemstamp + "&signature=" + urlEncodeSignature;

        JSONObject object = new JSONObject();
        object.put("tmp_auth_code", code);

        String back = HttpClientUtils.sendHttpPost(url, object.toJSONString());
        logger.info("------> back is :{} <-------", back);

    }

    /**
     * 获取毫秒时间戳
     *
     * @param date
     * @return
     */
    public static Long getTimestamp(Date date) {
        if (null == date) {
            return (long) 0;
        }
        String timestamp = String.valueOf(date.getTime());
        return Long.valueOf(timestamp);
    }


    // encoding参数使用utf-8
    public static String urlEncode(String value, String encoding) {
        if (value == null) {
            return "";
        }
        try {
            String encoded = URLEncoder.encode(value, encoding);
            return encoded.replace("+", "%20").replace("*", "%2A")
                    .replace("~", "%7E").replace("/", "%2F");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("FailedToEncodeUri", e);
        }
    }

}
