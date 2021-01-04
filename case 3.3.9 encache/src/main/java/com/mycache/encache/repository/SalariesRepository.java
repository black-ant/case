package com.mycache.encache.repository;

import com.mycache.encache.entity.Departments;
import com.mycache.encache.entity.Salaries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/3/9 21:55
 * @Version 1.0
 **/
public interface SalariesRepository extends JpaRepository<Salaries,Long> {

    List<Salaries> findAllBy();
}
