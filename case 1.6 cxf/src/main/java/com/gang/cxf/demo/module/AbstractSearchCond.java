package com.gang.cxf.demo.module;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @Classname AbstractSearchCond
 * @Description TODO
 * @Date 2019/10/31 10:46
 * @Created by ant-black 1016930479@qq.com
 */
public abstract class AbstractSearchCond implements Serializable {

    private static final long serialVersionUID = 5376869884544910804L;

    @Override
    public boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }

    public abstract boolean isValid();

}

