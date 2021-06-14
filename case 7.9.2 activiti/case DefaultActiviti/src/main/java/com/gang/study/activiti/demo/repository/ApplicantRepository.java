package com.gang.study.activiti.demo.repository;

import com.gang.study.activiti.demo.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

}
