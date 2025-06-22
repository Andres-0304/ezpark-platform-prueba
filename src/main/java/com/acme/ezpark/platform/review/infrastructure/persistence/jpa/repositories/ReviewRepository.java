package com.acme.ezpark.platform.review.infrastructure.persistence.jpa.repositories;

import com.acme.ezpark.platform.review.domain.model.aggregates.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Review repository interface
 * This interface extends JpaRepository to provide CRUD operations for Review entities.
 * @author EzPark Team
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    /**
     * Find review by ID and active status
     * @param id the review ID
     * @return Optional<Review> the review if found and active
     */
    Optional<Review> findByIdAndIsActiveTrue(Long id);
    
    /**
     * Find all active reviews by parking ID
     * @param parkingId the parking ID
     * @return List<Review> list of active reviews for the parking
     */
    List<Review> findByParkingIdAndIsActiveTrueOrderByCreatedAtDesc(Long parkingId);
    
    /**
     * Find all active reviews by user ID
     * @param userId the user ID
     * @return List<Review> list of active reviews by the user
     */
    List<Review> findByUserIdAndIsActiveTrueOrderByCreatedAtDesc(Long userId);
    
    /**
     * Find review by booking ID
     * @param bookingId the booking ID
     * @return Optional<Review> the review if found
     */
    Optional<Review> findByBookingIdAndIsActiveTrue(Long bookingId);
    
    /**
     * Check if a review exists for a specific booking
     * @param bookingId the booking ID
     * @return boolean true if review exists
     */
    boolean existsByBookingIdAndIsActiveTrue(Long bookingId);
    
    /**
     * Get average rating for a parking
     * @param parkingId the parking ID
     * @return Double the average rating
     */
    @Query("SELECT AVG(CASE r.rating " +
           "WHEN 'ONE' THEN 1 " +
           "WHEN 'TWO' THEN 2 " +
           "WHEN 'THREE' THEN 3 " +
           "WHEN 'FOUR' THEN 4 " +
           "WHEN 'FIVE' THEN 5 " +
           "ELSE 0 END) " +
           "FROM Review r WHERE r.parkingId = :parkingId AND r.isActive = true")
    Double findAverageRatingByParkingId(@Param("parkingId") Long parkingId);
    
    /**
     * Count reviews for a parking
     * @param parkingId the parking ID
     * @return Long the count of reviews
     */
    Long countByParkingIdAndIsActiveTrue(Long parkingId);
    
    /**
     * Find all active reviews
     * @return List<Review> list of all active reviews
     */
    List<Review> findByIsActiveTrueOrderByCreatedAtDesc();
}
