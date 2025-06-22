package com.acme.ezpark.platform.review.domain.model.queries;

/**
 * Query to get all reviews for a specific parking
 */
public record GetReviewsByParkingIdQuery(Long parkingId) {
}
