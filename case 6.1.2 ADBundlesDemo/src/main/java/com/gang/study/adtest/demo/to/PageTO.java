package com.gang.study.adtest.demo.to;

import lombok.Data;

/**
 * @Classname PageTO
 * @Description TODO
 * @Date 2019/11/14 17:31
 * @Created by zengzg
 */
@Data
public class PageTO<E> {

    private static final long serialVersionUID = 1221483938919458876L;

    private E page;

    private E size;

    public PageTO(E page, E size) {
        this.page = page;
        this.size = size;
    }
}
