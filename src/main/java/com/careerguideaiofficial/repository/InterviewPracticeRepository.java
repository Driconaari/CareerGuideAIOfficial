package com.careerguideaiofficial.repository;

import com.careerguideaiofficial.model.InterviewPractice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewPracticeRepository extends JpaRepository<InterviewPractice, Long> {
    List<InterviewPractice> findByUserId(Long userId);
}