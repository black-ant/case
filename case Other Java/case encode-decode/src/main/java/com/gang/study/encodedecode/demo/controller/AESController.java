package com.gang.study.encodedecode.demo.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.security.PrivateKey;

/**
 * @Classname RSAController
 * @Description TODO
 * @Date 2020/12/1 11:14
 * @Created by zengzg
 */
@RequestMapping("/aes")
@RestController
public class AESController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static PrivateKey PRIVATE_KEY = null;
    private static RSA rsa = new RSA();

    @GetMapping("/getPublicKey")
    public String getPublicKey() {
        logger.info("------> this is in get <-------");
        byte[] publicKey = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        String PUBLIC_KEY = Base64Utils.encodeToString(publicKey);
        logger.info("------> this is in get public key:{} <-------", PUBLIC_KEY);
        return PUBLIC_KEY;
    }

    /**
     * 已知情况下解密
     *
     * @return
     */
    @GetMapping("/check2")
    public String ckeck2() {
        byte[] publicKey = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        String PUBLIC_KEY = Base64Utils.encodeToString(publicKey);
        logger.info("------> this is aes PUBLIC_KEY :{} <-------", PUBLIC_KEY);

        AES aes = new AES(PUBLIC_KEY.getBytes());
        String key = aes.encryptBase64("123456");
        logger.info("------> this is aes encrypt key :{} <-------", key);

        String passwordNew = aes.decryptStr(key);
        logger.info("------> this is aes decrypt :{} <-------", passwordNew);
        return passwordNew;
    }

    @GetMapping("/check3")
    public String ckeck3() {
        String PUBLIC_KEY = "tC957JDeA92yqQsGhFSlKA==";
        logger.info("------> this is aes PUBLIC_KEY :{} <-------", PUBLIC_KEY);

        AES aes = new AES(PUBLIC_KEY.getBytes());
        String key = aes.encryptBase64("123456");
        logger.info("------> this is aes encrypt key :{} <-------", key);

        String passwordNew = aes.decryptStr(key);
        logger.info("------> this is aes decrypt :{} <-------", passwordNew);
        return passwordNew;
    }

}
