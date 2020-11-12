package com.myjpa.demo.entity;


import com.myjpa.demo.entity.api.IOrgEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "org")
@Data
public class OrgEntity implements IOrgEntity {

    @Id
    private int id;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "org_type")
    private String orgType;


}
