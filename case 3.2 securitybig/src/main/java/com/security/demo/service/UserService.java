package com.security.demo.service;


import com.security.demo.entity.Users;
import com.security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        System.out.println("step2============");
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        if(user==null){
            throw  new UsernameNotFoundException("用户不存在");
        }
        return user;
    }

    public Users findUserByName(String username){
        Users user = userRepository.findByUsername(username);
        return user;
    }
}
