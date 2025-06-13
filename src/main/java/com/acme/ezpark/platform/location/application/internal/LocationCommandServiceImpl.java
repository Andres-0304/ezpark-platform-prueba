package com.acme.ezpark.platform.location.application.internal;

import com.acme.ezpark.platform.location.domain.model.aggregates.Location;
import com.acme.ezpark.platform.location.domain.model.commands.*;
import com.acme.ezpark.platform.location.domain.services.LocationCommandService;
import com.acme.ezpark.platform.location.infrastructure.persistence.jpa.repositories.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationCommandServiceImpl implements LocationCommandService {

    private final LocationRepository locationRepository;

    public LocationCommandServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Optional<Location> handle(CreateLocationCommand command) {
        var location = new Location(
                command.name(),
                command.address(),
                command.latitude(),
                command.longitude(),
                command.city(),
                command.district(),
                command.postalCode(),
                command.country()
        );
        
        try {
            locationRepository.save(location);
            return Optional.of(location);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Location> handle(UpdateLocationCommand command) {
        return locationRepository.findById(command.locationId())
                .map(location -> {
                    location.updateLocation(
                            command.name(),
                            command.address(),
                            command.latitude(),
                            command.longitude(),
                            command.city(),
                            command.district(),
                            command.postalCode(),
                            command.country()
                    );
                    locationRepository.save(location);
                    return location;
                });
    }

    @Override
    public boolean handle(DeleteLocationCommand command) {
        return locationRepository.findById(command.locationId())
                .map(location -> {
                    location.deactivate();
                    locationRepository.save(location);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public void deleteLocation(Long locationId) {
        locationRepository.deleteById(locationId);
    }
}
