package com.gang.study.myoracle.demo.repository;


import com.gang.study.myoracle.demo.entity.OracleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Classname UserRepository
 * @Description TODO
 * @Date 2020/4/6 20:07
 * @Created by zengzg
 */
@Repository
public interface UserRepository extends JpaRepository<OracleUser, String> {
}
