package com.myrealestate.realestate.controller;

import com.myrealestate.realestate.model.Property;
import com.myrealestate.realestate.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.util.StringUtils; // For StringUtils.hasText

import java.math.BigDecimal;
import java.util.List;

@Controller
public class propertiesController {

    private final PropertyRepository propertyRepository;
    private static final BigDecimal DEFAULT_MAX_PRICE_IF_EMPTY = BigDecimal.valueOf(5000000);
    private static final BigDecimal MIN_SLIDER_RANGE_MAX = BigDecimal.valueOf(10000);

    @Autowired
    public propertiesController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/properties")
    public String properties(
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(name = "status", required = false) String status,
            Model model) {

        BigDecimal maxDbPrice = propertyRepository.findMaxPrice();
        BigDecimal overallMaxPrice;

        if (maxDbPrice == null) {
            overallMaxPrice = DEFAULT_MAX_PRICE_IF_EMPTY;
        } else {
            overallMaxPrice = maxDbPrice;
        }
        if (overallMaxPrice.compareTo(MIN_SLIDER_RANGE_MAX) < 0) {
            overallMaxPrice = MIN_SLIDER_RANGE_MAX;
        }

        List<Property> propertyList;

        boolean hasPriceFilter = minPrice != null && maxPrice != null;
        boolean hasStatusFilter = StringUtils.hasText(status); // Check if status is not null and not empty

        if (hasPriceFilter && minPrice.compareTo(maxPrice) > 0) {
            BigDecimal temp = minPrice;
            minPrice = maxPrice;
            maxPrice = temp;
        }

        if (hasPriceFilter && hasStatusFilter) {
            propertyList = propertyRepository.findByPriceBetweenAndStatusIgnoreCase(minPrice, maxPrice, status);
        } else if (hasPriceFilter) {
            propertyList = propertyRepository.findByPriceBetween(minPrice, maxPrice);
        } else if (hasStatusFilter) {
            propertyList = propertyRepository.findByStatusIgnoreCase(status);
        } else {
            propertyList = propertyRepository.findAll();
        }

        model.addAttribute("propertiesMessage", "Properties");
        model.addAttribute("properties", propertyList);
        model.addAttribute("maxOverallPrice", overallMaxPrice);
        model.addAttribute("currentMinPrice", (minPrice != null) ? minPrice : BigDecimal.ZERO);
        model.addAttribute("currentMaxPrice", (maxPrice != null) ? maxPrice : overallMaxPrice);
        model.addAttribute("currentStatus", (hasStatusFilter) ? status : ""); // Pass empty string if no status filter

        return "properties";
    }

    // TODO: Add methods for adding properties
}
