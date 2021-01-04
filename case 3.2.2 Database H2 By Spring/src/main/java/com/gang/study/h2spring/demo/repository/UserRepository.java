package com.gang.study.h2spring.demo.repository;

import com.gang.study.h2spring.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Classname UserRepository
 * @Description TODO
 * @Date 2020/4/5 22:16
 * @Created by zengzg
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
