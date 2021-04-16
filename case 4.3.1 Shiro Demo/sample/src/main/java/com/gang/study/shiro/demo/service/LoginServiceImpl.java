package com.gang.study.shiro.demo.service;

import com.gang.study.shiro.demo.api.LoginService;
import com.gang.study.shiro.demo.to.Permissions;
import com.gang.study.shiro.demo.to.Role;
import com.gang.study.shiro.demo.to.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Classname LoginServiceImpl
 * @Description TODO
 * @Date 2021/3/6 22:33
 * @Created by zengzg
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private PermissionServiceImpl permissionService;

    @Override
    public User getUserByName(String getMapByName) {
        return getMapByName(getMapByName);
    }

    /**
     * 模拟数据库查询
     *
     * @param userName 用户名
     * @return User
     */
    private User getMapByName(String userName) {

        // 构建 Role 1
        Role role = new Role("1", "admin", getAllPermission());
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        // 构建 Role 2
        Role role1 = new Role("2", "user", getSinglePermission());
        Set<Role> roleSet1 = new HashSet<>();
        roleSet1.add(role1);

        User user = new User("1", "root", "123456", roleSet);
        Map<String, User> map = new HashMap<>();
        map.put(user.getUserName(), user);

        User user1 = new User("2", "zhangsan", "123456", roleSet1);
        map.put(user1.getUserName(), user1);

        return map.get(userName);
    }

    /**
     * 权限类型一
     */
    private Set<Permissions> getAllPermission() {
        Set<Permissions> permissionsSet = new HashSet<>();
        permissionsSet.add(permissionService.getPermsByUserId("1"));
        permissionsSet.add(permissionService.getPermsByUserId("2"));
        return permissionsSet;
    }

    /**
     * 权限类型二
     */
    private Set<Permissions> getSinglePermission() {
        Set<Permissions> permissionsSet1 = new HashSet<>();
        permissionsSet1.add(permissionService.getPermsByUserId("1"));
        return permissionsSet1;
    }

}
