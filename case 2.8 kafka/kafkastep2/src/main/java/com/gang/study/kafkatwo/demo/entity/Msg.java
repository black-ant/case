package com.mykafka.demo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/25 17:15
 * @Version 1.0
 **/
@Data
public class Msg {

    private Integer msgid;

    private String msgDesc;

    private Date createDate;
}
