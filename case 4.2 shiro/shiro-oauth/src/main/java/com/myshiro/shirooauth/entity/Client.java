package com.myshiro.shirooauth.entity;

import lombok.Data;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/26 22:42
 * @Version 1.0
 **/
@Data
public class Client {
    private Long id;
    private String clientName;
    private String clientId;
    private String clientSecret;
}
