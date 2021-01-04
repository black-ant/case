package com.gang.tcc.transaction.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by changming.xie on 3/25/16.
 */
@Entity
@Table(name = "order")
@Data
public class Order implements Serializable {

    private static final long serialVersionUID = -5908730245224893590L;

    @Id
    private long id;

    @Column(name = "payerUserId")
    private long payerUserId;

    @Column(name = "payeeUserId")
    private long payeeUserId;

    @Column(name = "redPacketPayAmount")
    private BigDecimal redPacketPayAmount;

    @Column(name = "capitalPayAmount")
    private BigDecimal capitalPayAmount;

    @Column(name = "status")
    private String status = "DRAFT";

    @Column(name = "merchantOrderNo")
    private String merchantOrderNo;

    @Column(name = "version")
    private long version = 1l;

    @OneToMany
    private List<OrderLine> orderLines = new ArrayList<OrderLine>();

    public Order(long payerUserId, long payeeUserId) {
        this.payerUserId = payerUserId;
        this.payeeUserId = payeeUserId;
    }
}
