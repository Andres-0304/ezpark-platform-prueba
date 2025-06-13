package com.acme.ezpark.platform.location.interfaces.rest.transform;

import com.acme.ezpark.platform.location.domain.model.commands.UpdateLocationCommand;
import com.acme.ezpark.platform.location.interfaces.rest.resources.UpdateLocationResource;

public class UpdateLocationCommandFromResourceAssembler {
    
    public static UpdateLocationCommand toCommandFromResource(Long locationId, UpdateLocationResource resource) {
        return new UpdateLocationCommand(
                locationId,
                resource.name(),
                resource.address(),
                resource.latitude(),
                resource.longitude(),
                resource.city(),
                resource.district(),
                resource.postalCode(),
                resource.country()
        );
    }
}
