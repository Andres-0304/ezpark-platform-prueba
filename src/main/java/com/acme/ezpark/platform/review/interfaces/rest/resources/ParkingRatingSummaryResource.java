package com.acme.ezpark.platform.review.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Parking rating summary resource
 * This resource represents the rating statistics for a parking
 * @author EzPark Team
 */
@Schema(description = "Parking Rating Summary Resource")
public record ParkingRatingSummaryResource(
        @Schema(description = "Parking ID", example = "1")
        @JsonProperty("parking_id")
        Long parkingId,
        
        @Schema(description = "Average rating", example = "4.25")
        @JsonProperty("average_rating")
        Double averageRating,
        
        @Schema(description = "Total number of reviews", example = "12")
        @JsonProperty("review_count")
        Long reviewCount
) {
}
