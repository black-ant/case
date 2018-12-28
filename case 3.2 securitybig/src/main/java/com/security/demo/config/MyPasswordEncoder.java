package com.security.demo.config;

import com.security.demo.Utils.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;

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
