package org.mengyun.tcctransaction.sample.dubbo.entity;

import org.mengyun.tcctransaction.sample.exception.InsufficientBalanceException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by changming.xie on 4/2/16.
 */
@Entity
@Table(name = "red_red_packet_account")
public class RedPacketAccount {

    @Id
    @Column(name = "RED_PACKET_ACCOUNT_ID")
    private long id;

    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "BALANCE_AMOUNT")
    private BigDecimal balanceAmount;

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
    }

    public void transferTo(BigDecimal amount) {
        this.balanceAmount = this.balanceAmount.add(amount);
    }

    public void cancelTransfer(BigDecimal amount) {
        transferTo(amount);
    }
}
