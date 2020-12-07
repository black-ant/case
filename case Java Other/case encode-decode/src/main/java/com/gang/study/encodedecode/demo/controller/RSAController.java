package com.gang.study.encodedecode.demo.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.PrivateKey;

/**
 * @Classname RSAController
 * @Description TODO
 * @Date 2020/12/1 11:14
 * @Created by zengzg
 */
@RequestMapping("/rsa")
@RestController
public class RSAController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static PrivateKey PRIVATE_KEY = null;
    private static RSA rsa = new RSA();

    @GetMapping("/getPublicKey")
    public String getPublicKey() {
        logger.info("------> this is in get <-------");

        logger.info("------> this is in get public key:{} <-------", rsa.getPublicKeyBase64());
        PRIVATE_KEY = rsa.getPrivateKey();
        logger.info("------> this is in get private key:{} <-------", rsa.getPrivateKeyBase64());
        return rsa.getPublicKeyBase64();
    }

    @PostMapping("/check")
    public String check(@RequestBody String key) {
        logger.info("------> this is in get :{}<-------", key);
        String pwd = rsa.decryptStr(key, KeyType.PrivateKey);
        logger.info("------> this is in pwd :{}<-------", pwd);
        return pwd;
    }

    /**
     * 已知情况下解密
     *
     * @return
     */
    @PostMapping("/check2")
    public String ckeck2(@RequestBody String key) {

        logger.info("------> this is key :{} <-------", key);
        String PUBLIC_KEY =
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCBxzZjSziaFA2GLVprS39eW8RYIsbWaNYtt0KbXwzaEo29kmBx" +
                        "+snuRxvMdngOM007u0eoNc2XHUcEMr1qoH2dC9hEnMcypXoKyER4Gqm7eeDeTHMOJwPlDfK1cIhb0D40BcpwNW1Qs9AWM/5sc87hCb4NK/lC2vjZ5o7Q7kn/qwIDAQAB";
        String PRIVATE_KEY =
                "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIHHNmNLOJoUDYYtWmtLf15bxFgixtZo1i23QptfDNoSjb2SYHH6ye5HG8x2eA4zTTu7R6g1zZcdRwQyvWqgfZ0L2EScxzKlegrIRHgaqbt54N5Mcw4nA+UN8rVwiFvQPjQFynA1bVCz0BYz/mxzzuEJvg0r+ULa+NnmjtDuSf+rAgMBAAECgYAObY1YYKZ8SrpuLmCdCRmx7dXYTpV4hNM65eLbzydonyNu7fUW9uC4QuOOSaVzPrTZbSq8Qq8Gm9SnXNS+I/WS9ZAziTrMtrQXfM1aljhuqIJ8sJ2oqkrpLH4gD/FIh+imvyYzmqDq8f4xG1VGOID27LjN/fszkH2tTL1b99dvAQJBALvSxCs22R/bMpYzz0w796hSP/pNMwfsZfZpa8gLjk56ZasEZcGNpy2Zrr/lf+gsGNY2f7nhyMWuUFmoGBRdfusCQQCw4q239uDQAo2tFNOv5SAxXha6WT/2j1QbSQozTBZ6NEnCQCWMYVQcsdU8E89R+KyYcwIRtQrIlMBrAdJ1UNJBAkBAs2pAOLSy9J+IfnB6om293heL8ewSXq0BJW+ric+L2T4Mm4cyFcnxO2+nSze0kvw7TqrRLaYr8tBbEjy5RzvhAkAzUbDg/MmA0BzWqII6OBm8+PVTH23FVRQrzmcdNugWkHEN4JqEPgCvSpD5Gy49NA6vND7XcFUumKmy4hZzXv2BAkByokCaWoRLXzYjrYxudVBm+dIrUyGfpbVLbrl0vUTzkU17094J27GO7BrQXSGndQiSdtTNK/wz2NcY7mMKTV4b";
        RSA rsa = new RSA(PRIVATE_KEY, null);

//        byte[] aByte = HexUtil.decodeHex(key);
        byte[] decrypt = rsa.decrypt(key, KeyType.PrivateKey);
        logger.info("------> this is log :{} <-------", StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
        return StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8);
    }

}
