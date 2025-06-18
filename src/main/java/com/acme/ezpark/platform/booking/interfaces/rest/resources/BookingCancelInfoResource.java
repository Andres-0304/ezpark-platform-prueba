package com.acme.ezpark.platform.booking.interfaces.rest.resources;

import java.time.Instant;

/**
 * Resource for booking cancellation information
 * @author EzPark Team
 */
public record BookingCancelInfoResource(
    Boolean canCancel,
    Long minutesUntilDeadline,
    Instant cancelDeadline,
    String message
) {
}
