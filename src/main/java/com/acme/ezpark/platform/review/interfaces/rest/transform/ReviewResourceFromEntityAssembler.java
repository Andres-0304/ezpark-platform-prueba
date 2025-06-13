package com.acme.ezpark.platform.review.interfaces.rest.transform;

import com.acme.ezpark.platform.review.domain.model.aggregates.Review;
import com.acme.ezpark.platform.review.interfaces.rest.resources.ReviewResource;

/**
 * Review resource from entity assembler
 * This class transforms a Review entity into a ReviewResource
 * @author EzPark Team
 */
public class ReviewResourceFromEntityAssembler {
    
    /**
     * Transform Review entity to ReviewResource
     * @param entity the Review entity
     * @return ReviewResource the resource
     */
    public static ReviewResource toResourceFromEntity(Review entity) {
        return new ReviewResource(
                entity.getId(),
                entity.getUserId(),
                entity.getParkingId(),
                entity.getBookingId(),
                entity.getRating(),
                entity.getComment(),
                entity.getIsActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
