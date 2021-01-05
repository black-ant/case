package com.gang.study.ldap.ldap.to;

import lombok.Data;

/**
 * @Classname SearchTO
 * @Description TODO
 * @Date 2019/12/11 17:44
 * @Created by zengzg
 */
@Data
public class SearchTO<T, E> {

    protected T key;
    protected T parentId;
    protected Boolean fetchChild;
}
