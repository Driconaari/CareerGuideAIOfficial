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

    public void setResponse(String response) {
        this.answer = response;  // Store response in the 'answer' field
    }
}
