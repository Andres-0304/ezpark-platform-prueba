package com.acme.ezpark.platform.user.interfaces.rest.resources;

import com.acme.ezpark.platform.user.domain.model.valueobjects.UserRole;

/**
 * Resource for upgrading user role
 * Used when a user wants to access features from the other app
 */
public record UpgradeUserRoleResource(
    String email,
    UserRole requestedRole
) {
}
