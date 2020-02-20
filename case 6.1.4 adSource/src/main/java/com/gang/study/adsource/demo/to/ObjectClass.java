package com.gang.study.adsource.demo.to;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

/**
 * @Classname ObjectClass
 * @Description TODO
 * @Date 2020/2/20 14:17
 * @Created by zengzg
 */
public class ObjectClass {

    public static final String ACCOUNT_NAME = createSpecialName("ACCOUNT");

    public static final String GROUP_NAME = createSpecialName("GROUP");

    public static final String ORGANIZATION_NAME = createSpecialName("ORGANIZATION");

    public static final String ALL_NAME = createSpecialName("ALL");

    public static final ObjectClass ACCOUNT = new ObjectClass(ACCOUNT_NAME);

    public static final ObjectClass GROUP = new ObjectClass(GROUP_NAME);

    public static final ObjectClass ORGANIZATION = new ObjectClass(ORGANIZATION_NAME);

    public static final ObjectClass ALL = new ObjectClass(ALL_NAME);

    private final String type;

    public static String createSpecialName(final String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Name parameter must not be blank!");
        }
        final StringBuilder bld = new StringBuilder();
        bld.append("__").append(name).append("__");
        return bld.toString();
    }

    public ObjectClass(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null.");
        }
        this.type = type;
    }

    public String getObjectClassValue() {
        return type;
    }

    public String getDisplayNameKey() {
        return "MESSAGE_OBJECT_CLASS_" + type.toUpperCase(Locale.US);
    }

    public boolean is(String name) {
        return type.equals(name);
    }

}
