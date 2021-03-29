package com.gang.cloud.template.demo.repository;

import com.gang.cloud.template.demo.entity.AccountEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname NoDataAccountRepository
 * @Description TODO
 * @Created by zengzg
 */
@Component
public interface AccountRepository extends JpaRepository<AccountEntity, String> {


    @Query("select c from AccountEntity c where accountName = :accountName")
    List<AccountEntity> getByUserName(@Param("accountName") String accountName);

//    @Modifying
//    @Query(value = "update AccountEntity t set t.usertype=?1 where t.userid=?2")
//    Integer updateUser(String userorg, Integer userid);

    //    @Query("select c from UserEntity c where username = :username :#{and }}")
    //    List<UserEntity> getByUserName(@Param("username") String username, @Param("orgid") String age);

//    @Query("select u from AccountEntity u ,OrgEntity o where  u.orgid = o.id")
//    List<AccountEntity> getUserHaveOrg();

}
