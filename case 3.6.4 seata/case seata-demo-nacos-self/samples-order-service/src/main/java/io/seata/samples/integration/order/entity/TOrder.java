package io.seata.samples.integration.order.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>
 *
 * </p>
 * <p>
 * * @author lidong
 *
 * @since 2019-09-04
 */
@Entity
@Table(name = "t_order")
public class TOrder {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "commodity_code")
    private String commodityCode;

    @Column(name = "count")
    private Integer count;

    @Column(name = "amount")
    private Double amount;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TOrder{" +
                ", id=" + id +
                ", orderNo=" + orderNo +
                ", userId=" + userId +
                ", commodityCode=" + commodityCode +
                ", count=" + count +
                ", amount=" + amount +
                "}";
    }
}
