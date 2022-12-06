package com.bvrsoftware.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bvrsoftware.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    void  deleteById(Long id);
    Page<User> findAllByOrderByFistNameAsc(Pageable pageable);
}