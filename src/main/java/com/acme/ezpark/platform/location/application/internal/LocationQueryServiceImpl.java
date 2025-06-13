package com.acme.ezpark.platform.location.application.internal;

import com.acme.ezpark.platform.location.domain.model.aggregates.Location;
import com.acme.ezpark.platform.location.domain.model.queries.*;
import com.acme.ezpark.platform.location.domain.services.LocationQueryService;
import com.acme.ezpark.platform.location.infrastructure.persistence.jpa.repositories.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationQueryServiceImpl implements LocationQueryService {

    private final LocationRepository locationRepository;

    public LocationQueryServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Optional<Location> handle(GetLocationByIdQuery query) {
        return locationRepository.findById(query.locationId());
    }

    @Override
    public List<Location> handle(GetAllLocationsQuery query) {
        return locationRepository.findByIsActiveTrue();
    }

    @Override
    public List<Location> handle(GetLocationsByCityQuery query) {
        return locationRepository.findByCityAndIsActiveTrue(query.city());
    }

    @Override
    public List<Location> handle(GetLocationsByDistrictQuery query) {
        return locationRepository.findByDistrictAndIsActiveTrue(query.district());
    }

    @Override
    public List<Location> handle(GetLocationsNearbyQuery query) {
        return locationRepository.findLocationsWithinRadius(
                query.latitude(),
                query.longitude(),
                query.radiusKm()
        );
    }
}
