package com.acme.ezpark.platform.location.interfaces.rest;

import com.acme.ezpark.platform.location.domain.model.queries.*;
import com.acme.ezpark.platform.location.domain.services.LocationCommandService;
import com.acme.ezpark.platform.location.domain.services.LocationQueryService;
import com.acme.ezpark.platform.location.interfaces.rest.resources.*;
import com.acme.ezpark.platform.location.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/locations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Location", description = "Location Management Endpoints")
public class LocationsController {

    private final LocationCommandService locationCommandService;
    private final LocationQueryService locationQueryService;

    public LocationsController(LocationCommandService locationCommandService,
                              LocationQueryService locationQueryService) {
        this.locationCommandService = locationCommandService;
        this.locationQueryService = locationQueryService;
    }

    @PostMapping
    @Operation(summary = "Create location", description = "Create a new location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Location created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<LocationResource> createLocation(@RequestBody CreateLocationResource resource) {
        var createLocationCommand = CreateLocationCommandFromResourceAssembler.toCommandFromResource(resource);
        var location = locationCommandService.handle(createLocationCommand);
        if (location.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var locationResource = LocationResourceFromEntityAssembler.toResourceFromEntity(location.get());
        return new ResponseEntity<>(locationResource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all locations", description = "Get all active locations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Locations found")
    })
    public ResponseEntity<List<LocationResource>> getAllLocations() {
        var getAllLocationsQuery = new GetAllLocationsQuery();
        var locations = locationQueryService.handle(getAllLocationsQuery);
        var locationResources = locations.stream()
                .map(LocationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(locationResources);
    }

    @GetMapping("/{locationId}")
    @Operation(summary = "Get location by id", description = "Get location by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location found"),
            @ApiResponse(responseCode = "404", description = "Location not found")
    })
    public ResponseEntity<LocationResource> getLocationById(@PathVariable Long locationId) {
        var getLocationByIdQuery = new GetLocationByIdQuery(locationId);
        var location = locationQueryService.handle(getLocationByIdQuery);
        if (location.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var locationResource = LocationResourceFromEntityAssembler.toResourceFromEntity(location.get());
        return ResponseEntity.ok(locationResource);
    }

    @GetMapping("/city/{city}")
    @Operation(summary = "Get locations by city", description = "Get all locations in a specific city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Locations found")
    })
    public ResponseEntity<List<LocationResource>> getLocationsByCity(@PathVariable String city) {
        var getLocationsByCityQuery = new GetLocationsByCityQuery(city);
        var locations = locationQueryService.handle(getLocationsByCityQuery);
        var locationResources = locations.stream()
                .map(LocationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(locationResources);
    }

    @GetMapping("/district/{district}")
    @Operation(summary = "Get locations by district", description = "Get all locations in a specific district")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Locations found")
    })
    public ResponseEntity<List<LocationResource>> getLocationsByDistrict(@PathVariable String district) {
        var getLocationsByDistrictQuery = new GetLocationsByDistrictQuery(district);
        var locations = locationQueryService.handle(getLocationsByDistrictQuery);
        var locationResources = locations.stream()
                .map(LocationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(locationResources);
    }

    @GetMapping("/nearby")
    @Operation(summary = "Get nearby locations", description = "Get locations within a specific radius")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Locations found"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    public ResponseEntity<List<LocationResource>> getLocationsNearby(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "5.0") Double radiusKm) {
        
        var getLocationsNearbyQuery = new GetLocationsNearbyQuery(latitude, longitude, radiusKm);
        var locations = locationQueryService.handle(getLocationsNearbyQuery);
        var locationResources = locations.stream()
                .map(LocationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(locationResources);
    }

    @PutMapping("/{locationId}")
    @Operation(summary = "Update location", description = "Update location information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location updated"),
            @ApiResponse(responseCode = "404", description = "Location not found")
    })
    public ResponseEntity<LocationResource> updateLocation(@PathVariable Long locationId,
                                                         @RequestBody UpdateLocationResource resource) {
        var updateLocationCommand = UpdateLocationCommandFromResourceAssembler.toCommandFromResource(locationId, resource);
        var location = locationCommandService.handle(updateLocationCommand);
        if (location.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var locationResource = LocationResourceFromEntityAssembler.toResourceFromEntity(location.get());
        return ResponseEntity.ok(locationResource);
    }

    @DeleteMapping("/{locationId}")
    @Operation(summary = "Delete location", description = "Delete location by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location deleted"),
            @ApiResponse(responseCode = "404", description = "Location not found")
    })
    public ResponseEntity<?> deleteLocation(@PathVariable Long locationId) {
        var getLocationByIdQuery = new GetLocationByIdQuery(locationId);
        var location = locationQueryService.handle(getLocationByIdQuery);
        if (location.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        locationCommandService.deleteLocation(locationId);
        return ResponseEntity.ok("Location with given id successfully deleted");
    }
}
