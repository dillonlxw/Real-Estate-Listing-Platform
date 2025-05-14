package com.myrealestate.realestate.controller;

import com.myrealestate.realestate.model.Property;
import com.myrealestate.realestate.repository.PropertyRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
public class propertiesController {

    private final PropertyRepository propertyRepository;
    private static final BigDecimal DEFAULT_MAX_PRICE_IF_EMPTY = BigDecimal.valueOf(5000000);
    private static final BigDecimal MIN_SLIDER_RANGE_MAX = BigDecimal.valueOf(10000);

    public propertiesController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/properties")
    public String properties(
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "sortOrder", required = false, defaultValue = "default") String sortOrder,
            Model model, RedirectAttributes redirectAttributes) {

        if (redirectAttributes.getFlashAttributes().containsKey("globalSuccessMessage")) {
            model.addAttribute("globalSuccessMessage", redirectAttributes.getFlashAttributes().get("globalSuccessMessage"));
        }
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

        Sort sort;
        if ("price,asc".equalsIgnoreCase(sortOrder)) {
            sort = Sort.by(Sort.Direction.ASC, "price");
        } else if ("price,desc".equalsIgnoreCase(sortOrder)) {
            sort = Sort.by(Sort.Direction.DESC, "price");
        } else {
            sort = Sort.unsorted();
        }

        List<Property> propertyList;
        boolean hasPriceFilter = minPrice != null && maxPrice != null;
        boolean hasStatusFilter = StringUtils.hasText(status);

        if (hasPriceFilter && minPrice.compareTo(maxPrice) > 0) {
            BigDecimal temp = minPrice;
            minPrice = maxPrice;
            maxPrice = temp;
        }

        if (hasPriceFilter && hasStatusFilter) {
            propertyList = propertyRepository.findByPriceBetweenAndStatusIgnoreCase(minPrice, maxPrice, status, sort);
        } else if (hasPriceFilter) {
            propertyList = propertyRepository.findByPriceBetween(minPrice, maxPrice, sort);
        } else if (hasStatusFilter) {
            propertyList = propertyRepository.findByStatusIgnoreCase(status, sort);
        } else {
            propertyList = propertyRepository.findAll(sort);
        }

        model.addAttribute("propertiesMessage", "Properties");
        model.addAttribute("properties", propertyList);
        model.addAttribute("maxOverallPrice", overallMaxPrice);
        model.addAttribute("currentMinPrice", (minPrice != null) ? minPrice : BigDecimal.ZERO);
        model.addAttribute("currentMaxPrice", (maxPrice != null) ? maxPrice : overallMaxPrice);
        model.addAttribute("currentStatus", (hasStatusFilter) ? status : "");
        model.addAttribute("currentSortOrder", sortOrder);

        if (!model.containsAttribute("newProperty")) {
            model.addAttribute("newProperty", new Property());
        }
        return "properties";
    }

    @PostMapping("/properties/add")
    public String addProperty(@ModelAttribute("newProperty") Property property, RedirectAttributes redirectAttributes) {
        // ... (existing add property logic, ensure it's complete as per previous steps) ...
        if (property.getPropertyName() == null || property.getPropertyName().trim().isEmpty() ||
            property.getAddress() == null || property.getAddress().trim().isEmpty() ||
            property.getPrice() == null || property.getPrice().compareTo(BigDecimal.ZERO) < 0 ||
            property.getType() == null || property.getType().trim().isEmpty() ||
            property.getStatus() == null || property.getStatus().trim().isEmpty()) {
             // Basic validation or rely on HTML5 'required'
        }
        
        propertyRepository.save(property);
        redirectAttributes.addFlashAttribute("globalSuccessMessage", "Property added successfully!");
        return "redirect:/properties";
    }

    @PostMapping("/properties/action/complete/{id}") // Renamed and made more generic
    public ResponseEntity<?> completePropertyAction(@PathVariable("id") Long id) {
        if (!propertyRepository.existsById(id)) {
            return ResponseEntity.status(404).body(Map.of("error", "Property not found."));
        }

        // The action is to remove the property from the list (delete)
        propertyRepository.deleteById(id);
        // Client-side will handle dynamic success message ("Rent successful" vs "Purchase successful")
        return ResponseEntity.ok(Map.of("message", "Property action completed successfully."));
    }
}
