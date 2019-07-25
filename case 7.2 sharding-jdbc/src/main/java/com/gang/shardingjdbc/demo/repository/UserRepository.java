package com.gang.shardingjdbc.demo.repository;

import com.gang.shardingjdbc.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
