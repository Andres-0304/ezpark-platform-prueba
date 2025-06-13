package com.acme.ezpark.platform.location.domain.model.queries;

public record GetLocationsNearbyQuery(
        Double latitude,
        Double longitude,
        Double radiusKm
) {
}
