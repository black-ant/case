package com.gang.study.wycat.mycat.repository;

import com.gang.study.wycat.mycat.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/28 21:59
 * @Version 1.0
 **/
public interface UserRepository extends JpaRepository<UserEntity, Integer> {


    @Query("select c from UserEntity c where username = :username")
    List<UserEntity> getByUserName(@Param("username") String username);

//    @Query("select u from UserEntity u  where  u.orgid = o.id")
//    List<UserEntity> getUserHaveOrg();

}
