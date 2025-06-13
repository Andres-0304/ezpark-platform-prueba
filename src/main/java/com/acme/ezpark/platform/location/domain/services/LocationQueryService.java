package com.acme.ezpark.platform.location.domain.services;

import com.acme.ezpark.platform.location.domain.model.aggregates.Location;
import com.acme.ezpark.platform.location.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface LocationQueryService {
    Optional<Location> handle(GetLocationByIdQuery query);
    List<Location> handle(GetAllLocationsQuery query);
    List<Location> handle(GetLocationsByCityQuery query);
    List<Location> handle(GetLocationsByDistrictQuery query);
    List<Location> handle(GetLocationsNearbyQuery query);
}
