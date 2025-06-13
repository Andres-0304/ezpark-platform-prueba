package com.acme.ezpark.platform.user.interfaces.rest.resources;

import java.time.LocalDate;

public record CreateUserResource(
    String email,
    String password,
    String firstName,
    String lastName,
    String phone,
    LocalDate birthDate
) {
}
