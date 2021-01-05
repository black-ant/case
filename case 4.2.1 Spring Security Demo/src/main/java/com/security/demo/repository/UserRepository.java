package com.security.demo.repository;


import com.security.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<Users,Long> {

    Users findByUsername(String username);

    Users findById(Integer Id);

}
