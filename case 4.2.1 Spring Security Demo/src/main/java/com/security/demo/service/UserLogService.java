package com.security.demo.service;

import com.security.demo.entity.UserLog;
import com.security.demo.repository.UserLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserLogService {

    @Autowired
    UserLogRepository userLogRepository;

    public void save(UserLog userLog){
        userLogRepository.save(userLog);
    }
}
