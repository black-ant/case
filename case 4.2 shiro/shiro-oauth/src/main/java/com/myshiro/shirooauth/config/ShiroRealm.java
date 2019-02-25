package com.myshiro.shirooauth.config;

import com.myshiro.shirooauth.entity.Role;
import com.myshiro.shirooauth.entity.User;
import com.myshiro.shirooauth.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/22 22:11
 * @Version 1.0
 **/
@Component
public class ShiroRealm extends AuthorizingRealm {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("————权限认证————");
        String name = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        User user = userService.findUserByUsername(name);
        List<Role> roles = user.getRoles();
        logger.debug("roles权限：{}",roles.toString());
        logger.info("roles权限：{}",roles.toString());
        Set<String> set = new HashSet<>();
        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
        roles.forEach((role)->{set.add(role.getRoledesc());});
        //设置该用户拥有的角色
        info.setRoles(set);
        return info;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String name = token.getUsername();
        // 先从redis中获取用户信息,从数据库获取对应用户名密码的用户
        logger.info("登陆 doGetAuthenticationInfo：{}",authenticationToken.toString());
        User user = checkUserFromRedis(name);
        if (user.getPassword() == null || user == null) {
            throw new AccountException("用户名不正确!");
        } else if (!user.getPassword().equals(new String((char[]) token.getCredentials()))) {
            throw new AccountException("密码不正确!");
        }
        return new SimpleAuthenticationInfo(token.getPrincipal(), user.getPassword(), getName());
    }

    public User checkUserFromRedis(String name){
            User user = new User();
            user.setUserid(1);
            user.setUsername(name);
            user.setPassword("123456");
            List<Role> roles = new LinkedList<>();
            if("gang".equals(name)){
                roles.add(new Role(1,"user"));
            }else if("test".equals(name)){
                roles.add(new Role(1,"admin"));
            }
            user.setRoles(roles);
        return user;
    }


}
