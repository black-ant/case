package com.gang.study.sso.ltpa.demo.utils;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.sso.ltpa.demo.entity.UserInfo;
import com.gang.study.sso.ltpa.demo.to.LTPAPrivateKey;
import com.gang.study.sso.ltpa.demo.to.UserMetadata;
import com.gang.study.sso.ltpa.demo.type.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Classname LtpaTokenUtils
 * @Description TODO
 * @Date 2020/6/30 16:07
 * @Created by zengzg
 */
public class LtpaTokenUtils {

    private static Logger LOG = LoggerFactory.getLogger(LtpaTokenUtils.class);

    /**
     * Builder ltpa token
     *
     * @return
     * @throws Exception
     */
    public static String encode(UserInfo userInfo, Map<String, String> properties) throws Exception {

        LTPAPrivateKey ltpaPrivKey = new LTPAPrivateKey(CryptUtils.desDecode(properties.get("PrivateKey"), properties.get("KeyPassword")));
        byte[][] rawKey = ltpaPrivKey.getRawKey();

        Long expire = (System.currentTimeMillis() + Integer.valueOf(properties.get("expiredTime")) * 60L * 1000L + 60000L) / 60000L * 60000L;


        String userMetadata = createUserMeteDate(userInfo);
        // new lets prepare to prepare the signature
        MessageDigest md1JCE = MessageDigest.getInstance("SHA");
        byte[] plainUserDataBytes = md1JCE.digest(userMetadata.getBytes());

        // lets sign the hash created previously with the private key
        byte[] encodedSignatureBytes = null;
        if (Algorithm.AES.equals(properties.get("cryptType"))) {
            BigInteger biginteger = new BigInteger(rawKey[0]);
            BigInteger biginteger1 = new BigInteger(rawKey[2]);
            BigInteger biginteger2 = new BigInteger(rawKey[3]);
            BigInteger biginteger3 = new BigInteger(rawKey[4]);
            BigInteger biginteger4 =
                    biginteger1.modInverse(biginteger2.subtract(BigInteger.ONE).multiply(biginteger3.subtract(BigInteger.ONE)));
            KeyFactory keyfactory = KeyFactory.getInstance("RSA");

            RSAPrivateKeySpec rsaprivatekeyspec = new RSAPrivateKeySpec(biginteger, biginteger4);
            PrivateKey privatekey = keyfactory.generatePrivate(rsaprivatekeyspec);
            Signature signer = Signature.getInstance("SHA1withRSA");
            signer.initSign(privatekey);

            signer.update(plainUserDataBytes, 0, plainUserDataBytes.length);

            // signing the hash
            encodedSignatureBytes = signer.sign();
        } else {
            // signing the hash
            encodedSignatureBytes = RSAUtils.rsa(rawKey, plainUserDataBytes);
        } // else

        // ok. lets encode the signature with Base64
        LOG.debug("length : " + encodedSignatureBytes.length);
        String base64Signature = Base64Utils.encodeToString(encodedSignatureBytes);

        // now, lets create the plain text version of the token
        StringBuffer token = new StringBuffer();
        token.append(userMetadata).append("%");
        token.append(expire).append("%");
        token.append(base64Signature);

        // finally lets crypt everything with the private key and then
        // to apply a base64 encoding
        byte[] tokenBytes = token.toString().getBytes("UTF8");
        byte[] encryptedBytes = CryptUtils.crypt(
                tokenBytes, CryptUtils.desDecode(properties.get("sharedKey"), properties.get("keyPassword")), Algorithm.AES, Cipher.ENCRYPT_MODE);
        LOG.debug("length : " + encryptedBytes.length);
        return Base64Utils.encodeToString(encryptedBytes);
    }

    /**
     * decode ltpa token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static UserMetadata decode(String token, Map<String, String> properties) throws Exception {
        byte[] sharedKey = CryptUtils.desDecode(properties.get("sharedKey"), properties.get("keyPassword"));
        byte[] encryptedBytes = Base64Utils.decodeFromString(token);
        LOG.debug("length : " + encryptedBytes.length);
        String plainToken = new String(CryptUtils.crypt(encryptedBytes, sharedKey, Algorithm.AES, Cipher.DECRYPT_MODE), "UTF-8");
        return new UserMetadata(plainToken, "");
    }


    /**
     * @param userInfo
     * @return
     */
    public static String createUserMeteDate(UserInfo userInfo) {
        return JSONObject.toJSONString(userInfo);
    }

    /**
     * 临时
     *
     * @return
     */
    public static Map<String, String> getLtpaProperties() {
        Map<String, String> proMap = new HashMap<>();
        proMap.put("PrivateKey", "U2FsdGVkX18bF9mbPtiuLJsqiG1Gd1NMe80OMrSk6KWFMyoh74EsBZVEEsJBOluQVkCcGNE0EJ8=");
        proMap.put("KeyPassword", "ltpaprivatekey");
        proMap.put("expiredTime", "10");

        proMap.put("sharedKey", "");

        proMap.put("cryptType", "AES");
        return proMap;
    }
}
