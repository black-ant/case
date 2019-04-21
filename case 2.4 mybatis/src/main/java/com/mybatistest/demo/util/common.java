package com.mybatistest.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/21 18:19
 * @Version 1.0
 **/
public class common {

    public static Date stringToDate(String value) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(value);
    }
}
