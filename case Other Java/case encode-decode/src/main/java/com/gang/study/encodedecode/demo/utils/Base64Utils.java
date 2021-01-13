package com.gang.study.encodedecode.demo.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;

/**
 * @Classname Base64Utils
 * @Description TODO
 * @Date 2020/6/30 18:04
 * @Created by zengzg
 */
public class Base64Utils {


    private static String encode64(String data) {
        return Base64.encode(data);
    }

    private static String decode(String data) {
        return Base64.decodeStr(data);
    }
}
