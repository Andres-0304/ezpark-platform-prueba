package com.acme.ezpark.platform.booking.interfaces.rest.resources;

import java.time.LocalDateTime;

/**
 * Resource for booking cancellation information
 * @author EzPark Team
 */
public record BookingCancelInfoResource(
    Boolean canCancel,
    Long minutesUntilDeadline,
    LocalDateTime cancelDeadline,
    String message
) {
}
