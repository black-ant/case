package com.gang.junit.h2.demoh2.repository;

import com.gang.junit.h2.demoh2.entity.TestEntitiy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Classname TestRepository
 * @Description TODO
 * @Date 2020/10/12 16:39
 * @Created by zengzg
 */
@Repository
public interface TestRepository extends JpaRepository<TestEntitiy, String> {

}
