package com.example.entity;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    // ✅ Password will NOT appear in API responses
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // ✅ Add this!
    @NotBlank(message = "Password is required")
    private String password;

    private String role;
    private LocalDateTime createdAt;

    // Automatically set ID, createdAt, role
    @PrePersist
    public void onCreate() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        if (this.role == null) this.role = "USER";
    }

    // ------------------ Getters & Setters ------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
