package com.acme.ezpark.platform.parking.application.internal;

import com.acme.ezpark.platform.parking.domain.model.aggregates.Parking;
import com.acme.ezpark.platform.parking.domain.model.queries.GetParkingByIdQuery;
import com.acme.ezpark.platform.parking.domain.model.queries.GetParkingsByLocationQuery;
import com.acme.ezpark.platform.parking.domain.model.queries.GetParkingsByOwnerIdQuery;
import com.acme.ezpark.platform.parking.domain.services.ParkingQueryService;
import com.acme.ezpark.platform.parking.infrastructure.persistence.jpa.repositories.ParkingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingQueryServiceImpl implements ParkingQueryService {

    private final ParkingRepository parkingRepository;

    public ParkingQueryServiceImpl(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    public Optional<Parking> handle(GetParkingByIdQuery query) {
        return parkingRepository.findById(query.parkingId());
    }

    @Override
    public List<Parking> handle(GetParkingsByLocationQuery query) {
        return parkingRepository.findParkingsWithinRadius(
            query.latitude(), 
            query.longitude(), 
            query.radiusKm()
        );
    }

    @Override
    public List<Parking> handle(GetParkingsByOwnerIdQuery query) {
        return parkingRepository.findByOwnerIdAndIsActiveTrue(query.ownerId());
    }
}
