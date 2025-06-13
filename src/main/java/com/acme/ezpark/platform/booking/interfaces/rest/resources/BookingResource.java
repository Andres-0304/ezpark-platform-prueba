package com.acme.ezpark.platform.booking.interfaces.rest.resources;

import com.acme.ezpark.platform.booking.domain.model.valueobjects.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookingResource(
    Long id,
    Long userId,
    Long parkingId,
    Long vehicleId,
    LocalDateTime startTime,
    LocalDateTime endTime,
    LocalDateTime actualStartTime,
    LocalDateTime actualEndTime,
    BookingStatus status,
    BigDecimal totalPrice,
    BigDecimal finalPrice,
    String notes,
    String cancellationReason
) {
}
