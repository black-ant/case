package com.myjpa.demo.repository;

import com.myjpa.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/28 21:59
 * @Version 1.0
 **/
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
