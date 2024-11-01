package com.careerguideaiofficial.service;

import com.careerguideaiofficial.model.InterviewPractice;
import com.careerguideaiofficial.model.Resume;
import com.careerguideaiofficial.model.SkillProgress;
import com.careerguideaiofficial.model.User;
import com.careerguideaiofficial.repository.InterviewPracticeRepository;
import com.careerguideaiofficial.repository.ResumeRepository;
import com.careerguideaiofficial.repository.SkillProgressRepository;
import com.careerguideaiofficial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class CareerCoachService {

    private final OpenAiService openAiService;
    private final ResumeRepository resumeRepository;
    private final InterviewPracticeRepository interviewPracticeRepository;
    private final SkillProgressRepository skillProgressRepository;
    private final UserRepository userRepository;

    @Autowired
    public CareerCoachService(OpenAiService openAiService,
                              ResumeRepository resumeRepository,
                              InterviewPracticeRepository interviewPracticeRepository,
                              SkillProgressRepository skillProgressRepository,
                              UserRepository userRepository) {
        this.openAiService = openAiService;
        this.resumeRepository = resumeRepository;
        this.interviewPracticeRepository = interviewPracticeRepository;
        this.skillProgressRepository = skillProgressRepository;
        this.userRepository = userRepository;
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

    @Transactional
    public String getSkillRecommendations(User user, String careerGoals) {
        String prompt = "Based on the following career goals, please recommend skills to develop:\n\n" + careerGoals;
        String recommendations = openAiService.getAiResponse(prompt);

        List<String> skills = Arrays.stream(recommendations.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        for (String skill : skills) {
            SkillProgress progress = skillProgressRepository.findByUserIdAndSkillName(user.getId(), skill)
                    .orElse(new SkillProgress());
            progress.setUser(user);
            progress.setSkillName(skill);
            progress.setProficiencyLevel(progress.getProficiencyLevel() == null ? 1 : progress.getProficiencyLevel());
            progress.setUpdatedAt(LocalDateTime.now());
            skillProgressRepository.save(progress);
        }

        return recommendations;
    }

    @Transactional
    public String getPersonalityTypeGuidance(User user, String personalityType) {
        String prompt = "Provide career guidance for someone with the following personality type:\n\n" + personalityType;
        String guidance = openAiService.getAiResponse(prompt);

        user.setPersonalityType(personalityType);
        userRepository.save(user);

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