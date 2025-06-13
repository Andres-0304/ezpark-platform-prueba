package com.acme.ezpark.platform.review.domain.services;

import com.acme.ezpark.platform.review.domain.model.aggregates.Review;
import com.acme.ezpark.platform.review.domain.model.queries.GetReviewByIdQuery;
import com.acme.ezpark.platform.review.domain.model.queries.GetReviewsByParkingIdQuery;
import com.acme.ezpark.platform.review.domain.model.queries.GetReviewsByUserIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Review query service interface
 * This interface defines the contract for review query operations
 * @author EzPark Team
 */
public interface ReviewQueryService {
    Optional<Review> handle(GetReviewByIdQuery query);
    List<Review> handle(GetReviewsByParkingIdQuery query);
    List<Review> handle(GetReviewsByUserIdQuery query);
    List<Review> getAllReviews();
    Double getAverageRatingByParkingId(Long parkingId);
    Long getReviewCountByParkingId(Long parkingId);
}
