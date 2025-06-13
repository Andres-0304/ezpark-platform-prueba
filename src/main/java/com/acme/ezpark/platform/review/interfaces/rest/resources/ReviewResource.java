package com.acme.ezpark.platform.review.interfaces.rest.resources;

import com.acme.ezpark.platform.review.domain.model.valueobjects.Rating;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * Review resource
 * This resource represents a review in the API responses
 * @author EzPark Team
 */
@Schema(description = "Review Resource")
public record ReviewResource(
        @Schema(description = "Review ID", example = "1")
        Long id,
        
        @Schema(description = "User ID who wrote the review", example = "1")
        @JsonProperty("user_id")
        Long userId,
        
        @Schema(description = "Parking ID being reviewed", example = "1")
        @JsonProperty("parking_id")
        Long parkingId,
        
        @Schema(description = "Booking ID that allows this review", example = "1")
        @JsonProperty("booking_id")
        Long bookingId,
        
        @Schema(description = "Rating given to the parking", example = "FIVE")
        Rating rating,
        
        @Schema(description = "Comment about the parking", example = "Great parking space!")
        String comment,
        
        @Schema(description = "Whether the review is active", example = "true")
        @JsonProperty("is_active")
        Boolean isActive,
        
        @Schema(description = "When the review was created", example = "2025-06-13T10:30:00.000Z")
        @JsonProperty("created_at")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        Date createdAt,
        
        @Schema(description = "When the review was last updated", example = "2025-06-13T10:30:00.000Z")
        @JsonProperty("updated_at")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        Date updatedAt
) {
}
