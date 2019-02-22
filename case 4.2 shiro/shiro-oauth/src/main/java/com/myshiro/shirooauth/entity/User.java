package com.myshiro.shirooauth.entity;

import com.myshiro.shirooauth.entity.Role;
import lombok.Data;

import java.util.List;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/21 21:54
 * @Version 1.0
 **/
@Data
public class User {
    private int userid;
    private String username;
    private String password;
    private String type;
    private String status;
    private List<Role> roles;
}
