package com.gang.study.multiplysource.entityTwo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Classname OrgEntity
 * @Description TODO
 * @Date 2021/1/16 16:24
 * @Created by zengzg
 */
@Entity
@Table(name = "org")
@Data
public class OrgEntity {

    @Id
    private int id;

    @Column(name = "orgname")
    private String orgname;
}
