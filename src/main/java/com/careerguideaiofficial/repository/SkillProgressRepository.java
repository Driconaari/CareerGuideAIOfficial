package com.careerguideaiofficial.repository;

import com.careerguideaiofficial.model.SkillProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillProgressRepository extends JpaRepository<SkillProgress, Long> {
    List<SkillProgress> findByUserId(Long userId);
}