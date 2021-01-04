package com.myjpa.demo.repository;

import com.myjpa.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Query(value = "update UserEntity t set t.usertype=?1 where t.userid=?2")
    Integer updateUser(String userorg, Integer userid);

    //    @Query("select c from UserEntity c where username = :username :#{and }}")
    //    List<UserEntity> getByUserName(@Param("username") String username, @Param("orgid") String age);

    @Query("select c from UserEntity c where username = ?1")
    List<UserEntity> getByUserName1(String username);

    @Query("select u from UserEntity u ,OrgEntity o where  u.orgid = o.id")
    List<UserEntity> getUserHaveOrg();


}
