package com.gang.study.encodedecode.demo.logic;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.crypto.digest.MD5;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname SignLogic
 * @Description 签名处理
 * @Date 2021/1/12 19:46
 * @Created by zengzg
 */
@Service
public class SignLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        logger.info("------> this is in signLogic Run <-------");
//        reversibleSignEncode();
//        irreversibilityEncode();
        reversibleSignDecode("XNUlDT3BmcBZQzdVeVFCODhXSitRQT09");

        irreversibilityEncode();
    }

    /**
     * Sign 可逆操作加密
     */
    public void reversibleSignEncode() {

        String sign = "signLogic";
        // Step 1 : Base64 处理
        String base64 = Base64.encode(sign);
        logger.info("------> before :{} <-------", base64);
        // Step 2 : 进行移位处理 , 获取当前 Base64 长度的百分之30的字符下标
        int num = (int) (base64.length() * 0.3);
        List<Character> arrays = base64.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        // Step 3 : 取出该下标对应的字符并且放在首位
        arrays.add(0, arrays.remove(num));
        String newStr = arrays.stream().map(e -> e.toString()).reduce((acc, e) -> acc + e).get();

        reversibleSignDecode(newStr);
    }

    /**
     * Sign 可逆操作解密
     *
     * @param encodeStr
     */
    public void reversibleSignDecode(String encodeStr) {

        int num = (int) (encodeStr.length() * 0.3);

        List<Character> arrays = encodeStr.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        arrays.add(num, arrays.remove(0));
        String base64 = arrays.stream().map(e -> e.toString()).reduce((acc, e) -> acc + e).get();
        String result = Base64.decodeStr(base64);

        logger.info("------> result :{} <-------", result);
    }

    /**
     * Sign 不可逆加密
     */
    public void irreversibilityEncode() {
        // 85
        String salt1 = "5ICOpfqpYC7UyQB88WJ+QA==";
        String sign = "1438ca83b87d569744557015a9b82ce220210119175618" + salt1;
//        String sign = "1438ca83b87d569744557015a9b82ce220210119162103" + salt1;

        // Default
//        String salt1 = "RUzoY18p8OuL1rJovE3CZw==";
//        String sign = "123456120210119175600" + salt1;

        String md5Result = MD5.create().digestHex(sign);
        logger.info("------> 加密前数据 :{} <-------", sign);
        logger.info("------> md5Result :{} <-------", md5Result);

        String test = new Digester(DigestAlgorithm.SHA256).setSalt(salt1.getBytes()).digestHex(md5Result);
        String testJava = sha256(salt1, sign);
        logger.info("------> sha256 1  :{} <-------", test);
        logger.info("------> sha256 testJava  :{} <-------", testJava);

        String test1 = new Digester(DigestAlgorithm.SHA256).setSalt(salt1.getBytes()).digestHex(md5Result);
        logger.info("------> sha512 1  :{} <-------", test1);

        String test2 = new Digester(DigestAlgorithm.SHA256).digestHex(md5Result);
        logger.info("------> test2 1  :{} <-------", test2);

        String salt5 = "salt2";
        String test5 = new Digester(DigestAlgorithm.SHA256).setSalt(salt5.getBytes()).digestHex(md5Result);
//        logger.info("------> sha512 2 :{} <-------", test5);


    }

    /**
     * @param salt
     * @param pwdPlain
     * @return sha256
     */
    public static String sha256(String salt, String pwdPlain) {

        return Hashing.sha256().newHasher().putString(salt + pwdPlain, Charsets.UTF_8).hash().toString();
    }


    /**
     * 利用java原生的类实现SHA256加密
     *
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256(String str) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodestr;
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }


}
