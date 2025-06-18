package com.acme.ezpark.platform.booking.domain.model.aggregates;

import com.acme.ezpark.platform.booking.domain.model.valueobjects.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long userId;
    
    @Column(nullable = false)
    private Long parkingId;
    
    @Column(nullable = false)
    private Long vehicleId;
    
    @Column(nullable = false)
    private LocalDateTime startTime;
    
    @Column(nullable = false)
    private LocalDateTime endTime;
    
    @Column
    private LocalDateTime actualStartTime;
    
    @Column
    private LocalDateTime actualEndTime;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.PENDING;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal finalPrice;
    
    @Column(length = 1000)
    private String notes;
    
    @Column(length = 500)
    private String cancellationReason;
      @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;
    
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date updatedAt;
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public Long getParkingId() {
        return parkingId;
    }
    
    public Long getVehicleId() {
        return vehicleId;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public LocalDateTime getActualStartTime() {
        return actualStartTime;
    }
    
    public LocalDateTime getActualEndTime() {
        return actualEndTime;
    }
    
    public BookingStatus getStatus() {
        return status;
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public BigDecimal getFinalPrice() {
        return finalPrice;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public String getCancellationReason() {
        return cancellationReason;
    }
    
    public java.util.Date getCreatedAt() {
        return createdAt;
    }
    
    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }
    
    // Constructor
    public Booking(Long userId, Long parkingId, Long vehicleId, LocalDateTime startTime, 
                  LocalDateTime endTime, BigDecimal totalPrice, String notes) {
        this.userId = userId;
        this.parkingId = parkingId;
        this.vehicleId = vehicleId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalPrice = totalPrice;
        this.notes = notes;
        this.createdAt = new java.util.Date();
        this.updatedAt = new java.util.Date();
    }
    
    // Business methods
    public long getBookedHours() {
        return ChronoUnit.HOURS.between(startTime, endTime);
    }
    
    public long getActualHours() {
        if (actualStartTime != null && actualEndTime != null) {
            return ChronoUnit.HOURS.between(actualStartTime, actualEndTime);
        }
        return 0;
    }
    
    public void confirmBooking() {
        if (status == BookingStatus.PENDING) {
            this.status = BookingStatus.CONFIRMED;
            this.updatedAt = new java.util.Date();
        }
    }
      public void startBooking() {
        if (status == BookingStatus.CONFIRMED) {
            this.status = BookingStatus.ACTIVE;
            this.actualStartTime = LocalDateTime.now();
            this.updatedAt = new java.util.Date();
        }
    }
    
    public void completeBooking(BigDecimal finalPrice) {
        if (status == BookingStatus.ACTIVE) {
            this.status = BookingStatus.COMPLETED;
            this.actualEndTime = LocalDateTime.now();
            this.finalPrice = finalPrice;
            this.updatedAt = new java.util.Date();
        }
    }
    
    public void cancelBooking(String reason) {
        if (status == BookingStatus.PENDING || status == BookingStatus.CONFIRMED) {
            this.status = BookingStatus.CANCELLED;
            this.cancellationReason = reason;
            this.updatedAt = new java.util.Date();
        }
    }
    
    public void cancel() {
        this.cancelBooking("User role removal - automatic cancellation");
    }
    
    public boolean isActive() {
        return status == BookingStatus.CONFIRMED || status == BookingStatus.ACTIVE;
    }
    
    public boolean isPast() {
        return endTime.isBefore(LocalDateTime.now()) || status == BookingStatus.COMPLETED;
    }
    
    public boolean isFuture() {
        return startTime.isAfter(LocalDateTime.now()) && status != BookingStatus.CANCELLED;
    }
    
    // New temporal business methods for automatic status management
    
    /**
     * Check if booking can be cancelled (15 minutes before start time)
     */
    public boolean canBeCancelled() {
        if (status == BookingStatus.CANCELLED || status == BookingStatus.COMPLETED || status == BookingStatus.EXPIRED) {
            return false;
        }
        LocalDateTime cancelDeadline = startTime.minusMinutes(15);
        return LocalDateTime.now().isBefore(cancelDeadline);
    }
    
    /**
     * Check if booking should be automatically activated (start time reached)
     */
    public boolean shouldBeActive() {
        if (status != BookingStatus.CONFIRMED) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(startTime) && now.isBefore(endTime);
    }
    
    /**
     * Check if booking should be automatically completed (end time reached)
     */
    public boolean shouldBeCompleted() {
        if (status != BookingStatus.ACTIVE) {
            return false;
        }
        return LocalDateTime.now().isAfter(endTime);
    }
    
    /**
     * Check if booking should be expired (start time passed without activation)
     */
    public boolean shouldBeExpired() {
        if (status != BookingStatus.CONFIRMED) {
            return false;
        }
        // If 30 minutes passed after start time and still not active
        LocalDateTime expireTime = startTime.plusMinutes(30);
        return LocalDateTime.now().isAfter(expireTime);
    }
    
    /**
     * Get minutes remaining until cancellation deadline
     */
    public long getMinutesUntilCancelDeadline() {
        LocalDateTime cancelDeadline = startTime.minusMinutes(15);
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(cancelDeadline)) {
            return 0;
        }
        return ChronoUnit.MINUTES.between(now, cancelDeadline);
    }
    
    /**
     * Automatically update status based on current time
     */
    public void updateStatusBasedOnTime() {
        if (shouldBeExpired()) {
            this.status = BookingStatus.EXPIRED;
            this.updatedAt = new java.util.Date();
        } else if (shouldBeCompleted()) {
            this.status = BookingStatus.COMPLETED;
            this.actualEndTime = LocalDateTime.now();
            this.updatedAt = new java.util.Date();
        } else if (shouldBeActive()) {
            this.status = BookingStatus.ACTIVE;
            if (this.actualStartTime == null) {
                this.actualStartTime = LocalDateTime.now();
            }
            this.updatedAt = new java.util.Date();
        }
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = new java.util.Date();
        updatedAt = new java.util.Date();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new java.util.Date();
    }
}
