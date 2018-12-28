package com.security.demo.service;


import com.security.demo.entity.UserLog;
import com.security.demo.entity.Users;
import com.security.demo.repository.UserLogRepository;
import com.security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserLogRepository userLogRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        System.out.println("step2============");
        System.out.println(user.getUsername()+"---"+user.getPassword()+"--"+user.getRoles());
        System.out.println(new BCryptPasswordEncoder().encode(user.getPassword()));
        if(user==null){
            throw  new UsernameNotFoundException("用户不存在");
        }
        return user;
    }

    public Users findUserByName(String username){
        Users user = userRepository.findByUsername(username);
        return user;
    }
    public Users findBySn(){
        Users user = userRepository.findByUsername("gang");
        return user;
    }
    public void saveUser(Users user){
       userRepository.save(user);
    }

    public UserLog findLogBySn(){
        return userLogRepository.findBySn(1);
    }


}
