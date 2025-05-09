package com.myrealestate.realestate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class aboutController {

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("aboutMessage", "About Spring Real Estate");
        model.addAttribute("aboutText", "Welcome to Spring Real Estate, where finding your dream property is just a click away. " + "We've built a modern platform that connects buyers, sellers, and agents through innovative technology. " + "\n\nOur mission is to simplify the real estate journey by providing a transparent, user-friendly experience for everyone involved. Whether you're looking to buy your first home, sell a property, or grow your portfolio, our platform offers the tools and resources you need. " + "\n\nWith our dedicated team of real estate professionals and technology experts, we're committed to revolutionizing how people interact with real estate. We combine industry expertise with cutting-edge technology to deliver a seamless experience from search to closing. " + "\n\nThank you for choosing Spring Real Estate for your property needs. We look forward to helping you find the perfect place to call home.");
        return "about";
    }
}
