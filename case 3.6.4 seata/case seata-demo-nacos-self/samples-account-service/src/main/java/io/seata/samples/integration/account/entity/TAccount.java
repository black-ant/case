package io.seata.samples.integration.account.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

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
@Table(name = "t_account")
public class TAccount {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "amount")
    private Double amount;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
        return "TAccount{" +
                ", id=" + id +
                ", userId=" + userId +
                ", amount=" + amount +
                "}";
    }
}
