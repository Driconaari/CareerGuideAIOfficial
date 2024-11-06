package com.careerguideaiofficial.repository;

import com.careerguideaiofficial.model.SkillProgress;
import com.careerguideaiofficial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillProgressRepository extends JpaRepository<SkillProgress, Long> {

    List<SkillProgress> findByUser(User user);

    List<SkillProgress> findByUserId(Long userId);

    Optional<SkillProgress> findByUserAndSkillName(User user, String skillName);

    Optional<SkillProgress> findByUserIdAndSkillName(Long userId, String skillName);

    List<SkillProgress> findByUserOrderByProficiencyLevelDesc(User user);

    List<SkillProgress> findByUserIdOrderByProficiencyLevelDesc(Long userId);

    @Query("SELECT sp FROM SkillProgress sp WHERE sp.user.id = :userId AND sp.proficiencyLevel >= :minLevel")
    List<SkillProgress> findSkillsAboveLevel(@Param("userId") Long userId, @Param("minLevel") Integer minLevel);

    @Query("SELECT AVG(sp.proficiencyLevel) FROM SkillProgress sp WHERE sp.user.id = :userId")
    Double getAverageProficiencyLevel(@Param("userId") Long userId);

    @Query("SELECT sp.skillName FROM SkillProgress sp WHERE sp.user.id = :userId ORDER BY sp.proficiencyLevel DESC")
    List<String> findTopSkillsByUser(@Param("userId") Long userId);

    void deleteByUser(User user);

    void deleteByUserId(Long userId);
}