package com.acme.ezpark.platform.user.domain.model.commands;

import com.acme.ezpark.platform.user.domain.model.valueobjects.UserRole;

/**
 * Command to remove a specific role from user
 * This will trigger cleanup of all role-specific data
 */
public record RemoveUserRoleCommand(
    Long userId,
    UserRole roleToRemove
) {
}
