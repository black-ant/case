package com.gang.study.elasticsearch.demo.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

/**
 * @Classname BOrder
 * @Description TODO
 * @Date 2022/3/3
 * @Created by zengzg
 */
@Data
@Document(indexName = "order")
public class BOrder extends BaseOrder {

    private String username;

    private Integer age;

    private String innerAddress;

    private String nickName;

    private Date createTime;

    private Integer status;

    private Integer sex;

    private String role;

    private String type;

}
