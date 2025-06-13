package com.acme.ezpark.platform.booking.domain.model.commands;

import java.math.BigDecimal;

public record CompleteBookingCommand(
    Long bookingId,
    BigDecimal finalPrice
) {
}
