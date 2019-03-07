package com.mycache.encache.service;

import com.mycache.encache.entity.Departments;
import com.mycache.encache.repository.DepartmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/3/7 22:38
 * @Version 1.0
 **/
@Service
public class FindService {

    private static final String CACHE_KEY = "'dep'";

    @Autowired
    DepartmentsRepository departmentsRepository;


    public List<Departments> findDepartments(){
        return departmentsRepository.findAll();
    }


    @Cacheable(value=CACHE_KEY,key="'dep'+#dname")
    public Departments findDepartmentsByFilter(String dname){
        return departmentsRepository.findByDeptname(dname);
    }
}
