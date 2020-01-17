package com.myshiro.shirooauth.service;

import com.myshiro.shirooauth.entity.Role;
import com.myshiro.shirooauth.entity.User;
import com.myshiro.shirooauth.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/22 22:50
 * @Version 1.0
 **/
@Service
public class LoginService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;

    public String login(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
        User user = userMapper.findByUsername(username);
        logger.info("登陆信息：{}", subject.getPrincipal(),user.getUserid());
        logger.info("User信息：{}",user.getRoles().toArray().toString());

        //根据权限，指定返回数据
//        String role = userMapper.getRole(username);
//        if ("user".equals(role)) {
//            return resultMap.success().message("欢迎登陆");
//        }
//        if ("admin".equals(role)) {
//            return resultMap.success().message("欢迎来到管理员页面");
//        }
        return "";
    }

    public String logout() {
        logger.info("退出对象：{}", SecurityUtils.getSubject());
        SecurityUtils.getSubject().logout();
        return "index";
    }
}
