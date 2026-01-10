package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    // 🔹 Ye method User ko username se search karega
    Optional<User> findByUsername(String username);
}
