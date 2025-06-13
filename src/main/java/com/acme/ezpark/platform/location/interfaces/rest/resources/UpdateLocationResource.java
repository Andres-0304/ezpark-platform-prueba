package com.acme.ezpark.platform.location.interfaces.rest.resources;

public record UpdateLocationResource(
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
