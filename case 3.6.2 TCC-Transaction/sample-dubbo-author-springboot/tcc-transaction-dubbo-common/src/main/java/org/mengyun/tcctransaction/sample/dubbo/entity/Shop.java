package org.mengyun.tcctransaction.sample.dubbo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by changming.xie on 4/1/16.
 */
@Entity
@Table(name = "ord_shop")
public class Shop {

    @Id
    @Column(name = "SHOP_ID")
    private long id;

    @Column(name = "OWNER_USER_ID")
    private long ownerUserId;

    public long getOwnerUserId() {
        return ownerUserId;
    }

    public long getId() {
        return id;
    }
}
