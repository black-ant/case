package com.gang.study.elasticsearch.demo.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @Classname AOrder
 * @Description TODO
 * @Date 2022/3/3
 * @Created by zengzg
 */
@Data
@Document(indexName = "order")
public class AOrder extends BaseOrder {

    private String username;

    private Integer age;

    private String address;

    private String mobile;

    private String email;

    private String password;

    private Integer status;

    private Long idCard;

    private Integer sex;

    private String role;

    private String type;
}
