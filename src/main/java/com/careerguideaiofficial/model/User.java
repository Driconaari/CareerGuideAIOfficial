package com.careerguideaiofficial.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String passwordHash;
    private String personalityType;
    private String careerGoals;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and setters
}


