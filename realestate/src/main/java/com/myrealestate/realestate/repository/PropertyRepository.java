// realestate/src/main/java/com/myrealestate/realestate/repository/PropertyRepository.java
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
}
