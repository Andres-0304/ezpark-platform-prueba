package com.acme.ezpark.platform.review.interfaces.rest.transform;

import com.acme.ezpark.platform.review.interfaces.rest.resources.ParkingRatingSummaryResource;

/**
 * Parking rating summary resource assembler
 * This class creates ParkingRatingSummaryResource from data
 * @author EzPark Team
 */
public class ParkingRatingSummaryResourceAssembler {
    
    /**
     * Create ParkingRatingSummaryResource from parking data
     * @param parkingId the parking ID
     * @param averageRating the average rating
     * @param reviewCount the review count
     * @return ParkingRatingSummaryResource the resource
     */
    public static ParkingRatingSummaryResource toResource(Long parkingId, Double averageRating, Long reviewCount) {
        return new ParkingRatingSummaryResource(
                parkingId,
                averageRating,
                reviewCount
        );
    }
}
