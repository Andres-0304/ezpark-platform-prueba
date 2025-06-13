package com.acme.ezpark.platform.review.interfaces.rest.transform;

import com.acme.ezpark.platform.review.domain.model.commands.CreateReviewCommand;
import com.acme.ezpark.platform.review.interfaces.rest.resources.CreateReviewResource;

/**
 * Create review command from resource assembler
 * This class transforms a CreateReviewResource into a CreateReviewCommand
 * @author EzPark Team
 */
public class CreateReviewCommandFromResourceAssembler {
    
    /**
     * Transform CreateReviewResource to CreateReviewCommand
     * @param resource the CreateReviewResource
     * @return CreateReviewCommand the command
     */
    public static CreateReviewCommand toCommandFromResource(CreateReviewResource resource) {
        return new CreateReviewCommand(
                resource.userId(),
                resource.parkingId(), 
                resource.bookingId(),
                resource.rating(),
                resource.comment()
        );
    }
}
