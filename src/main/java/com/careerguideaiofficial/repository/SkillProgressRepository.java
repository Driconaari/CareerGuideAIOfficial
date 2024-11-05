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

    /**
     * Find all SkillProgress entries for a given user ID.
     *
     * @param userId the ID of the user
     * @return a list of SkillProgress entries
     */
    List<SkillProgress> findByUserId(Long userId);

    /**
     * Find a SkillProgress entry for a given user ID and skill name.
     *
     * @param userId    the ID of the user
     * @param skillName the name of the skill
     * @return an Optional containing the SkillProgress if found, or empty if not found
     */
    Optional<SkillProgress> findByUserIdAndSkillName(Long userId, String skillName);

    /**
     * Delete all SkillProgress entries for a given user ID.
     *
     * @param userId the ID of the user
     */
    void deleteByUserId(Long userId);

    /**
     * Check if a SkillProgress entry exists for a given user ID and skill name.
     *
     * @param userId    the ID of the user
     * @param skillName the name of the skill
     * @return true if the entry exists, false otherwise
     */
    boolean existsByUserIdAndSkillName(Long userId, String skillName);

    /**
     * Find all SkillProgress entries for a given user ID, ordered by proficiency level in descending order.
     *
     * @param userId the ID of the user
     * @return a list of SkillProgress entries ordered by proficiency level
     */
    List<SkillProgress> findByUserIdOrderByProficiencyLevelDesc(Long userId);

    /**
     * Find all SkillProgress entries for a given user ID with a proficiency level greater than or equal to the given level.
     *
     * @param userId           the ID of the user
     * @param proficiencyLevel the minimum proficiency level
     * @return a list of SkillProgress entries meeting the criteria
     */
    List<SkillProgress> findByUserIdAndProficiencyLevelGreaterThanEqual(Long userId, Integer proficiencyLevel);


    @Query("SELECT sp FROM SkillProgress sp WHERE sp.userId = :userId ORDER BY sp.proficiencyLevel DESC LIMIT :limit")
    List<SkillProgress> findTopNByUserIdOrderByProficiencyLevelDesc(Long userId, int limit);

    @Modifying
    @Query("UPDATE SkillProgress sp SET sp.proficiencyLevel = :newProficiencyLevel WHERE sp.userId = :userId AND sp.skillName = :skillName")
    int updateProficiencyLevel(Long userId, String skillName, Integer newProficiencyLevel);

    @Query("SELECT AVG(sp.proficiencyLevel) FROM SkillProgress sp WHERE sp.userId = :userId")
    Double findAverageProficiencyLevelByUserId(Long userId);

    @Query("SELECT DISTINCT sp.skillName FROM SkillProgress sp")
    List<String> findAllUniqueSkillNames();

}