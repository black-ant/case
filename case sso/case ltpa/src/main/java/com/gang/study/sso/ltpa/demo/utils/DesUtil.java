package com.gang.study.sso.ltpa.demo.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;

/**
 * @Classname DesUtil
 * @Description TODO
 * @Date 2020/6/30 17:49
 * @Created by zengzg
 */
public class DesUtil {
    /**
     * @param args
     */
    public static void main(String[] args) {
        //以下是加密方法algorithm="AES"的测试
        System.out.println(DesUtil.getInstance("lushuaiyin").getEnCodeString("hello"));
        //输出 LDewGAZkmWHeYFjBz56ylw==
        //将上面的密文解密：
        System.out.println(DesUtil.getInstance("lushuaiyin").getDecodeString("LDewGAZkmWHeYFjBz56ylw=="));
        //输出 hello
        //改变密钥测试
        System.out.println(DesUtil.getInstance("suolong").getEnCodeString("hello"));
        //输出 /RLowOJ+Fr3KdMcdJeNatg==
        System.out.println(DesUtil.getInstance("suolong").getDecodeString("/RLowOJ+Fr3KdMcdJeNatg=="));
        //输出 hello
        //如果使用不正确的密钥解密，将会：
        System.out.println(DesUtil.getInstance("suolong").getDecodeString("LDewGAZkmWHeYFjBz56ylw=="));
    }

    private SecretKey key = null;//密钥
    //定义 加密算法,可用 DES,DESede,Blowfish,AES
    //不同的加密方式结果会不同
    private static String algorithm = "AES";
    private static DesUtil desUtil = null;

    public DesUtil() {
    }

    public static DesUtil getInstance(String strKey) {
        desUtil = new DesUtil();
        desUtil.createKey(strKey);
        return desUtil;
    }

    /**
     * algorithm 算法
     *
     * @param strKey
     */
    public void createKey(String strKey) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(DesUtil.algorithm);
            byte[] bt = strKey.getBytes("UTF-8");
            SecureRandom sr = new SecureRandom(bt);
            kg.init(sr);
            this.setKey(kg.generateKey());
        } catch (Exception e) {
        }
    }

    /**
     * 加密方法，返回密文
     * cipher 密码
     *
     * @param dataStr
     */
    public String getEnCodeString(String dataStr) {
        byte[] miwen = null;//密文
        byte[] mingwen = null;//明文
        Cipher cipher;
        String result = "";//密文字符串
        try {
            mingwen = dataStr.getBytes("UTF-8");
            cipher = Cipher.getInstance(DesUtil.algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, this.getKey());
            miwen = cipher.doFinal(mingwen);
            BASE64Encoder base64en = new BASE64Encoder();
            result = base64en.encodeBuffer(miwen);//或者可以用下面的方法得到密文，结果是不一样的，都可以正常解密
            //   result=byte2hex(miwen);//密文结果类似2C:37:B0:18:06:64:99:61:DE:60:58:C1:CF:9E:B2:97
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解密方法，返回明文
     *
     * @param codeStr
     * @return
     */
    public String getDecodeString(String codeStr) {
        BASE64Decoder base64De = new BASE64Decoder();
        byte[] miwen = null;
        byte[] mingwen = null;
        String resultData = "";//返回的明文
        Cipher cipher;
        try {
            miwen = base64De.decodeBuffer(codeStr);
            cipher = Cipher.getInstance(DesUtil.algorithm);
            cipher.init(Cipher.DECRYPT_MODE, this.getKey());
            mingwen = cipher.doFinal(miwen);
            resultData = new String(mingwen, "UTF-8");
        } catch (Exception e) {
            return "密钥不正确或其他原因导致异常，无法解密！";
        }
        return resultData;
    }

    //二行制转字符串
    public String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + ":";
        }
        return hs.toUpperCase();
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public static String getAlgorithm() {
        return algorithm;
    }

    public static void setAlgorithm(String algorithm) {
        algorithm = algorithm;
    }
}
