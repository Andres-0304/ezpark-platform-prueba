package com.acme.ezpark.platform.review.interfaces.rest.transform;

import com.acme.ezpark.platform.review.domain.model.commands.UpdateReviewCommand;
import com.acme.ezpark.platform.review.interfaces.rest.resources.UpdateReviewResource;

/**
 * Update review command from resource assembler
 * This class transforms an UpdateReviewResource into an UpdateReviewCommand
 * @author EzPark Team
 */
public class UpdateReviewCommandFromResourceAssembler {
    
    /**
     * Transform UpdateReviewResource to UpdateReviewCommand
     * @param reviewId the review ID
     * @param resource the UpdateReviewResource
     * @return UpdateReviewCommand the command
     */
    public static UpdateReviewCommand toCommandFromResource(Long reviewId, UpdateReviewResource resource) {
        return new UpdateReviewCommand(
                reviewId,
                resource.userId(),
                resource.rating(),
                resource.comment()
        );
    }
}
