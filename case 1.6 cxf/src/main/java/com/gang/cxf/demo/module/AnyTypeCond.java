package com.gang.cxf.demo.module;

import com.gang.cxf.demo.module.AttributeCond.Type;

/**
 * @Classname AnyTypeCond
 * @Description TODO
 * @Date 2019/10/31 10:54
 * @Created by ant-black 1016930479@qq.com
 */

public class AnyTypeCond extends AbstractSearchCond {

    private static final long serialVersionUID = 4298076973281246633L;

    private String anyTypeKey;

    private Type type = Type.EQ;

    public final Type getType() {
        return type;
    }

    public final void setType(final Type conditionType) {
        this.type = conditionType;
    }

    public String getAnyTypeKey() {
        return anyTypeKey;
    }

    public void setAnyTypeKey(final String anyTypeKey) {
        this.anyTypeKey = anyTypeKey;
    }

    @Override
    public boolean isValid() {
        return anyTypeKey != null;
    }

}

