package com.careerguideaiofficial.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "skill_progress")
public class SkillProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "skill_name")
    private String skillName;

    @Column(name = "proficiency_level")
    private Integer proficiencyLevel;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Convenience method to get userId
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }
}