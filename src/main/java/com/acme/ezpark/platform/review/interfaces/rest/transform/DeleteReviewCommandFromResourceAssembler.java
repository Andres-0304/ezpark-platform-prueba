package com.acme.ezpark.platform.review.interfaces.rest.transform;

import com.acme.ezpark.platform.review.domain.model.commands.DeleteReviewCommand;
import com.acme.ezpark.platform.review.interfaces.rest.resources.DeleteReviewResource;

/**
 * Delete review command from resource assembler
 * This class transforms a DeleteReviewResource into a DeleteReviewCommand
 * @author EzPark Team
 */
public class DeleteReviewCommandFromResourceAssembler {
    
    /**
     * Transform DeleteReviewResource to DeleteReviewCommand
     * @param reviewId the review ID
     * @param resource the DeleteReviewResource
     * @return DeleteReviewCommand the command
     */
    public static DeleteReviewCommand toCommandFromResource(Long reviewId, DeleteReviewResource resource) {
        return new DeleteReviewCommand(
                reviewId,
                resource.userId()
        );
    }
}
