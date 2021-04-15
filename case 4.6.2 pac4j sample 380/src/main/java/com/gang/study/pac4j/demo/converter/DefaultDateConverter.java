package com.gang.study.pac4j.demo.converter;

import org.pac4j.core.profile.converter.DateConverter;

import java.util.Date;

/**
 * @Classname DefaultDateConverter
 * @Description TODO
 * @Date 2021/4/14
 * @Created by zengzg
 */
public class DefaultDateConverter extends DateConverter {

    public DefaultDateConverter() {
        super("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    }

    @Override
    public Date convert(final Object attribute) {
        Object a = attribute;
        if (a instanceof String) {
            String s = (String) a;
            int pos = s.lastIndexOf("[");
            if (pos > 0) {
                s = s.substring(0, pos);
                pos = s.lastIndexOf(":");
                if (pos > 0) {
                    s = s.substring(0, pos) + s.substring(pos + 1, s.length());
                }
                a = s;
            }
        }
        return super.convert(a);
    }
}
