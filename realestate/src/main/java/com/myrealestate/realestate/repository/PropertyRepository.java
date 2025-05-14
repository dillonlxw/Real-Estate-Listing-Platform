// realestate\src\main\java\com\myrealestate\realestate\repository\PropertyRepository.java
package com.myrealestate.realestate.repository;

import com.myrealestate.realestate.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    // You can add custom query methods here if needed
    // For example: List<Property> findByType(String type);
}
