package com.acme.ezpark.platform.user.domain.model.valueobjects;

/**
 * User role value object for P2P parking platform
 * HOST: Can offer parking spaces
 * GUEST: Can book parking spaces  
 * BOTH: Can do both actions
 */
public enum UserRole {
    HOST,   // Propietario de parking
    GUEST,  // Usuario que busca parking
    BOTH    // Puede ser ambos
}
