package org.mengyun.tcctransaction.sample.dubbo.entity;


import org.mengyun.tcctransaction.sample.exception.InsufficientBalanceException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * Created by changming.xie on 4/2/16.
 */
@Entity
@Table(name = "cap_capital_account")
public class CapitalAccount {

    @Id
    @Column(name = "CAPITAL_ACCOUNT_ID")
    private long id;

    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "BALANCE_AMOUNT")
    private BigDecimal balanceAmount;

    @Transient
    private BigDecimal transferAmount = BigDecimal.ZERO;

    public long getUserId() {
        return userId;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void transferFrom(BigDecimal amount) {

        this.balanceAmount = this.balanceAmount.subtract(amount);

        if (BigDecimal.ZERO.compareTo(this.balanceAmount) > 0) {
            throw new InsufficientBalanceException();
        }

        transferAmount = transferAmount.add(amount.negate());
    }

    public void transferTo(BigDecimal amount) {
        this.balanceAmount = this.balanceAmount.add(amount);
        transferAmount = transferAmount.add(amount);
    }

    public void cancelTransfer(BigDecimal amount) {
        transferTo(amount);
    }
}
