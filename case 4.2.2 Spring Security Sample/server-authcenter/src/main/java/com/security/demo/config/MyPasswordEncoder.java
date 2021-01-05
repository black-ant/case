package com.security.demo.config;

import com.serverauthcenter.demo.utils.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/1/20 12:29
 * @Version 1.0
 **/
public class MyPasswordEncoder implements PasswordEncoder {
    //用户加密配置
    @Override
    public String encode(CharSequence charSequence) {
        return MD5Util.encode((String)charSequence);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(MD5Util.encode((String)charSequence));
    }
}
