package com.mymap.baiduapi.entity;

import java.io.Serializable;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/28 20:17
 * @Version 1.0
 **/
public class PushWrapper implements Serializable {

    private String groupid;
    private String pushtype;
    private String msg;
    private String datacontent;
    private Integer pushcode;
}
