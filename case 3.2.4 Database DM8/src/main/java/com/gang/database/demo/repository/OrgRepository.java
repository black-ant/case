package com.gang.database.demo.repository;


import com.gang.study.wycat.mycat.entity.OrgEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Classname OrgRepository
 * @Description TODO
 * @Date 2021/1/16 16:35
 * @Created by zengzg
 */
public interface OrgRepository extends JpaRepository<OrgEntity, Integer> {

    OrgEntity findFirstByOrgname(String orgName);
}
