package com.acme.ezpark.platform.booking.infrastructure.persistence.jpa.repositories;

import com.acme.ezpark.platform.booking.domain.model.aggregates.Booking;
import com.acme.ezpark.platform.booking.domain.model.valueobjects.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByUserIdAndStatus(Long userId, BookingStatus status);
    List<Booking> findByParkingId(Long parkingId);
    
    // Methods for scheduler
    List<Booking> findByStatusIn(List<BookingStatus> statuses);
    List<Booking> findByStatusAndStartTimeBefore(BookingStatus status, LocalDateTime startTime);
}
