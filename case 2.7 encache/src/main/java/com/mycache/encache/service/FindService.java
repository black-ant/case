package com.mycache.encache.service;

import com.mycache.encache.entity.Departments;
import com.mycache.encache.repository.DepartmentsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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

    private static final String CACHE_KEY = "'deps'";
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    DepartmentsRepository departmentsRepository;


    public List<Departments> findDepartments() {
        return departmentsRepository.findAll();
    }


    @Cacheable(value = CACHE_KEY, key = "'dep'+#dname")
    public Departments findDepartmentsByFilter(String dname) {
        logger.info("进入查询逻辑：dname-{}", dname);
        return departmentsRepository.findByDeptname(dname);
    }

    @Cacheable(value = CACHE_KEY, key = "'dep'+#depno")
    public Departments findByDepno(String depno) {
        logger.info("进入查询逻辑：depno-{}", depno);
        return departmentsRepository.findFilters(null, depno);
    }

    @CacheEvict(value = CACHE_KEY)
    public void clearCache() {
    }

    @CachePut(value = CACHE_KEY, key = "'dep'+#departments.deptno")
    public Departments createOne(Departments departments) {
        return departmentsRepository.save(departments);
    }
}
