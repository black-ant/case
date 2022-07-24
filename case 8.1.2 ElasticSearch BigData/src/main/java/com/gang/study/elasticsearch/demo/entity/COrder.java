package com.gang.study.elasticsearch.demo.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

/**
 * @Classname COrder
 * @Description TODO
 * @Date 2022/3/3
 * @Created by zengzg
 */
@Data
@Document(indexName = "order")
public class COrder extends BaseOrder {

    private String username;

    private Integer age;

    private String outAddress;

    private Date createTime;

    private Date updateTime;

    private String extension;

    private Integer status;

    private Integer sex;

    private String type;
}
