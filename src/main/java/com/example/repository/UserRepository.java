package com.example.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // Username se search
    Optional<User> findByUsername(String username);

    // Email se search (add this!)
    Optional<User> findByEmail(String email);
}
