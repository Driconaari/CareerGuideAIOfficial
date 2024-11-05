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

    List<SkillProgress> findByUser_Id(Long userId);

    Optional<SkillProgress> findByUser_IdAndSkillName(Long userId, String skillName);

    void deleteByUser_Id(Long userId);

    boolean existsByUser_IdAndSkillName(Long userId, String skillName);

    List<SkillProgress> findByUser_IdOrderByProficiencyLevelDesc(Long userId);

    List<SkillProgress> findByUser_IdAndProficiencyLevelGreaterThanEqual(Long userId, Integer proficiencyLevel);

    @Query("SELECT sp FROM SkillProgress sp WHERE sp.user.id = :userId ORDER BY sp.proficiencyLevel DESC")
    List<SkillProgress> findTopNByUser_IdOrderByProficiencyLevelDesc(Long userId);

    @Modifying
    @Query("UPDATE SkillProgress sp SET sp.proficiencyLevel = :newProficiencyLevel WHERE sp.user.id = :userId AND sp.skillName = :skillName")
    int updateProficiencyLevel(Long userId, String skillName, Integer newProficiencyLevel);

    @Query("SELECT AVG(sp.proficiencyLevel) FROM SkillProgress sp WHERE sp.user.id = :userId")
    Double findAverageProficiencyLevelByUser_Id(Long userId);

    @Query("SELECT DISTINCT sp.skillName FROM SkillProgress sp")
    List<String> findAllUniqueSkillNames();
}