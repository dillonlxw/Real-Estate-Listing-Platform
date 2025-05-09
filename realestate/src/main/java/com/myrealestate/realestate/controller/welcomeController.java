package com.myrealestate.realestate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class welcomeController {

    @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("welcomeMessage", "Welcome");
        model.addAttribute("welcomeText", "Welcome to our Spring Real Estate Portal—your first step toward discovering the perfect property." + "\n\nExplore, connect, and get inspired as you embark on your home journey."); 
        return "welcome";
    }
}
