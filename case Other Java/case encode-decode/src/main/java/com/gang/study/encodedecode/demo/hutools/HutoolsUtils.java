package com.gang.study.encodedecode.demo.hutools;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.DESede;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.DESKeySpec;
import java.security.spec.KeySpec;

/**
 * @Classname HutoolsUtils
 * @Description SecureUtil
 * @Date 2020/6/30 21:10
 * @Created by zengzg
 */
public class HutoolsUtils {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String ENCODE_DATA = "123456789";


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

    /**
     * 生成指定长度Key
     */
    public void generateKeyHavaLength() {
        logger.info("------> generateKey AES :{} <-------", JSONObject.toJSONString(SecureUtil.generateKey("AES", 128)));
        logger.info("------> generateKey DES :{} <-------", JSONObject.toJSONString(SecureUtil.generateKey("DES", 56)));
        logger.info("------> generateKey DESede :{} <-------", JSONObject.toJSONString(SecureUtil.generateKey("DESede", 168)));
        logger.info("------> generateKey RC2 :{} <-------", JSONObject.toJSONString(SecureUtil.generateKey("RC2", 128)));
        logger.info("------> generateKey RC4 :{} <-------", JSONObject.toJSONString(SecureUtil.generateKey("RC4", 128)));
    }

    /**
     * 通过 KeySpec 生成
     */
    public void generateKeyHavaKeySpec() {
        logger.info("------> generateKey DES :{} <-------", JSONObject.toJSONString(SecureUtil.generatePrivateKey("DSA", createPrivateByte())));
    }

    public byte[] createPrivateByte() {
        return "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANp/sfjCkQX+4CV/FpbU1tYoyjP0HElmQOAivtbR1LRcffLcV+xJtf7S6pPhBnBkFvS1U7yyA7QgzEvA8k+Ei1kCAwEAAQ".getBytes();
    }

    public void generatePublicKey() {
        logger.info("------> generateKey DES :{} <-------", JSONObject.toJSONString(SecureUtil.generateKeyPair("RSA", 512)));

        logger.info("------> generateKey RSA :{} <-------", JSONObject.toJSONString(SecureUtil.generatePublicKey("RSA", createPrivateByte())));
    }

    public void aesBuild() {

        SecretKey aesKey = SecureUtil.generateKey("AES");
        logger.info("------> this is aesKey :{} <-------", JSONObject.toJSONString(aesKey));

        AES aes = SecureUtil.aes(aesKey.getEncoded());
        String passwordNew = aes.encryptBase64(ENCODE_DATA);
        logger.info("------> this is aes encrypt :{} <-------", JSONObject.toJSONString(passwordNew));
        logger.info("------> this is aes decrypt :{} <-------", JSONObject.toJSONString(aes.decryptStr(passwordNew)));

    }

    public void desBuild() {

        SecretKey desKey = SecureUtil.generateKey("DES");
        logger.info("------> this is aesKey :{} <-------", JSONObject.toJSONString(desKey));

        DES des = SecureUtil.des(desKey.getEncoded());
        String passwordNew = des.encryptBase64(ENCODE_DATA);
        logger.info("------> this is aes encrypt :{} <-------", JSONObject.toJSONString(passwordNew));
        logger.info("------> this is aes decrypt :{} <-------", JSONObject.toJSONString(des.decryptStr(passwordNew)));

    }

    public void dESedeBuild() {

        SecretKey desKey = SecureUtil.generateKey("DESede");
        logger.info("------> this is aesKey :{} <-------", JSONObject.toJSONString(desKey));

        DESede des = SecureUtil.desede(desKey.getEncoded());
        String passwordNew = des.encryptBase64(ENCODE_DATA);
        logger.info("------> this is aes encrypt :{} <-------", JSONObject.toJSONString(passwordNew));
        logger.info("------> this is aes decrypt :{} <-------", JSONObject.toJSONString(des.decryptStr(passwordNew)));

    }

    public void sha() {
        String sha = SecureUtil.sha1(ENCODE_DATA);
        logger.info("------> this is aes encrypt :{} <-------", JSONObject.toJSONString(sha));
    }

    public void sha256() {
        String sha = SecureUtil.sha256(ENCODE_DATA);
        logger.info("------> this is aes encrypt :{} <-------", JSONObject.toJSONString(sha));
    }

    /**
     * MD5 加密
     *
     * @param data
     */
    public void md5(String data) {
        logger.info("------> md5 :{} <-------", SecureUtil.md5().digest(data));
    }
}
