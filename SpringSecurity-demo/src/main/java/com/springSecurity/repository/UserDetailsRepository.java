package com.springSecurity.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springSecurity.Entities.Users;

@Repository
public interface UserDetailsRepository extends JpaRepository<Users, Long> {
Users findByUsername(String username);
}
