package com.gang.study.sso.ltpa.demo.utils;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname HutoolsUtils
 * @Description TODO
 * @Date 2020/7/1 9:51
 * @Created by zengzg
 */
public class HutoolsUtils {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * MD5 加密
     *
     * @param data
     */
    public void md5(String data) {
        logger.info("------> md5 :{} <-------", SecureUtil.md5().digest(data));
    }

    /**
     * 生成 对称key
     */
    public void generateKey() {
        logger.info("------> generateKey AES :{} <-------", JSONObject.toJSONString(SecureUtil.generateKey("AES")));
        logger.info("------> generateKey DES :{} <-------", JSONObject.toJSONString(SecureUtil.generateKey("DES")));
        logger.info("------> generateKey DESede :{} <-------", JSONObject.toJSONString(SecureUtil.generateKey("DESede")));
        logger.info("------> generateKey RC2 :{} <-------", JSONObject.toJSONString(SecureUtil.generateKey("RC2")));
        logger.info("------> generateKey RC4 :{} <-------", JSONObject.toJSONString(SecureUtil.generateKey("RC4")));
    }
}
