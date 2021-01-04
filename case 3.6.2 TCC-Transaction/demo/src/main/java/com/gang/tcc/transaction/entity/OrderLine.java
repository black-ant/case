package com.gang.tcc.transaction.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by changming.xie on 4/1/16.
 */
@Entity
@Table(name = "OrderLine")
@Data
public class OrderLine implements Serializable {

    private static final long serialVersionUID = 2300754647209250837L;

    @Id
    private long id;

    @Column(name = "productId")
    private long productId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unitPrice")
    private BigDecimal unitPrice;

}
