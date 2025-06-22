package com.acme.ezpark.platform.user.interfaces.rest.transform;

import com.acme.ezpark.platform.user.domain.model.commands.RemoveUserRoleCommand;
import com.acme.ezpark.platform.user.interfaces.rest.resources.RemoveUserRoleResource;

/**
 * Assembler to transform RemoveUserRoleResource to RemoveUserRoleCommand
 */
public class RemoveUserRoleCommandFromResourceAssembler {
    
    public static RemoveUserRoleCommand toCommandFromResource(RemoveUserRoleResource resource) {
        return new RemoveUserRoleCommand(
            resource.userId(),
            resource.roleToRemove()
        );
    }
}
