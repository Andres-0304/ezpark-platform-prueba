package com.acme.ezpark.platform.review.domain.model.commands;

import com.acme.ezpark.platform.review.domain.model.valueobjects.Rating;

/**
 * Command to create a new review for a parking
 * Can only be created by guests who have completed a booking
 */
public record CreateReviewCommand(
    Long userId,
    Long parkingId,
    Long bookingId,
    Rating rating,
    String comment
) {
}
