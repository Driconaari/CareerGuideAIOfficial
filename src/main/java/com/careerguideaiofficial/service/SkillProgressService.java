package com.careerguideaiofficial.service;

import com.careerguideaiofficial.model.SkillProgress;
import com.careerguideaiofficial.model.User;
import com.careerguideaiofficial.repository.SkillProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SkillProgressService {

    private final SkillProgressRepository skillProgressRepository;

    @Autowired
    public SkillProgressService(SkillProgressRepository skillProgressRepository) {
        this.skillProgressRepository = skillProgressRepository;
    }

    @Transactional
    public SkillProgress addOrUpdateSkillProgress(User user, String skillName, Integer proficiencyLevel) {
        Optional<SkillProgress> existingSkillProgress = skillProgressRepository.findByUser_IdAndSkillName(user.getId(), skillName);

        if (existingSkillProgress.isPresent()) {
            SkillProgress skillProgress = existingSkillProgress.get();
            skillProgress.setProficiencyLevel(proficiencyLevel);
            skillProgress.setUpdatedAt(LocalDateTime.now());
            return skillProgressRepository.save(skillProgress);
        } else {
            SkillProgress newSkillProgress = new SkillProgress();
            newSkillProgress.setUser(user);
            newSkillProgress.setSkillName(skillName);
            newSkillProgress.setProficiencyLevel(proficiencyLevel);
            newSkillProgress.setUpdatedAt(LocalDateTime.now());
            return skillProgressRepository.save(newSkillProgress);
        }
    }

    public List<SkillProgress> getUserSkillProgress(User user) {
        return skillProgressRepository.findByUser_Id(user.getId());
    }

    public List<SkillProgress> getTopUserSkills(User user) {
        return skillProgressRepository.findTopNByUser_IdOrderByProficiencyLevelDesc(user.getId());
    }

    @Transactional
    public void removeSkillProgress(User user, String skillName) {
        Optional<SkillProgress> skillProgress = skillProgressRepository.findByUser_IdAndSkillName(user.getId(), skillName);
        skillProgress.ifPresent(skillProgressRepository::delete);
    }

    @Transactional
    public void updateSkillProficiency(User user, String skillName, Integer newProficiencyLevel) {
        skillProgressRepository.updateProficiencyLevel(user.getId(), skillName, newProficiencyLevel);
    }

    public Double getUserAverageProficiency(User user) {
        return skillProgressRepository.findAverageProficiencyLevelByUser_Id(user.getId());
    }

    public List<String> getAllUniqueSkillNames() {
        return skillProgressRepository.findAllUniqueSkillNames();
    }

    public List<SkillProgress> getSkillsAboveProficiencyLevel(User user, Integer proficiencyLevel) {
        return skillProgressRepository.findByUser_IdAndProficiencyLevelGreaterThanEqual(user.getId(), proficiencyLevel);
    }

    @Transactional
    public void deleteAllUserSkillProgress(User user) {
        skillProgressRepository.deleteByUser_Id(user.getId());
    }
}