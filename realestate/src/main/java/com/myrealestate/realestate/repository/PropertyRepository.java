package com.myrealestate.realestate.repository;

import com.myrealestate.realestate.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    /**
     * Finds properties with price between minPrice and maxPrice (inclusive).
     * @param minPrice The minimum price.
     * @param maxPrice The maximum price.
     * @return A list of properties within the price range.
     */
    List<Property> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Finds the maximum price among all properties.
     * @return The maximum price, or null if no properties exist.
     */
    @Query("SELECT MAX(p.price) FROM Property p")
    BigDecimal findMaxPrice();

    /**
     * Finds properties by their status, case-insensitive.
     * @param status The property status (e.g., "For Sale", "For Rent").
     * @return A list of properties matching the status.
     */
    List<Property> findByStatusIgnoreCase(String status);

    /**
     * Finds properties by price range and status, case-insensitive.
     * @param minPrice The minimum price.
     * @param maxPrice The maximum price.
     * @param status The property status.
     * @return A list of properties matching the criteria.
     */
    List<Property> findByPriceBetweenAndStatusIgnoreCase(BigDecimal minPrice, BigDecimal maxPrice, String status);
}
