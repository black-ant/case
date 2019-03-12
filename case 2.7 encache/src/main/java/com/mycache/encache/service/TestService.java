package com.mycache.encache.service;

import com.mycache.encache.entity.Salaries;
import com.mycache.encache.repository.SalariesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/3/9 22:23
 * @Version 1.0
 **/
@Service
public class TestService {

    @Autowired
    SalariesRepository salariesRepository;

    public List<Salaries> findAll(){
        return  salariesRepository.findAll();
    }
}
