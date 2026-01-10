package com.example.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "complaint")
public class Complaint {

    @Id
    @Column(length =36, nullable = false, updatable = false)
    private String id;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description too long")
    private String description;

    private String status; // PENDING, IN_PROGRESS, RESOLVED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    public void onCreate() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.status = "PENDING";
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters & Setters
    public String getId() { return id; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
