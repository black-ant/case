package com.mybatistest.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/20 18:07
 * @Version 1.0
 **/
@Data
public class MyOrder implements Serializable {

    private Integer id;

    private Date startdate;

    private String ordername;

    private String orderdesc;

    private String remarks;

    private Boolean active;

    private Integer status;

    private BigDecimal price;

    private Integer createuser;

    private List<User> users;

    public MyOrder() {
    }

    public MyOrder(String ordername, String orderdesc, BigDecimal price) {
        this.startdate = new Date();
        this.ordername = ordername;
        this.orderdesc = orderdesc;
        this.active = Boolean.TRUE;
        this.status = 1;
        this.price = price;
    }

    public MyOrder(Date startDate, String ordername, String orderdesc, Boolean active, Integer status, BigDecimal price) {
        this.startdate = startDate;
        this.ordername = ordername;
        this.orderdesc = orderdesc;
        this.active = active;
        this.status = status;
        this.price = price;
    }
}
