package com.acme.ezpark.platform.booking.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateBookingResource(
    Long parkingId,
    Long vehicleId,
    LocalDateTime startTime,
    LocalDateTime endTime,
    BigDecimal totalPrice,
    String notes
) {
}
