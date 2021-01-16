package com.gang.study.multiplysource.jpa.repositorytwo;

import com.gang.study.multiplysource.entity.UserEntity;
import com.gang.study.multiplysource.entityTwo.OrgEntity;
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
