package com.gang.tcc.transaction.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by changming.xie on 4/1/16.
 */

@Entity
@Table(name = "shop")
@Data
public class Shop {

    @Id
    private long id;

    @Column(name = "ownerUserId")
    private long ownerUserId;

}
