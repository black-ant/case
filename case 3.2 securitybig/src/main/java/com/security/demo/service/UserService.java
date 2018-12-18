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
        System.out.print("step2============");
        System.out.print(user.getUsername());
        System.out.print(user.getPassword());
        if(user==null){
            throw  new UsernameNotFoundException("用户不存在");
        }
        return user;
    }
}
