// realestate/src/main/java/com/myrealestate/realestate/controller/propertiesController.java
package com.myrealestate.realestate.controller;

import com.myrealestate.realestate.model.Property;
import com.myrealestate.realestate.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class propertiesController {

    private final PropertyRepository propertyRepository;
    private static final BigDecimal DEFAULT_MAX_PRICE_IF_EMPTY = BigDecimal.valueOf(5000000); // Default if DB is empty or no prices
    private static final BigDecimal MIN_SLIDER_RANGE_MAX = BigDecimal.valueOf(10000); // Ensures slider has some visual range

    @Autowired
    public propertiesController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/properties")
    public String properties(
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
            Model model) {

        BigDecimal maxDbPrice = propertyRepository.findMaxPrice();
        BigDecimal overallMaxPrice;

        if (maxDbPrice == null) {
            overallMaxPrice = DEFAULT_MAX_PRICE_IF_EMPTY;
        } else {
            overallMaxPrice = maxDbPrice;
        }
        // Ensure the slider has a minimum visual range if maxDbPrice is very low or zero
        if (overallMaxPrice.compareTo(MIN_SLIDER_RANGE_MAX) < 0) {
            overallMaxPrice = MIN_SLIDER_RANGE_MAX;
        }


        List<Property> propertyList;
        if (minPrice != null && maxPrice != null) {
            // Ensure minPrice is not greater than maxPrice for the query
            if (minPrice.compareTo(maxPrice) > 0) {
                // Swap if minPrice is greater than maxPrice (or handle as an error)
                BigDecimal temp = minPrice;
                minPrice = maxPrice;
                maxPrice = temp;
            }
            propertyList = propertyRepository.findByPriceBetween(minPrice, maxPrice);
        } else {
            propertyList = propertyRepository.findAll();
        }

        model.addAttribute("propertiesMessage", "Properties");
        model.addAttribute("properties", propertyList);
        model.addAttribute("maxOverallPrice", overallMaxPrice);
        // Set current filter values for the view, defaulting if not provided
        model.addAttribute("currentMinPrice", (minPrice != null) ? minPrice : BigDecimal.ZERO);
        model.addAttribute("currentMaxPrice", (maxPrice != null) ? maxPrice : overallMaxPrice);

        return "properties";
    }

    // TODO: Add methods for adding properties
}
