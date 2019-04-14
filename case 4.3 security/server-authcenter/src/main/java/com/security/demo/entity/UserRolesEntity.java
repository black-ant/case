package com.security.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/1/17 22:22
 * @Version 1.0
 **/
@Data
@Entity(name="user_roles")
public class UserRolesEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String role;
    @JsonIgnore
    @ManyToMany(targetEntity =AuthorityEntity.class,fetch = FetchType.EAGER)
    @BatchSize(size = 20)
    private Set<AuthorityEntity> authorities = new HashSet<>();
}
