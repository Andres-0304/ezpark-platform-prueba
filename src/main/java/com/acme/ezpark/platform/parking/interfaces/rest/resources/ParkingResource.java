package com.acme.ezpark.platform.parking.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalTime;

public record ParkingResource(
    Long id,
    Long ownerId,
    String address,
    BigDecimal latitude,
    BigDecimal longitude,
    BigDecimal width,
    BigDecimal length,
    BigDecimal height,
    BigDecimal pricePerHour,
    String description,
    String imageUrl,
    LocalTime availableFrom,
    LocalTime availableTo,
    Boolean isAvailable,
    String parkingType
) {
}
