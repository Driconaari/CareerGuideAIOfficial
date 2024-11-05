package com.careerguideaiofficial.repository;

import com.careerguideaiofficial.model.SkillProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillProgressRepository extends JpaRepository<SkillProgress, Long> {

    List<SkillProgress> findByUserId(Long userId);

    Optional<SkillProgress> findByUserIdAndSkillName(Long userId, String skillName);

    void deleteByUserId(Long userId);

    boolean existsByUserIdAndSkillName(Long userId, String skillName);

    List<SkillProgress> findByUserIdOrderByProficiencyLevelDesc(Long userId);

    List<SkillProgress> findByUserIdAndProficiencyLevelGreaterThanEqual(Long userId, Integer proficiencyLevel);

    @Query("SELECT sp FROM SkillProgress sp WHERE sp.user.id = :userId ORDER BY sp.proficiencyLevel DESC LIMIT :limit")
    List<SkillProgress> findTopNByUserIdOrderByProficiencyLevelDesc(Long userId, int limit);

    @Modifying
    @Query("UPDATE SkillProgress sp SET sp.proficiencyLevel = :newProficiencyLevel WHERE sp.user.id = :userId AND sp.skillName = :skillName")
    int updateProficiencyLevel(Long userId, String skillName, Integer newProficiencyLevel);

    @Query("SELECT AVG(sp.proficiencyLevel) FROM SkillProgress sp WHERE sp.user.id = :userId")
    Double findAverageProficiencyLevelByUserId(Long userId);

    @Query("SELECT DISTINCT sp.skillName FROM SkillProgress sp")
    List<String> findAllUniqueSkillNames();
}