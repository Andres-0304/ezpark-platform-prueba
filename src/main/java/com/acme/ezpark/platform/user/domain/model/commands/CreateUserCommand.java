package com.acme.ezpark.platform.user.domain.model.commands;

import java.time.LocalDate;

public record CreateUserCommand(
    String email,
    String password,
    String firstName,
    String lastName,
    String phone,
    LocalDate birthDate
) {
}
