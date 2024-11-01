package com.careerguideaiofficial.controller;


import com.careerguideaiofficial.service.CareerCoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CareerCoachController {

    @Autowired
    private CareerCoachService careerCoachService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Add any necessary data to the model
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
}