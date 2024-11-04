package com.careerguideaiofficial.controller;


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

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);

        if (user == null) {
            // Handle the case where user is not found
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "dashboard";
    }
}