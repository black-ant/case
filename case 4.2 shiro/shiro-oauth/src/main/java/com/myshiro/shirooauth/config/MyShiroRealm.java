package com.myshiro.shirooauth.config;

import com.myshiro.shirooauth.entity.Role;
import com.myshiro.shirooauth.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/22 22:11
 * @Version 1.0
 **/
@Component
public class MyShiroRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        //获取登录用户名
//        String name= (String) principalCollection.getPrimaryPrincipal();
//        //查询用户名称
//        RespMsgBean respMsgBean = userClientService.findByName(name);
//        UserVo user = (UserVo) respMsgBean.getData();
//        //添加角色和权限
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        for (RoleVo role : user.getRoles()) {
//            //添加角色
//            simpleAuthorizationInfo.addRole(role.getRoleName());
//            for (PermissionVo permission:role.getPermissions()) {
//                //添加权限
//                simpleAuthorizationInfo.addStringPermission(permission.getPermissionName());
//            }
//        }
//        return simpleAuthorizationInfo;

        System.out.println("————权限认证————");
        String name = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        User user = checkUserFromRedis(name);
        List<Role> roles = user.getRoles();
        Set<String> set = new HashSet<>();
        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
        roles.forEach((role)->{set.add(role.getRoleName());});
        //设置该用户拥有的角色
        info.setRoles(set);
        return info;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String name = token.getUsername();
        // 先从redis中获取用户信息,从数据库获取对应用户名密码的用户
        User user = checkUserFromRedis(name);
        if (user.getPassword() == null || user == null) {
            throw new AccountException("用户名不正确!");
        } else if (!user.getPassword().equals(new String((char[]) token.getCredentials()))) {
            throw new AccountException("密码不正确!");
        }
        return new SimpleAuthenticationInfo(token.getPrincipal(), user.getPassword(), getName());
    }

    public User checkUserFromRedis(String name){
//        User user = null;
//        Map<String,User> onLineMap = new HashMap<String, User>();
//        if(!redisClient.hasKeyOfMap("onLineMap",name)){
//            RespMsgBean<User> respMsgBean = userClientService.findByName(name);
//            user = respMsgBean.getData();
//            // 设置缓存
//            redisClient.setOneMap("onLineMap",name,user,60*60*24*7);
////            redisClient.setValue(name,user,60*60*24*7);
//        }else{
////            user = (UserVo) redisClient.getValue(name);
//            user = (User) redisClient.getValueOfMap("onLineMap",name);
//        }
            User user = new User();
            user.setUserid(1);
            user.setPassword("123456");

        return user;
    }
}
