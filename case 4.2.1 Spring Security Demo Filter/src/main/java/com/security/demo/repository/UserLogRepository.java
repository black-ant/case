package com.security.demo.repository;

import com.security.demo.entity.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog,Long> {

     UserLog findBySn(int sn);
}
