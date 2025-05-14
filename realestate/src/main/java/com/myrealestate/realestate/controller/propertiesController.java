// realestate\src\main\java\com\myrealestate\realestate\controller\propertiesController.java
package com.myrealestate.realestate.controller;

import com.myrealestate.realestate.model.Property;
import com.myrealestate.realestate.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class propertiesController {

    private final PropertyRepository propertyRepository;

    @Autowired
    public propertiesController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/properties")
    public String properties(Model model) {
        List<Property> propertyList = propertyRepository.findAll();
        model.addAttribute("propertiesMessage", "Properties");
        model.addAttribute("properties", propertyList); // Add the list of properties to the model
        return "properties";
    }

    // TODO: Add methods for adding/filtering properties
}
