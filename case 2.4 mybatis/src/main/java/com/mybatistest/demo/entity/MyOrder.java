package com.mybatistest.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/20 18:07
 * @Version 1.0
 **/
@Data
public class MyOrder implements Serializable {

    private Integer id;

    private Date startDate;

    private String ordername;

    private String desc;

    private Boolean active;

    private Integer status;

    private BigDecimal price;
}
