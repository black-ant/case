package com.gang.cloud.template.demo.repository;

import com.gang.cloud.template.demo.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname NoDataAccountRepository
 * @Description TODO
 * @Created by zengzg
 */
@Component
public interface ProductRepository extends JpaRepository<ProductEntity, String> {


    @Query("select c from ProductEntity c where username = :username")
    List<ProductEntity> getByUserName(@Param("username") String username);

//    @Modifying
//    @Query(value = "update AccountEntity t set t.usertype=?1 where t.userid=?2")
//    Integer updateUser(String userorg, Integer userid);

    //    @Query("select c from UserEntity c where username = :username :#{and }}")
    //    List<UserEntity> getByUserName(@Param("username") String username, @Param("orgid") String age);

//    @Query("select u from AccountEntity u ,OrgEntity o where  u.orgid = o.id")
//    List<AccountEntity> getUserHaveOrg();

}
