package com.gang.junit.h2.demoh2.entity;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Classname TestDO
 * @Description TODO
 * @Date 2020/10/12 16:37
 * @Created by zengzg
 */
@Entity
@Table(name = "user")
@Data
@Proxy(lazy = false)
public class TestEntitiy implements Serializable {

    @Id
    @Column(name = "userid")
    private String userid;

    @Column(name = "username")
    private String username;

}
