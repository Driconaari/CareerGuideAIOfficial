package com.careerguideaiofficial.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "interview_practices")
public class InterviewPractice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String question;
    private String answer;
    private String feedback;
    private LocalDateTime createdAt;

    // Getters and setters
}
