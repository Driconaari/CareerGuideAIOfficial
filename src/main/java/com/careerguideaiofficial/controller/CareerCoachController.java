package com.careerguideaiofficial.controller;

import com.careerguideaiofficial.model.User;
import com.careerguideaiofficial.service.CareerCoachService;
import com.careerguideaiofficial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CareerCoachController {

    @Autowired
    private CareerCoachService careerCoachService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Assume we have a logged-in user
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
        return "dashboard";
    }

    @PostMapping("/review-resume")
    public String reviewResume(@RequestParam String resume, Model model) {
        String feedback = careerCoachService.getResumeReview(resume);
        model.addAttribute("feedback", feedback);
        return "dashboard";
    }

    @PostMapping("/interview-practice")
    public String interviewPractice(@RequestParam String question, Model model) {
        String response = careerCoachService.getInterviewResponse(question);
        model.addAttribute("response", response);
        return "dashboard";
    }

    @PostMapping("/skill-recommendations")
    public String skillRecommendations(@RequestParam String careerGoals, Model model) {
        String recommendations = careerCoachService.getSkillRecommendations(careerGoals);
        model.addAttribute("recommendations", recommendations);
        return "dashboard";
    }

    @PostMapping("/personality-guidance")
    public String personalityGuidance(@RequestParam String personalityType, Model model) {
        String guidance = careerCoachService.getPersonalityTypeGuidance(personalityType);
        model.addAttribute("guidance", guidance);
        return "dashboard";
    }
}