package com.acme.ezpark.platform.user.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Setter
@NoArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(nullable = false, length = 255)
    private String password;
    
    @Column(nullable = false, length = 100)
    private String firstName;
    
    @Column(nullable = false, length = 100)
    private String lastName;
    
    @Column(length = 20)
    private String phone;
    
    @Column
    private LocalDate birthDate;
    
    @Column(length = 255)
    private String profilePicture;
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @Column(nullable = false)
    private Boolean isVerified = false;
    
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;
    
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date updatedAt;
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public String getProfilePicture() {
        return profilePicture;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public Boolean getIsVerified() {
        return isVerified;
    }
    
    public java.util.Date getCreatedAt() {
        return createdAt;
    }
    
    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }
    
    // Constructor
    public User(String email, String password, String firstName, String lastName, String phone, LocalDate birthDate) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.birthDate = birthDate;
        this.createdAt = new java.util.Date();
        this.updatedAt = new java.util.Date();
    }
    
    // Business methods
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public void updateProfile(String firstName, String lastName, String phone, LocalDate birthDate, String profilePicture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.birthDate = birthDate;
        this.profilePicture = profilePicture;
        this.updatedAt = new java.util.Date();
    }
    
    public void verifyUser() {
        this.isVerified = true;
        this.updatedAt = new java.util.Date();
    }
    
    public void deactivateUser() {
        this.isActive = false;
        this.updatedAt = new java.util.Date();
    }
    
    public void activateUser() {
        this.isActive = true;
        this.updatedAt = new java.util.Date();
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
