package com.careerguideaiofficial.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CareerCoachService {

    @Autowired
    private OpenAiService openAiService;

    public String getResumeReview(String resume) {
        String prompt = "Please review the following resume and provide constructive feedback for improvement:\n\n" + resume;
        return openAiService.getAiResponse(prompt);
    }

    public String getInterviewResponse(String question) {
        String prompt = "As an AI career coach, please provide a sample answer to the following interview question:\n\n" + question;
        return openAiService.getAiResponse(prompt);
    }

    public String getSkillRecommendations(String careerGoals) {
        String prompt = "Based on the following career goals, please recommend skills to develop:\n\n" + careerGoals;
        return openAiService.getAiResponse(prompt);
    }

    public String getPersonalityTypeGuidance(String personalityType) {
        String prompt = "Provide career guidance for someone with the following personality type:\n\n" + personalityType;
        return openAiService.getAiResponse(prompt);
    }
}