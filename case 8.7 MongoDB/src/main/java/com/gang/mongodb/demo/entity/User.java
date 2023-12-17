package com.gang.mongodb.demo.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @Classname User
 * @Description TODO
 * @Date 2021/9/21
 * @Created by zengzg
 */
@Data
@Document(collection = "user")
public class User {

    private Long id;

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

    private String innerAddress;

    private String nickName;

    private Date createTime;

}
