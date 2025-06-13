package com.acme.ezpark.platform.location.interfaces.rest.resources;

public record LocationResource(
        Long id,
        String name,
        String address,
        Double latitude,
        Double longitude,
        String city,
        String district,
        String postalCode,
        String country,
        Boolean isActive
) {
}
