package com.myshiro.shirooauth.entity;

import lombok.Data;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/22 22:03
 * @Version 1.0
 **/

@Data
public class Role {

    private int roleid;
    private String roletype;
    private String roledesc;
    private int userid;

    public Role(){}

    public Role( int role, String roletype) {
        this.roleid = role;
        this.roletype = roletype;
    }
}
