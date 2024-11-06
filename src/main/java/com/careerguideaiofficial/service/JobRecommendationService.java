package com.careerguideaiofficial.service;

import com.careerguideaiofficial.model.User;
import com.careerguideaiofficial.model.SkillProgress;
import com.careerguideaiofficial.repository.SkillProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobRecommendationService {

    private final OpenAiService openAiService;
    private final SkillProgressRepository skillProgressRepository;

    @Autowired
    public JobRecommendationService(OpenAiService openAiService, SkillProgressRepository skillProgressRepository) {
        this.openAiService = openAiService;
        this.skillProgressRepository = skillProgressRepository;
    }

    public List<String> getJobRecommendations(User user) {
        List<SkillProgress> userSkills = skillProgressRepository.findByUserId(user.getId());

        if (userSkills.isEmpty()) {
            return List.of("No skills found. Please add some skills to get job recommendations.");
        }

        StringBuilder skillsPrompt = new StringBuilder("Based on the following skills and proficiency levels, recommend 5 suitable job positions:\n\n");
        for (SkillProgress skill : userSkills) {
            skillsPrompt.append(skill.getSkillName()).append(" (Level ").append(skill.getProficiencyLevel()).append(")\n");
        }
        skillsPrompt.append("\nPlease provide the job recommendations in a numbered list format.");

        String aiResponse = openAiService.getAiResponse(skillsPrompt.toString());
        return parseJobRecommendations(aiResponse);
    }

    private List<String> parseJobRecommendations(String aiResponse) {
        List<String> recommendations = new ArrayList<>();
        String[] lines = aiResponse.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.matches("\\d+\\..*")) {
                recommendations.add(line.substring(line.indexOf(".") + 1).trim());
            }
        }
        return recommendations;
    }
}