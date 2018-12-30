package com.security.demo.repository;

import com.security.demo.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles,Long> {

    Roles findByName(String name);
}
