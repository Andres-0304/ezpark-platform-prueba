package com.acme.ezpark.platform.user.interfaces.rest.resources;

import com.acme.ezpark.platform.user.domain.model.valueobjects.UserRole;

/**
 * Resource for removing a specific role from user
 * This will trigger cleanup of all role-specific data
 */
public record RemoveUserRoleResource(
    Long userId,
    UserRole roleToRemove
) {
}
