package com.myrealestate.realestate.repository;

import com.myrealestate.realestate.model.Property;
import org.springframework.data.domain.Sort; // Import Sort
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    /**
     * Finds properties with price between minPrice and maxPrice (inclusive), with sorting.
     * @param minPrice The minimum price.
     * @param maxPrice The maximum price.
     * @param sort Sorting criteria.
     * @return A list of properties within the price range, sorted.
     */
    List<Property> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Sort sort);

    /**
     * Finds the maximum price among all properties.
     * @return The maximum price, or null if no properties exist.
     */
    @Query("SELECT MAX(p.price) FROM Property p")
    BigDecimal findMaxPrice();

    /**
     * Finds properties by their status, case-insensitive, with sorting.
     * @param status The property status (e.g., "For Sale", "For Rent").
     * @param sort Sorting criteria.
     * @return A list of properties matching the status, sorted.
     */
    List<Property> findByStatusIgnoreCase(String status, Sort sort);

    /**
     * Finds properties by price range and status, case-insensitive, with sorting.
     * @param minPrice The minimum price.
     * @param maxPrice The maximum price.
     * @param status The property status.
     * @param sort Sorting criteria.
     * @return A list of properties matching the criteria, sorted.
     */
    List<Property> findByPriceBetweenAndStatusIgnoreCase(BigDecimal minPrice, BigDecimal maxPrice, String status, Sort sort);

    // JpaRepository already provides findAll(Sort sort)
}
