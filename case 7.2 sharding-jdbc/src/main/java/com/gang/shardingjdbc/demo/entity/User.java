package com.gang.shardingjdbc.demo.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    private Long id;

    private String city;

    private String name;
}
