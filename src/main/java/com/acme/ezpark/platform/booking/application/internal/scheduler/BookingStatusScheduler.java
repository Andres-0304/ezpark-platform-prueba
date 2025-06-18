package com.acme.ezpark.platform.booking.application.internal.scheduler;

import com.acme.ezpark.platform.booking.domain.model.aggregates.Booking;
import com.acme.ezpark.platform.booking.domain.model.valueobjects.BookingStatus;
import com.acme.ezpark.platform.booking.infrastructure.persistence.jpa.repositories.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Scheduler service for automatic booking status updates
 * Runs periodically to update booking statuses based on time
 * @author EzPark Team
 */
@Service
public class BookingStatusScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(BookingStatusScheduler.class);
    
    private final BookingRepository bookingRepository;
    
    @Autowired
    public BookingStatusScheduler(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
    
    /**
     * Updates booking statuses every minute
     * Activates, completes, and expires bookings based on current time
     */
    @Scheduled(fixedRate = 60000) // Every minute
    @Transactional
    public void updateBookingStatuses() {
        try {
            logger.debug("Starting automatic booking status update at {}", LocalDateTime.now());
            
            // Get all bookings that might need status updates
            List<Booking> activeBookings = bookingRepository.findByStatusIn(
                List.of(BookingStatus.CONFIRMED, BookingStatus.ACTIVE)
            );
            
            int updatedCount = 0;
            
            for (Booking booking : activeBookings) {
                BookingStatus originalStatus = booking.getStatus();
                booking.updateStatusBasedOnTime();
                
                if (!originalStatus.equals(booking.getStatus())) {
                    bookingRepository.save(booking);
                    updatedCount++;
                    logger.info("Updated booking {} from {} to {} at {}", 
                        booking.getId(), originalStatus, booking.getStatus(), LocalDateTime.now());
                }
            }
            
            if (updatedCount > 0) {
                logger.info("Updated {} booking statuses", updatedCount);
            }
            
        } catch (Exception e) {
            logger.error("Error updating booking statuses: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Clean up expired bookings once per hour
     * Marks old expired bookings as expired if they weren't caught by the regular update
     */
    @Scheduled(fixedRate = 3600000) // Every hour
    @Transactional
    public void cleanupExpiredBookings() {
        try {
            logger.debug("Starting expired bookings cleanup at {}", LocalDateTime.now());
            
            // Find bookings that should be expired (confirmed but start time passed by more than 30 minutes)
            LocalDateTime expireThreshold = LocalDateTime.now().minusMinutes(30);
            List<Booking> expiredBookings = bookingRepository.findByStatusAndStartTimeBefore(
                BookingStatus.CONFIRMED, expireThreshold
            );
            
            for (Booking booking : expiredBookings) {
                booking.setStatus(BookingStatus.EXPIRED);
                booking.setUpdatedAt(new java.util.Date());
                bookingRepository.save(booking);
                logger.info("Expired booking {} that started at {}", booking.getId(), booking.getStartTime());
            }
            
            if (!expiredBookings.isEmpty()) {
                logger.info("Expired {} old confirmed bookings", expiredBookings.size());
            }
            
        } catch (Exception e) {
            logger.error("Error cleaning up expired bookings: {}", e.getMessage(), e);
        }
    }
}
