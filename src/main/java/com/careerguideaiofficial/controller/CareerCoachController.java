package com.careerguideaiofficial.controller;

import com.careerguideaiofficial.model.SkillProgress;
import com.careerguideaiofficial.model.User;
import com.careerguideaiofficial.service.CareerCoachService;
import com.careerguideaiofficial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CareerCoachController {

    @Autowired
    private CareerCoachService careerCoachService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
        model.addAttribute("skillProgress", careerCoachService.getUserSkillProgress(currentUser));
        model.addAttribute("resumes", careerCoachService.getUserResumes(currentUser));
        model.addAttribute("interviewPractices", careerCoachService.getUserInterviewPractices(currentUser));
        return "dashboard";
    }

    @PostMapping("/review-resume")
    public String reviewResume(@RequestParam String resume, Model model) {
        User currentUser = userService.getCurrentUser();
        String feedback = careerCoachService.getResumeReview(currentUser, resume);
        model.addAttribute("feedback", feedback);
        return "dashboard";
    }

    @PostMapping("/interview-practice")
    public String interviewPractice(@RequestParam String question, Model model) {
        User currentUser = userService.getCurrentUser();
        String response = careerCoachService.getInterviewResponse(currentUser, question);
        model.addAttribute("response", response);
        return "dashboard";
    }

    @PostMapping("/skill-recommendations")
    public String skillRecommendations(@RequestParam String careerGoals, Model model) {
        User currentUser = userService.getCurrentUser();
        String recommendations = careerCoachService.getSkillRecommendations(currentUser, careerGoals);
        model.addAttribute("recommendations", recommendations);
        return "dashboard";
    }

    @PostMapping("/personality-guidance")
    public String personalityGuidance(@RequestParam String personalityType, Model model) {
        User currentUser = userService.getCurrentUser();
        String guidance = careerCoachService.getPersonalityTypeGuidance(currentUser, personalityType);
        model.addAttribute("guidance", guidance);
        return "dashboard";
    }

    @GetMapping("/api/skill-progress")
    @ResponseBody
    public List<SkillProgress> getSkillProgress() {
        User currentUser = userService.getCurrentUser();
        return careerCoachService.getUserSkillProgress(currentUser);
    }
}