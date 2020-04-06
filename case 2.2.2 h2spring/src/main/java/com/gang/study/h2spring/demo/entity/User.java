package com.gang.study.h2spring.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Classname User
 * @Description TODO
 * @Date 2020/4/5 22:13
 * @Created by zengzg
 */
@Entity
@Data
public class User implements Serializable {

    @Id
    private Long userId;
    @Column
    private String userName;
    @Column
    private Integer userAge;
    @Column
    private Date createTime;
    @Column
    private Date modifyTime;
    @Column
    private Boolean userStatus;
}
