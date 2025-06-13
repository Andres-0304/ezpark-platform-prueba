package com.acme.ezpark.platform.review.domain.model.queries;

/**
 * Query to get all reviews written by a specific user
 */
public record GetReviewsByUserIdQuery(Long userId) {
}
