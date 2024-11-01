package com.careerguideaiofficial.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "skill_progress")
public class SkillProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String skillName;
    private Integer proficiencyLevel;
    private LocalDateTime updatedAt;

    // Getters and setters
}
