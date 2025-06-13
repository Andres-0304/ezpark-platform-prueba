package com.acme.ezpark.platform.location.interfaces.rest.transform;

import com.acme.ezpark.platform.location.domain.model.aggregates.Location;
import com.acme.ezpark.platform.location.interfaces.rest.resources.LocationResource;

public class LocationResourceFromEntityAssembler {
    
    public static LocationResource toResourceFromEntity(Location entity) {
        return new LocationResource(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getCity(),
                entity.getDistrict(),
                entity.getPostalCode(),
                entity.getCountry(),
                entity.getIsActive()
        );
    }
}
