package com.acme.ezpark.platform.parking.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalTime;

public record UpdateParkingResource(
    String address,
    BigDecimal width,
    BigDecimal length,
    BigDecimal height,
    BigDecimal pricePerHour,
    String description,
    LocalTime availableFrom,
    LocalTime availableTo,
    String parkingType
) {
}
