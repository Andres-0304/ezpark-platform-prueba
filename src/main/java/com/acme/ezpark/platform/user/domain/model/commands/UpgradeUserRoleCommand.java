package com.acme.ezpark.platform.user.domain.model.commands;

import com.acme.ezpark.platform.user.domain.model.valueobjects.UserRole;

/**
 * Command to upgrade user role
 * Used when a user wants to upgrade from HOST to BOTH or GUEST to BOTH
 */
public record UpgradeUserRoleCommand(
    String email,
    UserRole requestedRole
) {
}
