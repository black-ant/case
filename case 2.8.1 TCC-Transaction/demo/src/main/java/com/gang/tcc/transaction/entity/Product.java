package com.gang.tcc.transaction.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by twinkle.zhou on 16/11/10.
 */
@Entity
@Table(name = "product")
@Data
public class Product implements Serializable {

    @Id
    private long id;

    @Column(name = "shopId")
    private long shopId;

    @Column(name = "productName")
    private String productName;

    @Column(name = "price")
    private BigDecimal price;

}
