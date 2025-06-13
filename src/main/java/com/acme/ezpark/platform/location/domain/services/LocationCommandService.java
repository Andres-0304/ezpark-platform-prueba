package com.acme.ezpark.platform.location.domain.services;

import com.acme.ezpark.platform.location.domain.model.aggregates.Location;
import com.acme.ezpark.platform.location.domain.model.commands.*;

import java.util.Optional;

public interface LocationCommandService {
    Optional<Location> handle(CreateLocationCommand command);
    Optional<Location> handle(UpdateLocationCommand command);
    boolean handle(DeleteLocationCommand command);
    void deleteLocation(Long locationId);
}
