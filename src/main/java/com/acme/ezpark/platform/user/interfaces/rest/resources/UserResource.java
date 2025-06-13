package com.acme.ezpark.platform.user.interfaces.rest.resources;

import java.time.LocalDate;

public record UserResource(
    Long id,
    String email,
    String firstName,
    String lastName,
    String phone,
    LocalDate birthDate,
    String profilePicture,
    Boolean isActive,
    Boolean isVerified
) {
}
