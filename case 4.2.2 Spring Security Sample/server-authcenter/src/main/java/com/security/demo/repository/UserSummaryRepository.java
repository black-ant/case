package com.security.demo.repository;

import com.security.demo.entity.UserSummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/1/17 22:25
 * @Version 1.0
 **/
public interface UserSummaryRepository extends JpaRepository<UserSummaryEntity,Long> {

    public UserSummaryEntity findByUserid(String userid);

    public UserSummaryEntity findByUsername(String username);
}
