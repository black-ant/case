package com.myjpa.demo.repository;

import com.myjpa.demo.entity.OrgEntity;
import com.myjpa.demo.entity.api.IOrgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Classname OrgRepository
 * @Description TODO
 * @Date 2020/8/21 11:05
 * @Created by zengzg
 */
public interface OrgRepository extends JpaRepository<OrgEntity, Integer> {

    List<IOrgEntity> findByOrgname(String orgName);

    List<IOrgEntity> findByOrOrgnameLike(String orgName);

}
