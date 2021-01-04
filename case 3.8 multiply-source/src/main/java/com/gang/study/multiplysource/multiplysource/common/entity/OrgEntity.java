package com.gang.study.multiplysource.multiplysource.common.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "org")
@Data
public class OrgEntity {

    @Id
    private int id;

    @Column(name = "orgname")
    private String orgname;
}
