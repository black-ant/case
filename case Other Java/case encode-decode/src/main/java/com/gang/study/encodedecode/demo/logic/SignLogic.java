package com.gang.study.encodedecode.demo.logic;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.crypto.digest.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

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
        reversibleSignEncode();
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
        String sign = "SignLogic";
        String salt1 = "IQqao1p4bPlKDfF55a1+6Q==";
        String md5Result = MD5.create().digestHex(sign);
        logger.info("------> md5Result :{} <-------", md5Result);

        String test = new Digester(DigestAlgorithm.SHA256).setSalt(salt1.getBytes()).digestHex(md5Result);
        logger.info("------> sha512 1  :{} <-------", test);

        String test1 = new Digester(DigestAlgorithm.SHA256).setSalt(salt1.getBytes()).digestHex(md5Result);
        logger.info("------> sha512 1  :{} <-------", test1);

        String salt5 = "salt2";
        String test5 = new Digester(DigestAlgorithm.SHA256).setSalt(salt5.getBytes()).digestHex(md5Result);
        logger.info("------> sha512 2 :{} <-------", test5);
    }


}
