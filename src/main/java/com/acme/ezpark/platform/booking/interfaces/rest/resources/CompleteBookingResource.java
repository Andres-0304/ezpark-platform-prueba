package com.acme.ezpark.platform.booking.interfaces.rest.resources;

import java.math.BigDecimal;

public record CompleteBookingResource(
    BigDecimal finalPrice
) {
}
