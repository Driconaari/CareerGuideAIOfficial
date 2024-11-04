package com.careerguideaiofficial.controller;

import com.careerguideaiofficial.model.User;
import com.careerguideaiofficial.service.CareerCoachService;
import com.careerguideaiofficial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CareerCoachController {

    @Autowired
    private CareerCoachService careerCoachService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String username = auth.getName();
            User user = userService.findByUsername(username);

            if (user != null) {
                model.addAttribute("user", user);
                model.addAttribute("skillProgress", careerCoachService.getUserSkillProgress(user));
                model.addAttribute("resumes", careerCoachService.getUserResumes(user));
                model.addAttribute("interviewPractices", careerCoachService.getUserInterviewPractices(user));
                return "dashboard";
            }
        }

        return "redirect:/login";
    }

    /*
    @PostMapping("/review-resume")
    public String reviewResume(@RequestParam String resume, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User user = userService.findByUsername(auth.getName());
            String feedback = careerCoachService.getResumeReview(user, resume);
            model.addAttribute("feedback", feedback);
            return "dashboard";
        }
        return "redirect:/login";
    }

     */

    @PostMapping("/review-resume")
    public String reviewResume(@RequestParam String resume, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        String feedback = careerCoachService.getResumeReview(user, resume);
        model.addAttribute("feedback", feedback);
        return "dashboard";
    }

    @PostMapping("/interview-practice")
    public String interviewPractice(@RequestParam String question, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User user = userService.findByUsername(auth.getName());
            String response = careerCoachService.getInterviewResponse(user, question);
            model.addAttribute("response", response);
            return "dashboard";
        }
        return "redirect:/login";
    }

    @PostMapping("/skill-recommendations")
    public String skillRecommendations(@RequestParam String careerGoals, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User user = userService.findByUsername(auth.getName());
            String recommendations = careerCoachService.getSkillRecommendations(user, careerGoals);
            model.addAttribute("recommendations", recommendations);
            return "dashboard";
        }
        return "redirect:/login";
    }

    @PostMapping("/personality-guidance")
    public String personalityGuidance(@RequestParam String personalityType, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User user = userService.findByUsername(auth.getName());
            String guidance = careerCoachService.getPersonalityTypeGuidance(user, personalityType);
            model.addAttribute("guidance", guidance);
            return "dashboard";
        }
        return "redirect:/login";
    }
}