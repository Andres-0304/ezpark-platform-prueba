package com.acme.ezpark.platform.user.interfaces.rest;

import com.acme.ezpark.platform.user.domain.model.queries.GetUserByIdQuery;
import com.acme.ezpark.platform.user.domain.services.UserCommandService;
import com.acme.ezpark.platform.user.domain.services.UserQueryService;
import com.acme.ezpark.platform.user.interfaces.rest.resources.*;
import com.acme.ezpark.platform.user.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
public class UsersController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public UsersController(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Register a new user in the system")
    public ResponseEntity<UserResource> createUser(@RequestBody CreateUserResource resource) {
        var createUserCommand = CreateUserCommandFromResourceAssembler.toCommandFromResource(resource);
        var user = userCommandService.handle(createUserCommand);
        
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticate user credentials")
    public ResponseEntity<UserResource> loginUser(@RequestBody LoginUserResource resource) {
        var loginUserCommand = LoginUserCommandFromResourceAssembler.toCommandFromResource(resource);
        var user = userCommandService.handle(loginUserCommand);
        
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID", description = "Get user information by user ID")
    public ResponseEntity<UserResource> getUserById(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(getUserByIdQuery);
        
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user profile", description = "Update user profile information")
    public ResponseEntity<UserResource> updateUser(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @RequestBody UpdateUserResource resource) {
        var updateUserCommand = UpdateUserCommandFromResourceAssembler.toCommandFromResource(userId, resource);
        var user = userCommandService.handle(updateUserCommand);
        
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }
}
