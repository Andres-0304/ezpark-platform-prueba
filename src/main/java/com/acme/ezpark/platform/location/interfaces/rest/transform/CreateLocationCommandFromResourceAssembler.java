package com.acme.ezpark.platform.location.interfaces.rest.transform;

import com.acme.ezpark.platform.location.domain.model.commands.CreateLocationCommand;
import com.acme.ezpark.platform.location.interfaces.rest.resources.CreateLocationResource;

public class CreateLocationCommandFromResourceAssembler {
    
    public static CreateLocationCommand toCommandFromResource(CreateLocationResource resource) {
        return new CreateLocationCommand(
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
