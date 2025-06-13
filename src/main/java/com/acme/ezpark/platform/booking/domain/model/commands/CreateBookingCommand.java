package com.acme.ezpark.platform.booking.domain.model.commands;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateBookingCommand(
    Long userId,
    Long parkingId,
    Long vehicleId,
    LocalDateTime startTime,
    LocalDateTime endTime,
    BigDecimal totalPrice,
    String notes
) {
}
