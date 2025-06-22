package com.acme.ezpark.platform.review.domain.model.commands;

/**
 * Command to delete a review
 * Only the review author can delete their review
 */
public record DeleteReviewCommand(
    Long reviewId,
    Long userId
) {
}
