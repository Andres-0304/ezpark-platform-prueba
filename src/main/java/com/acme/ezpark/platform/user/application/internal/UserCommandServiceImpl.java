package com.acme.ezpark.platform.user.application.internal;

import com.acme.ezpark.platform.user.domain.model.aggregates.User;
import com.acme.ezpark.platform.user.domain.model.commands.CreateUserCommand;
import com.acme.ezpark.platform.user.domain.model.commands.LoginUserCommand;
import com.acme.ezpark.platform.user.domain.model.commands.UpdateUserCommand;
import com.acme.ezpark.platform.user.domain.services.UserCommandService;
import com.acme.ezpark.platform.user.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;

    public UserCommandServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
            command.birthDate()
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
    }    @Override
    public Optional<User> handle(LoginUserCommand command) {
        return userRepository.findByEmail(command.email())
            .filter(user -> user.getPassword().equals(command.password())) // In production, use proper password verification
            .filter(user -> user.getIsActive());
    }
}
