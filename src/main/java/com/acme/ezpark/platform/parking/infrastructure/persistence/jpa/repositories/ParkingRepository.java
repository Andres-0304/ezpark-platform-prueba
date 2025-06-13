package com.acme.ezpark.platform.parking.infrastructure.persistence.jpa.repositories;

import com.acme.ezpark.platform.parking.domain.model.aggregates.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
    List<Parking> findByOwnerIdAndIsActiveTrue(Long ownerId);
    
    @Query(value = """
        SELECT * FROM parkings p 
        WHERE p.is_active = true AND p.is_available = true
        AND (6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) 
        * cos(radians(p.longitude) - radians(:longitude)) 
        + sin(radians(:latitude)) * sin(radians(p.latitude)))) <= :radiusKm
        ORDER BY (6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) 
        * cos(radians(p.longitude) - radians(:longitude)) 
        + sin(radians(:latitude)) * sin(radians(p.latitude))))
        """, nativeQuery = true)
    List<Parking> findParkingsWithinRadius(@Param("latitude") BigDecimal latitude, 
                                          @Param("longitude") BigDecimal longitude, 
                                          @Param("radiusKm") BigDecimal radiusKm);
}
