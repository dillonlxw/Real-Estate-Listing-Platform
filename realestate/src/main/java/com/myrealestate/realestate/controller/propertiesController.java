package com.myrealestate.realestate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class propertiesController {
    
    @GetMapping("/properties")
    public String properties(Model model) {
        model.addAttribute("propertiesMessage", "Properties");
        return "properties";
    }
    
}
