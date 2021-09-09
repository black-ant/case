package com.gang.study.collection.demo.to;

import java.util.Objects;

/**
 * @Classname HashTO
 * @Description TODO
 * @Date 2021/9/9
 * @Created by zengzg
 */
public class HashTO {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashTO)) return false;
        HashTO hashTO = (HashTO) o;
        return Objects.equals(value, hashTO.value);
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
