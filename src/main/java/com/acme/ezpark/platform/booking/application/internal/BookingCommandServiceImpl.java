package com.acme.ezpark.platform.booking.application.internal;

import com.acme.ezpark.platform.booking.domain.model.aggregates.Booking;
import com.acme.ezpark.platform.booking.domain.model.commands.*;
import com.acme.ezpark.platform.booking.domain.services.BookingCommandService;
import com.acme.ezpark.platform.booking.infrastructure.persistence.jpa.repositories.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingCommandServiceImpl implements BookingCommandService {

    private final BookingRepository bookingRepository;

    public BookingCommandServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Optional<Booking> handle(CreateBookingCommand command) {
        var booking = new Booking(
            command.userId(),
            command.parkingId(),
            command.vehicleId(),
            command.startTime(),
            command.endTime(),
            command.totalPrice(),
            command.notes()
        );

        try {
            bookingRepository.save(booking);
            return Optional.of(booking);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Booking> handle(CancelBookingCommand command) {
        return bookingRepository.findById(command.bookingId())
            .map(booking -> {
                booking.cancelBooking(command.cancellationReason());
                bookingRepository.save(booking);
                return booking;
            });
    }

    @Override
    public Optional<Booking> handle(ConfirmBookingCommand command) {
        return bookingRepository.findById(command.bookingId())
            .map(booking -> {
                booking.confirmBooking();
                bookingRepository.save(booking);
                return booking;
            });
    }

    @Override
    public Optional<Booking> handle(StartBookingCommand command) {
        return bookingRepository.findById(command.bookingId())
            .map(booking -> {
                booking.startBooking();
                bookingRepository.save(booking);
                return booking;
            });
    }

    @Override
    public Optional<Booking> handle(CompleteBookingCommand command) {
        return bookingRepository.findById(command.bookingId())
            .map(booking -> {
                booking.completeBooking(command.finalPrice());
                bookingRepository.save(booking);
                return booking;
            });
    }
}
