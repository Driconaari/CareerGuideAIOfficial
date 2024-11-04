package com.careerguideaiofficial.controller;

import com.careerguideaiofficial.model.User;
import com.careerguideaiofficial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/user-dashboard")
    public String userDashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            User user = userService.findByUsername(username);

            if (user != null) {
                model.addAttribute("user", user);
                return "user-dashboard";
            }
        }


        // If user is not authenticated or not found, redirect to login
        return "redirect:/login";
    }
}