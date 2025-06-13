package com.acme.ezpark.platform.location.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country", nullable = false)
    private String country = "Peru";

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // Getters
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public Double getLatitude() {
        return latitude;
    }
    
    public Double getLongitude() {
        return longitude;
    }
    
    public String getCity() {
        return city;
    }
    
    public String getDistrict() {
        return district;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public String getCountry() {
        return country;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }

    public Location() {}

    public Location(String name, String address, Double latitude, Double longitude, 
                   String city, String district, String postalCode, String country) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.district = district;
        this.postalCode = postalCode;
        this.country = country;
        this.isActive = true;
    }

    public void updateLocation(String name, String address, Double latitude, Double longitude,
                              String city, String district, String postalCode, String country) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.district = district;
        this.postalCode = postalCode;
        this.country = country;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void activate() {
        this.isActive = true;
    }

    public double calculateDistance(Double otherLatitude, Double otherLongitude) {
        final int R = 6371; // Radius of the earth in km
        
        double latDistance = Math.toRadians(otherLatitude - this.latitude);
        double lonDistance = Math.toRadians(otherLongitude - this.longitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(otherLatitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return R * c; // Distance in km
    }
}
