package com.careerguideaiofficial.service;

import com.careerguideaiofficial.model.SkillProgress;
import com.careerguideaiofficial.model.User;
import com.careerguideaiofficial.repository.SkillProgressRepository;
import com.careerguideaiofficial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SkillProgressService {

    private final SkillProgressRepository skillProgressRepository;
    private final UserRepository userRepository;

    @Autowired
    public SkillProgressService(SkillProgressRepository skillProgressRepository, UserRepository userRepository) {
        this.skillProgressRepository = skillProgressRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public SkillProgress addOrUpdateSkillProgress(User user, String skillName, Integer proficiencyLevel) {
        Optional<SkillProgress> existingSkillProgress = skillProgressRepository.findByUserIdAndSkillName(user.getId(), skillName);

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
        return skillProgressRepository.findByUserId(user.getId());
    }

    public List<SkillProgress> getTopUserSkills(User user, int limit) {
        return skillProgressRepository.findTopNByUserIdOrderByProficiencyLevelDesc(user.getId(), limit);
    }

    @Transactional
    public void removeSkillProgress(User user, String skillName) {
        Optional<SkillProgress> skillProgress = skillProgressRepository.findByUserIdAndSkillName(user.getId(), skillName);
        skillProgress.ifPresent(skillProgressRepository::delete);
    }

    @Transactional
    public void updateSkillProficiency(User user, String skillName, Integer newProficiencyLevel) {
        skillProgressRepository.updateProficiencyLevel(user.getId(), skillName, newProficiencyLevel);
    }

    public Double getUserAverageProficiency(User user) {
        return skillProgressRepository.findAverageProficiencyLevelByUserId(user.getId());
    }

    public List<String> getAllUniqueSkillNames() {
        return skillProgressRepository.findAllUniqueSkillNames();
    }

    public List<SkillProgress> getSkillsAboveProficiencyLevel(User user, Integer proficiencyLevel) {
        return skillProgressRepository.findByUserIdAndProficiencyLevelGreaterThanEqual(user.getId(), proficiencyLevel);
    }

    @Transactional
    public void deleteAllUserSkillProgress(User user) {
        skillProgressRepository.deleteByUserId(user.getId());
    }
}