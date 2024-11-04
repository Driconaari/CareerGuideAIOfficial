package com.careerguideaiofficial.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String activityType;
    private String description;
    private LocalDateTime timestamp;

}