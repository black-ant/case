package com.mycache.encache.repository;

import com.mycache.encache.entity.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/3/7 22:38
 * @Version 1.0
 **/
public interface DepartmentsRepository extends JpaRepository<Departments,Long> {

    Departments findByDeptname(String deptName);

    Departments findByDeptnameAndDeptno(String deptName, String deptNo);

    @Query("from Departments u where u.deptname=:deptName")
    Departments findDepartments(@Param("deptName") String deptName);

    @Query("from Departments u where (u.deptname=:deptName or :deptName is null ) and (u.deptno=:deptno or :deptno is null )")
    Departments findFilters(@Param("deptName") String deptName,@Param("deptno") String deptno);

}
