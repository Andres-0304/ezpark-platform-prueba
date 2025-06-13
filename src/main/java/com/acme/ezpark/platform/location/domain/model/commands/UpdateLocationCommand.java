package com.acme.ezpark.platform.location.domain.model.commands;

public record UpdateLocationCommand(
        Long locationId,
        String name,
        String address,
        Double latitude,
        Double longitude,
        String city,
        String district,
        String postalCode,
        String country
) {
}
