package com.acme.ezpark.platform.location.domain.model.commands;

public record CreateLocationCommand(
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
