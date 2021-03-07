package com.gang.study.shiro.demo.service;

import com.gang.study.shiro.demo.to.Permissions;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @Classname PermissionServiceImpl
 * @Description TODO
 * @Date 2021/3/7 11:28
 * @Created by zengzg
 */
@Component
public class PermissionServiceImpl {

    /**
     * 返回 Permission
     *
     * @param pid
     * @return
     */
    public Permissions getPermsByUserId(String pid) {
        return pid.equals("1") ? new Permissions("1", "query") : new Permissions("2", "add");
    }
}
