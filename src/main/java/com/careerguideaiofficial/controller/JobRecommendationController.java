package com.careerguideaiofficial.controller;

import com.careerguideaiofficial.model.User;
import com.careerguideaiofficial.service.JobRecommendationService;
import com.careerguideaiofficial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class JobRecommendationController {

    private final JobRecommendationService jobRecommendationService;
    private final UserService userService;

    @Autowired
    public JobRecommendationController(JobRecommendationService jobRecommendationService, UserService userService) {
        this.jobRecommendationService = jobRecommendationService;
        this.userService = userService;
    }

    @GetMapping("/job-recommendations")
    public String showJobRecommendationsPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        if (user != null) {
            model.addAttribute("user", user);
            return "job-recommendations";
        }

        return "redirect:/login";
    }

    @PostMapping("/job-recommendations")
    public String getJobRecommendations(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        if (user != null) {
            List<String> recommendations = jobRecommendationService.getJobRecommendations(user);
            model.addAttribute("jobRecommendations", recommendations);
            model.addAttribute("user", user);
            return "job-recommendations";
        }

        return "redirect:/login";
    }
}