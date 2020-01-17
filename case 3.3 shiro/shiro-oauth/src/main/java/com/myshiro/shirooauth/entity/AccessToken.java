package com.myshiro.shirooauth.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/3/5 21:35
 * @Version 1.0
 **/
@Data
public class AccessToken {
    private Long sn;
    private Long userid;
    private String username;
    private String accesscode;
    private String accesstoken;
    private Date startdate;
    private Date outdate;

    public AccessToken(){}

    public AccessToken(String username, String accesscode, String accesstoken, Date startdate, Date outdate) {
        this.username = username;
        this.accesscode = accesscode;
        this.accesstoken = accesstoken;
        this.startdate = startdate;
        this.outdate = outdate;
    }
}
