package com.careerguideaiofficial.service;

import com.careerguideaiofficial.model.InterviewPractice;
import com.careerguideaiofficial.model.Resume;
import com.careerguideaiofficial.model.SkillProgress;
import com.careerguideaiofficial.model.User;
import com.careerguideaiofficial.repository.InterviewPracticeRepository;
import com.careerguideaiofficial.repository.ResumeRepository;
import com.careerguideaiofficial.repository.SkillProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CareerCoachService {

    private final OpenAiService openAiService;
    private final ResumeRepository resumeRepository;
    private final InterviewPracticeRepository interviewPracticeRepository;
    private final SkillProgressRepository skillProgressRepository;

    @Autowired
    public CareerCoachService(OpenAiService openAiService,
                              ResumeRepository resumeRepository,
                              InterviewPracticeRepository interviewPracticeRepository,
                              SkillProgressRepository skillProgressRepository) {
        this.openAiService = openAiService;
        this.resumeRepository = resumeRepository;
        this.interviewPracticeRepository = interviewPracticeRepository;
        this.skillProgressRepository = skillProgressRepository;
    }

    @Transactional
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

    @Transactional
    public String getInterviewResponse(User user, String question) {

        String prompt = "Provide a professional response to the following interview question:\n\n" + question;
        String response = openAiService.getAiResponse(prompt);

        InterviewPractice interviewPractice = new InterviewPractice();
        interviewPractice.setUser(user);
        interviewPractice.setQuestion(question);
        interviewPractice.setResponse(response);  // Calls setResponse, which saves it to 'answer'
        interviewPractice.setCreatedAt(LocalDateTime.now());

        interviewPracticeRepository.save(interviewPractice);

        return response;
    }


    @Transactional
    public String getSkillRecommendations(User user, String careerGoals) {
        String prompt = "Based on the following career goals, recommend skills to develop:\n\n" + careerGoals;
        String recommendations = openAiService.getAiResponse(prompt);

        // Extract skills from recommendations and update skill progress
        String[] skills = extractSkillsFromRecommendations(recommendations);
        for (String skill : skills) {
            updateUserSkillProgress(user, skill);
        }

        return recommendations;
    }

    private String[] extractSkillsFromRecommendations(String recommendations) {
        // This is a simple implementation. You might want to use more sophisticated NLP techniques.
        return recommendations.split(",");
    }

    @Transactional
    public void updateUserSkillProgress(User user, String skillName) {
        Optional<SkillProgress> existingSkill = skillProgressRepository.findByUser_IdAndSkillName(user.getId(), skillName);

        if (existingSkill.isPresent()) {
            SkillProgress skillProgress = existingSkill.get();
            skillProgress.setProficiencyLevel(skillProgress.getProficiencyLevel() + 1);
            skillProgress.setUpdatedAt(LocalDateTime.now());
            skillProgressRepository.save(skillProgress);
        } else {
            SkillProgress newSkillProgress = new SkillProgress();
            newSkillProgress.setUser(user);
            newSkillProgress.setSkillName(skillName);
            newSkillProgress.setProficiencyLevel(1);
            newSkillProgress.setUpdatedAt(LocalDateTime.now());
            skillProgressRepository.save(newSkillProgress);
        }
    }

    public List<SkillProgress> getUserSkillProgress(User user) {
        return skillProgressRepository.findByUser_Id(user.getId());
    }

    public List<Resume> getUserResumes(User user) {
        return resumeRepository.findByUserId(user.getId());
    }

    public List<InterviewPractice> getUserInterviewPractices(User user) {
        return interviewPracticeRepository.findByUserId(user.getId());
    }

    @Transactional
    public String getPersonalityTypeGuidance(User user, String personalityType) {
        String prompt = "Provide career guidance for someone with the following personality type:\n\n" + personalityType;
        return openAiService.getAiResponse(prompt);
    }
}