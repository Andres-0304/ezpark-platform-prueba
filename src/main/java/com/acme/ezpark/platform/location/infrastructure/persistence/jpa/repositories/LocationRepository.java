package com.acme.ezpark.platform.location.infrastructure.persistence.jpa.repositories;

import com.acme.ezpark.platform.location.domain.model.aggregates.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByIsActiveTrue();
    List<Location> findByCityAndIsActiveTrue(String city);
    List<Location> findByDistrictAndIsActiveTrue(String district);
    List<Location> findByCityAndDistrictAndIsActiveTrue(String city, String district);
    
    @Query("SELECT l FROM Location l WHERE l.isActive = true AND " +
           "(6371 * acos(cos(radians(:latitude)) * cos(radians(l.latitude)) * " +
           "cos(radians(l.longitude) - radians(:longitude)) + " +
           "sin(radians(:latitude)) * sin(radians(l.latitude)))) <= :radiusKm")
    List<Location> findLocationsWithinRadius(@Param("latitude") Double latitude, 
                                           @Param("longitude") Double longitude, 
                                           @Param("radiusKm") Double radiusKm);
}
