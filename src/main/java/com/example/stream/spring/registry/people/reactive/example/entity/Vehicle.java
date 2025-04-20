package com.example.stream.spring.registry.people.reactive.example.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Table(name = "vehicles")
public class Vehicle {
    @Id
    private UUID id;

    @Column(name = "vehicle_type")
    private String vehicleType;  // e.g., Car, Motorcycle, Truck

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "ownership_start_date")
    private String ownershipStartDate;

    // Getters and Setters
    @Override
    public String toString() {
        return "Vehicle{id=" + id + ", vehicleType='" + vehicleType + "', registrationNumber='" + registrationNumber + "', ownershipStartDate='" + ownershipStartDate + "'}";
    }
}
