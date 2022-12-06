package com.bvrsoftware.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bvrsoftware.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
}
