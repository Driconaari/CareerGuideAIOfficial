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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CareerCoachController {

    private final CareerCoachService careerCoachService;
    private final UserService userService;

    @Autowired
    public CareerCoachController(CareerCoachService careerCoachService, UserService userService) {
        this.careerCoachService = careerCoachService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User user = getAuthenticatedUser();
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("skillProgress", careerCoachService.getUserSkillProgress(user));
        model.addAttribute("resumes", careerCoachService.getUserResumes(user));
        model.addAttribute("interviewPractices", careerCoachService.getUserInterviewPractices(user));
        return "dashboard";
    }

    @PostMapping("/review-resume")
    public String reviewResume(@RequestParam String resume, Model model, RedirectAttributes redirectAttributes) {
        User user = getAuthenticatedUser();
        if (user == null) {
            return "redirect:/login";
        }

        try {
            String feedback = careerCoachService.getResumeReview(user, resume);
            model.addAttribute("feedback", feedback);
            return "dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred while reviewing your resume. Please try again later.");
            return "redirect:/dashboard";
        }
    }

    @PostMapping("/interview-practice")
    public String interviewPractice(@RequestParam String question, Model model, RedirectAttributes redirectAttributes) {
        User user = getAuthenticatedUser();
        if (user == null) {
            return "redirect:/login";
        }

        try {
            String response = careerCoachService.getInterviewResponse(user, question);
            model.addAttribute("response", response);
            return "dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred during interview practice. Please try again later.");
            return "redirect:/dashboard";
        }
    }

    @PostMapping("/skill-recommendations")
    public String skillRecommendations(@RequestParam String careerGoals, Model model, RedirectAttributes redirectAttributes) {
        User user = getAuthenticatedUser(); // This should return the logged-in user

        if (user == null) {
            return "redirect:/login";
        }

        try {
            String recommendations = careerCoachService.getSkillRecommendations(user, careerGoals);
            model.addAttribute("recommendations", recommendations);
            model.addAttribute("user", user); // Add user to the model
            return "dashboard"; // Ensure 'dashboard' is the correct view name
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred while getting skill recommendations. Please try again later.");
            return "redirect:/dashboard";
        }
    }



    @PostMapping("/personality-guidance")
    public String personalityGuidance(@RequestParam String personalityType, Model model, RedirectAttributes redirectAttributes) {
        User user = getAuthenticatedUser();
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "You must be logged in to get personality guidance.");
            return "redirect:/login";
        }

        try {
            String guidance = careerCoachService.getPersonalityTypeGuidance(user, personalityType);
            model.addAttribute("user", user);
            model.addAttribute("guidance", guidance);
            return "dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred while getting personality guidance. Please try again later.");
            return "redirect:/dashboard";
        }
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return (User) authentication.getPrincipal(); // Assuming User is the principal object
        }
        return null;
    }

}