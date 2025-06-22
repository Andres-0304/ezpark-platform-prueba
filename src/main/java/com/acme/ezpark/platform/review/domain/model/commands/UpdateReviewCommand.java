package com.acme.ezpark.platform.review.domain.model.commands;

import com.acme.ezpark.platform.review.domain.model.valueobjects.Rating;

/**
 * Command to update an existing review
 * Only the review author can update their review
 */
public record UpdateReviewCommand(
    Long reviewId,
    Long userId,
    Rating rating,
    String comment
) {
}
