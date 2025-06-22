package com.acme.ezpark.platform.user.application.internal;

import com.acme.ezpark.platform.user.domain.model.aggregates.User;
import com.acme.ezpark.platform.user.domain.model.commands.CreateUserCommand;
import com.acme.ezpark.platform.user.domain.model.commands.LoginUserCommand;
import com.acme.ezpark.platform.user.domain.model.commands.UpdateUserCommand;
import com.acme.ezpark.platform.user.domain.model.commands.UpgradeUserRoleCommand;
import com.acme.ezpark.platform.user.domain.model.commands.RemoveUserRoleCommand;
import com.acme.ezpark.platform.user.domain.model.valueobjects.UserRole;
import com.acme.ezpark.platform.user.domain.services.UserCommandService;
import com.acme.ezpark.platform.user.infrastructure.persistence.jpa.repositories.UserRepository;
import com.acme.ezpark.platform.booking.infrastructure.persistence.jpa.repositories.BookingRepository;
import com.acme.ezpark.platform.parking.infrastructure.persistence.jpa.repositories.ParkingRepository;
import com.acme.ezpark.platform.vehicle.infrastructure.persistence.jpa.repositories.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final ParkingRepository parkingRepository;
    private final VehicleRepository vehicleRepository;

    public UserCommandServiceImpl(UserRepository userRepository,
                                BookingRepository bookingRepository,
                                ParkingRepository parkingRepository,
                                VehicleRepository vehicleRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.parkingRepository = parkingRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Optional<User> handle(CreateUserCommand command) {
        // Check if user already exists
        if (userRepository.existsByEmail(command.email())) {
            return Optional.empty();
        }

        // In production, password should be hashed
        var user = new User(
            command.email(),
            command.password(), // Should be hashed
            command.firstName(),
            command.lastName(),
            command.phone(),
            command.birthDate(),
            command.role()
        );

        try {
            userRepository.save(user);
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> handle(UpdateUserCommand command) {
        return userRepository.findById(command.userId())
            .map(user -> {
                user.updateProfile(
                    command.firstName(),
                    command.lastName(),
                    command.phone(),
                    command.birthDate(),
                    command.profilePicture()
                );
                userRepository.save(user);
                return user;
            });
    }
    
    @Override
    public Optional<User> handle(LoginUserCommand command) {
        return userRepository.findByEmail(command.email())
            .filter(user -> user.getPassword().equals(command.password())) // In production, use proper password verification
            .filter(user -> user.getIsActive());
    }
    
    @Override
    public Optional<User> handle(UpgradeUserRoleCommand command) {
        return userRepository.findByEmail(command.email())
            .map(user -> {
                user.upgradeRole(command.requestedRole());
                userRepository.save(user);
                return user;
            });
    }
    
    @Override
    @Transactional
    public Optional<User> handle(RemoveUserRoleCommand command) {
        return userRepository.findById(command.userId())
            .map(user -> {
                // Perform cleanup before role removal
                cleanupRoleSpecificData(user, command.roleToRemove());
                
                // Remove the role
                user.downgradeFromRole(command.roleToRemove());
                userRepository.save(user);
                return user;
            });
    }
    
    private void cleanupRoleSpecificData(User user, UserRole roleToRemove) {
        if (roleToRemove == UserRole.HOST) {
            cleanupHostData(user.getId());
        } else if (roleToRemove == UserRole.GUEST) {
            cleanupGuestData(user.getId());
        }
    }
    
    private void cleanupHostData(Long userId) {
        // 1. Cancel all bookings in my parkings
        var myParkings = parkingRepository.findByOwnerId(userId);
        myParkings.forEach(parking -> {
            var bookings = bookingRepository.findByParkingId(parking.getId());
            bookings.forEach(booking -> {
                if (!booking.getStatus().name().equals("COMPLETED") && 
                    !booking.getStatus().name().equals("CANCELLED")) {
                    booking.cancel(); // This should set status to CANCELLED
                    bookingRepository.save(booking);
                }
            });
        });
        
        // 2. Deactivate all my parkings
        myParkings.forEach(parking -> {
            parking.deactivate(); // This should set isActive to false
            parkingRepository.save(parking);
        });
    }
    
    private void cleanupGuestData(Long userId) {
        // 1. Cancel all my active bookings
        var myBookings = bookingRepository.findByUserId(userId);
        myBookings.forEach(booking -> {
            if (!booking.getStatus().name().equals("COMPLETED") && 
                !booking.getStatus().name().equals("CANCELLED")) {
                booking.cancel(); // This should set status to CANCELLED
                bookingRepository.save(booking);
            }
        });
        
        // 2. Deactivate all my vehicles
        var myVehicles = vehicleRepository.findByUserId(userId);
        myVehicles.forEach(vehicle -> {
            vehicle.deactivate(); // This should set isActive to false
            vehicleRepository.save(vehicle);
        });
    }
}
