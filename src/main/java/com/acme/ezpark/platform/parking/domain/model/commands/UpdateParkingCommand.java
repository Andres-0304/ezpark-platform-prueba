package com.acme.ezpark.platform.parking.domain.model.commands;

import java.math.BigDecimal;
import java.time.LocalTime;

public record UpdateParkingCommand(
    Long parkingId,
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
