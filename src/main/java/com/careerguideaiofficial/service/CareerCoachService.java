package com.careerguideaiofficial.service;


import com.careerguideaiofficial.model.Resume;
import com.careerguideaiofficial.model.User;
import com.careerguideaiofficial.repository.InterviewPracticeRepository;
import com.careerguideaiofficial.repository.ResumeRepository;
import com.careerguideaiofficial.repository.SkillProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CareerCoachService {

    @Autowired
    private OpenAiService openAiService;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private InterviewPracticeRepository interviewPracticeRepository;

    @Autowired
    private SkillProgressRepository skillProgressRepository;

    public String getResumeReview(User user, String resumeContent) {
        String prompt = "Please review the following resume and provide constructive feedback for improvement:\n\n" + resumeContent;
        String feedback = openAiService.getAiResponse(prompt);

        Resume resume = new Resume();
        resume.setUser(user);
        resume.setContent(resumeContent);
        resume.setFeedback(feedback);
        resume.setCreatedAt(LocalDateTime.now());
        resume.setUpdatedAt(LocalDateTime.now());
        resumeRepository.save(resume);

        return feedback;
    }

    public String getInterviewResponse(User user, String question) {
        String prompt = "As an AI career coach, please provide a sample answer to the following interview question:\n\n" + question;
        String answer = openAiService.getAiResponse(prompt);

        InterviewPractice practice = new InterviewPractice();
        practice.setUser(user);
        practice.setQuestion(question);
        practice.setAnswer(answer);
        practice.setCreatedAt(LocalDateTime.now());
        interviewPracticeRepository.save(practice);

        return answer;
    }

    public String getSkillRecommendations(User user, String careerGoals) {
        String prompt = "Based on the following career goals, please recommend skills to develop:\n\n" + careerGoals;
        String recommendations = openAiService.getAiResponse(prompt);

        // Extract skills from recommendations and update user's skill progress
        // This is a simplified version, you might want to use NLP for better extraction
        String[] skills = recommendations.split(",");
        for (String skill : skills) {
            skill = skill.trim();
            SkillProgress progress = skillProgressRepository.findByUserIdAndSkillName(user.getId(), skill)
                    .orElse(new SkillProgress());
            progress.setUser(user);
            progress.setSkillName(skill);
            progress.setProficiencyLevel(1); // Start at beginner level
            progress.setUpdatedAt(LocalDateTime.now());
            skillProgressRepository.save(progress);
        }

        return recommendations;
    }

    public String getPersonalityTypeGuidance(User user, String personalityType) {
        String prompt = "Provide career guidance for someone with the following personality type:\n\n" + personalityType;
        String guidance = openAiService.getAiResponse(prompt);

        user.setPersonalityType(personalityType);
        // Assuming you have a userRepository
        // userRepository.save(user);

        return guidance;
    }

    public List<SkillProgress> getUserSkillProgress(User user) {
        return skillProgressRepository.findByUserId(user.getId());
    }

    public List<Resume> getUserResumes(User user) {
        return resumeRepository.findByUserId(user.getId());
    }

    public List<InterviewPractice> getUserInterviewPractices(User user) {
        return interviewPracticeRepository.findByUserId(user.getId());
    }
}