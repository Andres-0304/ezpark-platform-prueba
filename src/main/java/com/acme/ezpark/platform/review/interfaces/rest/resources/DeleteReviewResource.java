package com.acme.ezpark.platform.review.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Delete review resource
 * This resource represents the data needed to delete a review
 * @author EzPark Team
 */
@Schema(description = "Delete Review Resource")
public record DeleteReviewResource(
        @Schema(description = "User ID (must be the owner of the review)", example = "1", required = true)
        @JsonProperty("user_id")
        Long userId
) {
}
