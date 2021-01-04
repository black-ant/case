package com.chapterJPA.demo.entity.repository;

import com.chapterJPA.demo.entity.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepartmentsRepository extends JpaRepository<Departments,Long> {

    Departments findByDeptname(String deptName);

    Departments findByDeptnameAndDeptno(String deptName, String deptNo);

    @Query("from Departments u where u.deptname=:deptName")
    Departments findDepartments(@Param("deptName") String deptName);

}
